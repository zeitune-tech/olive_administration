package sn.zeitune.olive_insurance_administration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import sn.zeitune.olive_insurance_administration.app.clients.CoverageClient;
import sn.zeitune.olive_insurance_administration.app.dto.external.CoverageReferenceResponse;
import sn.zeitune.olive_insurance_administration.app.dto.requests.*;
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
public class OliveInsuranceAdministrationApplication {



    public static void main(String[] args) {
        SpringApplication.run(OliveInsuranceAdministrationApplication.class, args);
    }

}
