package sn.zeitune.olive_insurance_administration.app.mappers;

import sn.zeitune.olive_insurance_administration.app.dto.requests.BrokerPointOfSaleRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.BrokerPointOfSaleResponseDTO;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.Company;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.CompanyLevelOrganization;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.BrokerPointOfSale;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class BrokerPointOfSaleMapper {

    public static BrokerPointOfSale map(
            BrokerPointOfSaleRequestDTO dto,
            Set<Company> companies,
            Set<CompanyLevelOrganization> companyLevelOrganizations,
            BrokerPointOfSale entity
    ) {
        entity.setName(dto.name());
        entity.setAcronym(dto.acronym());
        entity.setEmail(dto.email());
        entity.setPhone(dto.phone());
        entity.setAddress(dto.address());
        entity.setLogo(dto.logo());
        entity.setFax(dto.fax());
        entity.setGsm(dto.gsm());
        entity.setTypePointOfSale(dto.typePointOfSale());

        entity.setCompanies(companies);
        entity.setCompanyLevelOrganizations(companyLevelOrganizations);

        return entity;
    }

    public static BrokerPointOfSaleResponseDTO map(BrokerPointOfSale entity) {

        Set<UUID> companyUuids = entity.getCompanies().stream()
                .map(Company::getUuid)
                .collect(Collectors.toSet());

        Set<String> companyNames = entity.getCompanies().stream()
                .map(Company::getName)
                .collect(Collectors.toSet());

        Set<UUID> companyLevelOrgUuids = entity.getCompanyLevelOrganizations().stream()
                .map(CompanyLevelOrganization::getUuid)
                .collect(Collectors.toSet());

        Set<String> companyLevelOrgNames = entity.getCompanyLevelOrganizations().stream()
                .map(CompanyLevelOrganization::getName)
                .collect(Collectors.toSet());

        return new BrokerPointOfSaleResponseDTO(
                entity.getUuid(),
                entity.getName(),
                entity.getAcronym(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getAddress(),
                entity.getLogo(),
                entity.getFax(),
                entity.getGsm(),
                entity.getTypePointOfSale(),
                companyUuids,
                companyNames,
                companyLevelOrgUuids,
                companyLevelOrgNames
        );
    }
}
