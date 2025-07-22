package sn.zeitune.olive_insurance_administration.app.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sn.zeitune.olive_insurance_administration.app.clients.CoverageClient;
import sn.zeitune.olive_insurance_administration.app.dto.external.CoverageReferenceResponse;
import sn.zeitune.olive_insurance_administration.app.dto.requests.*;
import sn.zeitune.olive_insurance_administration.app.dto.responses.*;
import sn.zeitune.olive_insurance_administration.app.services.*;
import sn.zeitune.olive_insurance_administration.enums.PointOfSaleType;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AppInitializer implements CommandLineRunner {

    private final CompanyService companyService;
    private final MarketLevelOrganizationService marketLevelOrganizationService;
    private final PointOfSaleService pointOfSaleService;


    @Override
    public void run(String... args) {

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
    }
}