package sn.zeitune.olive_insurance_administration.app.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.olive_insurance_administration.app.clients.CoverageClient;
import sn.zeitune.olive_insurance_administration.app.dto.external.CoverageRequest;
import sn.zeitune.olive_insurance_administration.app.dto.requests.ProductRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ProductResponseDTO;
import sn.zeitune.olive_insurance_administration.app.entities.Branch;
import sn.zeitune.olive_insurance_administration.app.entities.PointOfSaleProduct;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.Company;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.ManagementEntity;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.MarketLevelOrganization;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.CompanyPointOfSale;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.PointOfSale;
import sn.zeitune.olive_insurance_administration.app.entities.product.PrivateProduct;
import sn.zeitune.olive_insurance_administration.app.entities.product.Product;
import sn.zeitune.olive_insurance_administration.app.entities.product.PublicProduct;
import sn.zeitune.olive_insurance_administration.app.exceptions.BusinessException;
import sn.zeitune.olive_insurance_administration.app.exceptions.NotFoundException;
import sn.zeitune.olive_insurance_administration.app.mappers.ProductMapper;
import sn.zeitune.olive_insurance_administration.app.repositories.*;
import sn.zeitune.olive_insurance_administration.app.services.ProductService;
import sn.zeitune.olive_insurance_administration.app.specifications.CompanyPointOfSaleSpecification;
import sn.zeitune.olive_insurance_administration.app.specifications.ProductSpecification;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final BranchRepository branchRepository;
    private final CompanyRepository companyRepository;
    private final ProductRepository productRepository;
    private final ManagementEntityRepository entityRepository;

    private final CoverageClient coverageClient;
    private final PointOfSaleProductRepository pointOfSaleProductRepository;
    private final CompanyPointOfSaleRepository companyPointOfSaleRepository;


    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO dto, UUID ownerUuid) {
        Branch branch = branchRepository.findByUuid(dto.branch())
                .orElseThrow(() -> new NotFoundException("Branch not found"));

        ManagementEntity owner = entityRepository.findByUuid(ownerUuid)
                .orElseThrow(() -> new NotFoundException("Owner entity not found"));

        Product product;

        if (owner instanceof MarketLevelOrganization) {

            product = PublicProduct.builder()
                    .name(dto.name())
                    .description(dto.description())
                    .fleet(dto.fleet())
                    .hasReduction(dto.hasReduction())
                    .branch(branch)
                    .minRisk(dto.minRisk())
                    .maxRisk(dto.maxRisk())
                    .minimumGuaranteeNumber(dto.minimumGuaranteeNumber())
                    .owner(owner)
                    .build();
        } else {
            product = PrivateProduct.builder()
                    .name(dto.name())
                    .fleet(dto.fleet())
                    .hasReduction(dto.hasReduction())
                    .branch(branch)
                    .minRisk(dto.minRisk())
                    .maxRisk(dto.maxRisk())
                    .minimumGuaranteeNumber(dto.minimumGuaranteeNumber())
                    .owner(owner)
                    .build();
        }

        if (productRepository.existsByNameAndBranchAndOwner(dto.name(), branch, owner)) {
            throw new BusinessException("Product with the same name already exists in this branch and owner.");
        }

        ProductResponseDTO productResponseDTO = ProductMapper.map(productRepository.save(product));

        // create coverages
        try {
            coverageClient.createCoverages(new CoverageRequest(
                    productResponseDTO.id(),
                    dto.coverages(),
                    ownerUuid
            ));
        } catch (Exception e) {
            log.error("Error creating coverages for product {}: {}", productResponseDTO.id(), e.getMessage());
            throw new BusinessException("Error creating coverages for product");
        }

        if (owner instanceof Company) {
            // create point of sale product
            Specification<CompanyPointOfSale> specification = CompanyPointOfSaleSpecification
                    .withFilters(
                            (Company) owner,
                            ""
                    );
            List<CompanyPointOfSale> pointOfSales = companyPointOfSaleRepository.findAll(specification);

            if (pointOfSales.isEmpty()) {
                return productResponseDTO;
            }

            for (CompanyPointOfSale pointOfSale : pointOfSales) {
                PointOfSaleProduct pointOfSaleProduct = new PointOfSaleProduct();
                pointOfSaleProduct.setPointOfSale(pointOfSale);
                pointOfSaleProduct.setProduct(product);
                pointOfSaleProduct.setCompany(ownerUuid);
                pointOfSaleProductRepository.save(pointOfSaleProduct);
            }

        }

        return productResponseDTO;
    }

    @Override
    public ProductResponseDTO updateProduct(UUID uuid, ProductRequestDTO dto) {
        Product product = productRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        Branch branch = branchRepository.findByUuid(dto.branch())
                .orElseThrow(() -> new NotFoundException("Branch not found"));


        ProductMapper.map(dto, branch, product.getOwner(), product);

        return ProductMapper.map(productRepository.save(product));
    }



    @Override
    public void sharePublicProductWithCompanies(UUID productUuid, Set<UUID> companyUuids) {
        PublicProduct product = (PublicProduct) productRepository.findByUuid(productUuid)
                .filter(p -> p instanceof PublicProduct)
                .orElseThrow(() -> new NotFoundException("Public product not found"));

        Set<Company> companies = new HashSet<>(companyRepository.findAllByUuidIn(companyUuids));
        if (companies.isEmpty()) throw new NotFoundException("No valid companies found");

        product.getSharedWithCompanies().addAll(companies);
        productRepository.save(product);
    }

    @Override
    public ProductResponseDTO getByUuid(UUID uuid) {
        Product product = productRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        return ProductMapper.map(product);
    }

    @Override
    public List<ProductResponseDTO> getAll() {
        return productRepository.findAll().stream()
                .map(ProductMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> getByManagementEntityUuid(UUID uuid) {
        ManagementEntity owner = entityRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Management entity not found"));
        return productRepository.findAllByOwner(owner).stream()
                .map(ProductMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductResponseDTO> search(
            String name,
            String branchName,
            Integer minRisk,
            Integer maxRisk,
            Boolean fleet,
            Boolean hasReduction,
            Pageable pageable,
            UUID ownerUuid
    ) {
        ManagementEntity owner = entityRepository.findByUuid(ownerUuid)
                .orElseThrow(() -> new NotFoundException("Management entity not found"));

        log.info("Management entity: {}", owner);

        if (owner instanceof PointOfSale) {
            return pointOfSaleProductRepository.findAllByPointOfSale(
                    (PointOfSale) owner,
                    pageable
            ).map(item -> ProductMapper.map(item.getProduct()));
        }

        return productRepository
                .findAll(ProductSpecification.withFilters(name, branchName, minRisk, maxRisk, fleet, hasReduction, owner), pageable)
                .map(ProductMapper::map);
    }

}
