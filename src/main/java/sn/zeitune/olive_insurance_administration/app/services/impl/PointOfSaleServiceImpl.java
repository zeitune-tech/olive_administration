package sn.zeitune.olive_insurance_administration.app.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import sn.zeitune.olive_insurance_administration.app.clients.UserClient;
import sn.zeitune.olive_insurance_administration.app.dto.external.CreateUserRequest;
import sn.zeitune.olive_insurance_administration.app.dto.requests.BrokerPointOfSaleRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.requests.CompanyPointOfSaleRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.PointOfSaleResponseDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.SimpleCompanyDTO;
import sn.zeitune.olive_insurance_administration.app.entities.PointOfSaleProduct;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.Company;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.BrokerPointOfSale;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.CompanyPointOfSale;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.PointOfSale;
import sn.zeitune.olive_insurance_administration.app.entities.product.Product;
import sn.zeitune.olive_insurance_administration.app.exceptions.ConflictException;
import sn.zeitune.olive_insurance_administration.app.exceptions.NotFoundException;
import sn.zeitune.olive_insurance_administration.app.mappers.BrokerPointOfSaleMapper;
import sn.zeitune.olive_insurance_administration.app.mappers.PointOfSaleMapper;
import sn.zeitune.olive_insurance_administration.app.repositories.*;
import sn.zeitune.olive_insurance_administration.app.services.PointOfSaleService;
import sn.zeitune.olive_insurance_administration.app.specifications.BrokerPointOfSaleSpecification;
import sn.zeitune.olive_insurance_administration.app.specifications.CompanyPointOfSaleSpecification;
import sn.zeitune.olive_insurance_administration.app.specifications.ProductSpecification;
import sn.zeitune.olive_insurance_administration.enums.ManagementEntityType;
import sn.zeitune.olive_insurance_administration.enums.PointOfSaleType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class PointOfSaleServiceImpl implements PointOfSaleService {

    private final BrokerPointOfSaleRepository brokerRepo;
    private final CompanyPointOfSaleRepository companyRepo;
    private final CompanyRepository companyRepository;
    private final UserClient userClient;

    private final PointOfSaleProductRepository pointOfSaleProductRepository;
    private final ProductRepository productRepository;

    @Override
    public PointOfSaleResponseDTO createBrokerPdv(BrokerPointOfSaleRequestDTO dto) {

        BrokerPointOfSale pdv = BrokerPointOfSale.builder()
                .name(dto.name())
                .email(dto.email())
                .phone(dto.phone())
                .address(dto.address())
                .typePointOfSale(dto.typePointOfSale())
                .companies(new HashSet<>()) // vide à la création
                .build();

        // if exists, throw exception
        if (brokerRepo.existsByNameOrEmail(pdv.getName(), pdv.getEmail())) {
            throw new ConflictException("A broker with this email already exists");
        }

        BrokerPointOfSale saved = brokerRepo.save(pdv);

        if (saved.getEmail() != null) {
            CreateUserRequest userRequest = new CreateUserRequest(
                    saved.getEmail(),
                    saved.getName(),
                    saved.getUuid(),
                    ManagementEntityType.POINT_OF_SALE,
                    ManagementEntityType.POINT_OF_SALE
            );
            try {
                userClient.createUser(userRequest);
            } catch (Exception e) {
                System.err.println("Failed to create user: " + e.getMessage());
            }
        }


        return PointOfSaleMapper.map(saved);
    }

    @Override
    public PointOfSaleResponseDTO createCompanyPdv(CompanyPointOfSaleRequestDTO dto, UUID managementEntity) {
        Company company = companyRepository.findByUuid(managementEntity)
                .orElseThrow(() -> new NotFoundException("Company not found"));

        CompanyPointOfSale pdv = CompanyPointOfSale.builder()
                .name(dto.name())
                .email(dto.email())
                .phone(dto.phone())
                .address(dto.address())
                .typePointOfSale(dto.typePointOfSale())
                .type(ManagementEntityType.POINT_OF_SALE)
                .company(company)
                .build();

        PointOfSaleResponseDTO pointOfSaleResponse = PointOfSaleMapper.map(companyRepo.save(pdv));

        Specification<Product> spec = ProductSpecification.ownerEquals(company);
        List< Product> products = productRepository.findAll(spec);
        for (Product product : products) {
            pointOfSaleProductRepository.save(
                    PointOfSaleProduct.builder()
                            .company(company.getUuid())
                            .product(product)
                            .pointOfSale(pdv)
                            .build()
            );
        }

        return pointOfSaleResponse;

    }

    @Override
    public PointOfSaleResponseDTO getByUuid(UUID uuid) {
        return brokerRepo.findByUuid(uuid).map(PointOfSaleMapper::map)
                .or(() -> companyRepo.findByUuid(uuid).map(PointOfSaleMapper::map))
                .orElseThrow(() -> new NotFoundException("Point of Sale not found"));
    }

    @Override
    public List<PointOfSaleResponseDTO> getAll() {
        List<PointOfSaleResponseDTO> brokers = brokerRepo.findAll().stream()
                .map(PointOfSaleMapper::map).toList();
        List<PointOfSaleResponseDTO> companies = companyRepo.findAll().stream()
                .map(PointOfSaleMapper::map).toList();
        return Stream.concat(brokers.stream(), companies.stream()).toList();
    }

    @Override
    public Page<PointOfSaleResponseDTO> getAll(Pageable pageable) {
        return brokerRepo.findAll(pageable)
                .map(PointOfSaleMapper::map);
    }

    @Override
    public Page<PointOfSaleResponseDTO> search(String name, PointOfSaleType type, Pageable pageable) {
        Page<CompanyPointOfSale> companyPage = companyRepo.findAll(pageable);
        Page<BrokerPointOfSale> brokerPage = brokerRepo.findAll(pageable);

        return new PageImpl<>(
                Stream.concat(
                        companyPage.getContent().stream().filter(p -> filter(p, name, type)).map(PointOfSaleMapper::map),
                        brokerPage.getContent().stream().filter(p -> filter(p, name, type)).map(PointOfSaleMapper::map)
                ).toList(),
                pageable,
                companyPage.getTotalElements() + brokerPage.getTotalElements()
        );
    }

    private boolean filter(PointOfSale pdv, String name, PointOfSaleType type) {
        boolean matches = true;
        if (name != null) {
            matches &= pdv.getName().toLowerCase().contains(name.toLowerCase());
        }
        if (type != null) {
            matches &= pdv.getTypePointOfSale() == type;
        }
        return matches;
    }

    @Override
    public Page<PointOfSaleResponseDTO> searchByCompany(UUID companyUuid, String name, Pageable pageable) {
        Company company = companyRepository.findByUuid(companyUuid)
                .orElseThrow(() -> new NotFoundException("Company not found"));

        Specification<CompanyPointOfSale> spec = CompanyPointOfSaleSpecification.withFilters(company, name);

        return companyRepo.findAll(spec, pageable)
                .map(PointOfSaleMapper::map);
    }

    @Override
    public Page<PointOfSaleResponseDTO> searchBrokers(String name, PointOfSaleType type, Pageable pageable) {
        Specification<BrokerPointOfSale> spec = BrokerPointOfSaleSpecification.withFilters(name, type);
        return brokerRepo.findAll(spec, pageable).map(PointOfSaleMapper::map);
    }


    @Override
    public Page<PointOfSaleResponseDTO> searchBrokersByCompany(UUID companyUuid, String name, PointOfSaleType type, Pageable pageable) {
        Company company = companyRepository.findByUuid(companyUuid)
                .orElseThrow(() -> new NotFoundException("Company not found"));

        Specification<BrokerPointOfSale> spec = BrokerPointOfSaleSpecification.withCompanyAndFilters(company, name, type);

        return brokerRepo.findAll(spec, pageable).map(PointOfSaleMapper::map);
    }

    @Override
    public void assignCompaniesToBrokerPdv(UUID brokerPdvUuid, Set<UUID> companyUuids) {
        BrokerPointOfSale pdv = brokerRepo.findByUuid(brokerPdvUuid)
                .orElseThrow(() -> new NotFoundException("Broker point of sale not found"));

        List<Company> companies = companyRepository.findAllByUuidIn(companyUuids);
        if (companies.isEmpty()) throw new NotFoundException("No valid companies found");

        for (Company company : companies) {
            if (pdv.getCompanies().contains(company)) {
                throw new ConflictException("This broker is already associated with company: " + company.getName());
            }
        }

        pdv.getCompanies().addAll(companies);
        brokerRepo.save(pdv);
    }

    @Override
    public void removeCompanyFromBrokerPdv(UUID brokerPdvUuid, UUID companyUuid) {
        BrokerPointOfSale pdv = brokerRepo.findByUuid(brokerPdvUuid)
                .orElseThrow(() -> new NotFoundException("Broker point of sale not found"));

        Company company = companyRepository.findByUuid(companyUuid)
                .orElseThrow(() -> new NotFoundException("Company not found"));

        if (!pdv.getCompanies().contains(company)) {
            throw new NotFoundException("This company is not associated with this broker");
        }

        pdv.getCompanies().remove(company);
        brokerRepo.save(pdv);
    }

    @Override
    public List<SimpleCompanyDTO> getCompaniesOfBroker(UUID brokerPdvUuid) {
        BrokerPointOfSale pdv = brokerRepo.findByUuid(brokerPdvUuid)
                .orElseThrow(() -> new NotFoundException("Broker point of sale not found"));

        return pdv.getCompanies().stream()
                .map(c -> new SimpleCompanyDTO(c.getUuid(), c.getName(), c.getEmail()))
                .toList();
    }
}

