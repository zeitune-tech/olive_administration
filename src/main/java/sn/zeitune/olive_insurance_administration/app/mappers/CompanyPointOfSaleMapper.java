package sn.zeitune.olive_insurance_administration.app.mappers;

import sn.zeitune.olive_insurance_administration.app.dto.requests.CompanyPointOfSaleRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.CompanyPointOfSaleResponseDTO;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.Company;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.CompanyLevelOrganization;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.CompanyPointOfSale;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class CompanyPointOfSaleMapper {

    public static CompanyPointOfSale map(
            CompanyPointOfSaleRequestDTO dto,
            Company company,
            Set<CompanyLevelOrganization> companyLevelOrganizations,
            CompanyPointOfSale entity
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

        entity.setCompany(company);
        entity.setCompanyLevelOrganizations(companyLevelOrganizations);

        return entity;
    }

    public static CompanyPointOfSaleResponseDTO map(CompanyPointOfSale entity) {

        Set<UUID> companyLevelOrgUuids = entity.getCompanyLevelOrganizations().stream()
                .map(CompanyLevelOrganization::getUuid)
                .collect(Collectors.toSet());

        Set<String> companyLevelOrgNames = entity.getCompanyLevelOrganizations().stream()
                .map(CompanyLevelOrganization::getName)
                .collect(Collectors.toSet());

        return new CompanyPointOfSaleResponseDTO(
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
                entity.getCompany() != null ? entity.getCompany().getUuid() : null,
                entity.getCompany() != null ? entity.getCompany().getName() : null,
                companyLevelOrgUuids,
                companyLevelOrgNames
        );
    }
}
