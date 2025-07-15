package sn.zeitune.olive_insurance_administration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import sn.zeitune.olive_insurance_administration.app.clients.CoverageClient;
import sn.zeitune.olive_insurance_administration.app.dto.external.CoverageReferenceResponse;
import sn.zeitune.olive_insurance_administration.app.dto.requests.*;
import sn.zeitune.olive_insurance_administration.app.dto.responses.BranchResponseDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.CategoryResponseDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.CompanyResponseDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.MarketLevelOrganizationResponseDTO;
import sn.zeitune.olive_insurance_administration.app.services.*;
import sn.zeitune.olive_insurance_administration.enums.PointOfSaleType;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableDiscoveryClient
public class OliveInsuranceAdministrationApplication implements CommandLineRunner {

    private final CompanyService companyService;
    private final MarketLevelOrganizationService marketLevelOrganizationService;
    private final BranchService branchService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final PointOfSaleService pointOfSaleService;
    private final CoverageClient coverageClient;

    public OliveInsuranceAdministrationApplication(CompanyService companyService, MarketLevelOrganizationService marketLevelOrganizationService, BranchService branchService, CategoryService categoryService, ProductService productService, PointOfSaleService pointOfSaleService, CoverageClient coverageClient) {
        this.companyService = companyService;
        this.marketLevelOrganizationService = marketLevelOrganizationService;
        this.branchService = branchService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.pointOfSaleService = pointOfSaleService;
        this.coverageClient = coverageClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(OliveInsuranceAdministrationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        try {

            companyService.create(
                    new CompanyRequestDTO(
                            "AXA Assurances",
                            "AXA",
                            "axa@gmail.com",
                            "33 989 00 03",
                            "Dakar, Senegal",
                            "images/logos/axa.png",
                            "",
                            "",
                            "",
                            ""
                    )
            );



            // Create default companies
            CompanyResponseDTO amsa = companyService.create(
                    new CompanyRequestDTO(
                            "AMSA Assurances",
                            "AMSA",
                            "amsa@gmail.com",
                            "33 989 00 01",
                            "Dakar, Senegal",
                            "images/logos/amsa.png",
                            "",
                            "",
                            "",
                            ""
                    )
            );

            // Create default market level organizations
            MarketLevelOrganizationResponseDTO poolTPV = marketLevelOrganizationService.create(new MarketLevelOrganizationRequestDTO(
                    "Pool Transports Publics de Voyageurs",
                    "PoolTPV",
                    "pooltpv@gmail.com",
                    "33 989 00 02",
                    "Dakar, Senegal",
                    "images/logos/pooltpv.png",
                    "",
                    ""
            ));
            // Link companies to market level organizations
            marketLevelOrganizationService.assignCompanies(
                    poolTPV.id(),
                    Set.copyOf(
                            Collections.singleton(amsa.id())
                    )
            );



        } catch (Exception e) {
            // Handle exception
            System.err.println("Error during initialization: " + e.getMessage());
        }

        try {
            pointOfSaleService.createBrokerPdv(
                    new BrokerPointOfSaleRequestDTO(
                            "VIP",
                            "VIP",
                            "vipassurance@gmail.com",
                            "33 989 00 04",
                            "Dakar, Senegal",
                            "images/logos/vip.png",
                            "",
                            "",
                            PointOfSaleType.BROKER
                    )
            );

            pointOfSaleService.createBrokerPdv(
                    new BrokerPointOfSaleRequestDTO(
                            "AssuCO",
                            "AssuCO",
                            "assuco@gmail.com",
                            "33 989 00 05",
                            "Dakar, Senegal",
                            "images/logos/assuco.png",
                            "",
                            "",
                            PointOfSaleType.BROKER
                    )
            );

        } catch (Exception e) {
            // Handle exception
            System.err.println("Error during initialization: " + e.getMessage());
        }


        try {

            CategoryResponseDTO assRep = categoryService.create(new CategoryRequestDTO(
                    "Assurance par répartition",
                    "Assurance par répartition"
            ));

            branchService.create(new BranchRequestDTO(
                    "Automobile",
                    "Assurance automobile",
                    assRep.id()
            ));


        } catch (Exception e) {
            // Handle exception
            System.err.println("Error during initialization: " + e.getMessage());
        }

        try {
            // init coverages
            CompanyResponseDTO amsa = companyService.getByName("AMSA Assurances");
            MarketLevelOrganizationResponseDTO poolTPV = marketLevelOrganizationService.getByName("Pool Transports Publics de Voyageurs");
            List<CoverageReferenceResponse> amsaCoverages = coverageClient.initCoverageReferences(
                    amsa.id()
            );
            List<CoverageReferenceResponse> poolTPVCoverages = coverageClient.initCoverageReferences(
                    poolTPV.id()
            );

            BranchResponseDTO branchTPV = branchService.getByName("Automobile");

            ProductRequestDTO amsaProduct = ProductRequestDTO.builder()
                    .name("AAP")
                    .description("Assurance automobile pour particulier")
                    .branch(branchTPV.id())
                    .fleet(false)
                    .hasReduction(false)
                    .minRisk(1)
                    .maxRisk(1)
                    .canBeRepartitioned(false)
                    .coverages(
                            amsaCoverages.stream()
                                    .map(CoverageReferenceResponse::id)
                                    .collect(Collectors.toSet())
                    )
                    .build();
            productService.createProduct(
                    amsaProduct,
                    amsa.id()
            );

            ProductRequestDTO poolTPVProduct = ProductRequestDTO.builder()
                    .name("TPV")
                    .description("Transport Public de Voyageurs")
                    .branch(branchTPV.id())
                    .fleet(false)
                    .hasReduction(false)
                    .minRisk(1)
                    .maxRisk(1)
                    .canBeRepartitioned(false)
                    .coverages(
                            poolTPVCoverages.stream()
                                    .map(CoverageReferenceResponse::id)
                                    .collect(Collectors.toSet())
                    )
                    .build();

            productService.createProduct(
                    poolTPVProduct,
                    poolTPV.id()
            );

        } catch (Exception e) {
            // Handle exception
            System.err.println("Error during initialization: " + e.getMessage());
        }

    }
}
