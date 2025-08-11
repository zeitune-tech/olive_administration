package sn.zeitune.olive_insurance_administration.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.olive_insurance_administration.app.clients.UserClient;
import sn.zeitune.olive_insurance_administration.app.dto.external.CreateUserRequest;
import sn.zeitune.olive_insurance_administration.app.dto.requests.CompanyRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.CompanyResponseDTO;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.Company;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.CompanyMarketLevelOrganizations;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.ManagementEntity;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.MarketLevelOrganization;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.BrokerPointOfSale;
import sn.zeitune.olive_insurance_administration.app.exceptions.NotFoundException;
import sn.zeitune.olive_insurance_administration.app.mappers.CompanyMapper;
import sn.zeitune.olive_insurance_administration.app.mappers.MarketLevelOrganizationMapper;
import sn.zeitune.olive_insurance_administration.app.repositories.CompanyMarketLevelOrganizationRepository;
import sn.zeitune.olive_insurance_administration.app.repositories.CompanyRepository;
import sn.zeitune.olive_insurance_administration.app.repositories.ManagementEntityRepository;
import sn.zeitune.olive_insurance_administration.app.services.CompanyService;
import sn.zeitune.olive_insurance_administration.app.specifications.CompanySpecification;
import sn.zeitune.olive_insurance_administration.enums.ManagementEntityType;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ManagementEntityRepository managementEntityRepository;
    private final CompanyMarketLevelOrganizationRepository marketLevelOrgAssociationRepository;
    private final UserClient userClient;

    @Override
    public CompanyResponseDTO create(CompanyRequestDTO dto) {
        Company company = CompanyMapper.map(dto, new Company());

        // Check if a company with the same name or email already exists
        if (companyRepository.existsByNameOrEmail(company.getName(), company.getEmail())) {
            throw new RuntimeException("Company with the same name or email already exists");
        }
        Company saved = companyRepository.save(company);

        if (saved.getEmail() != null && !saved.getEmail().isBlank()) {
            CreateUserRequest userRequest = new CreateUserRequest(
                    saved.getEmail(),
                    saved.getName(),
                    saved.getUuid(),
                    ManagementEntityType.COMPANY,
                    ManagementEntityType.COMPANY
            );
            try {
                userClient.createUser(userRequest);
            } catch (Exception e) {
                // Handle the exception as needed
                throw new RuntimeException("Failed to create user in external system", e);
            }
        }

        return CompanyMapper.map(saved);
    }

    @Override
    public CompanyResponseDTO update(UUID uuid, CompanyRequestDTO dto) {
        Company company = companyRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Company not found"));
        CompanyMapper.map(dto, company);
        return CompanyMapper.map(companyRepository.save(company));
    }

    @Override
    public void delete(UUID uuid) {
        Company company = companyRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Company not found"));
        companyRepository.delete(company);
    }

    @Override
    public CompanyResponseDTO getByUuid(UUID uuid) {
        Company company = companyRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Company not found"));
        return CompanyMapper.map(company);
    }

    @Override
    public List<CompanyResponseDTO> getAll() {
        return companyRepository.findAll().stream()
                .map(CompanyMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyResponseDTO getByName(String name) {
        return companyRepository.findByName(name)
                .map(CompanyMapper::map)
                .orElseThrow(() -> new NotFoundException("Company not found"));
    }

    @Override
    public Page<CompanyResponseDTO> getAllCompanies(Pageable pageable) {
        return companyRepository
                .findAll(pageable)
                .map(CompanyMapper::map);
    }

    @Override
    public Page<CompanyResponseDTO> getAllLinkedCompanies(UUID managementEntity, Pageable pageable) {
        ManagementEntity entity = managementEntityRepository.findByUuid(managementEntity)
                .orElseThrow(() -> new NotFoundException("Management entity not found"));
        if (entity instanceof MarketLevelOrganization) {
            return marketLevelOrgAssociationRepository
                    .findAllByMarketLevelOrganization((MarketLevelOrganization) entity, pageable)
                    .map(CompanyMarketLevelOrganizations::getCompany)
                    .map(CompanyMapper::map);
        }

        else if (entity instanceof BrokerPointOfSale)  {
            return companyRepository.findAllByBrokerPointOfSalesContains(
                    (BrokerPointOfSale) entity, pageable)
                    .map(CompanyMapper::map);
        }

        else {
            throw new RuntimeException("Invalid management entity type");
        }
    }

    @Override
    public Page<CompanyResponseDTO> search(String name, String acronym, String email, Pageable pageable) {
        return companyRepository
                .findAll(CompanySpecification.withFilters(name, acronym, email), pageable)
                .map(CompanyMapper::map);
    }

}
