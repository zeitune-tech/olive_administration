package sn.zeitune.olive_insurance_administration.app.mappers;

import sn.zeitune.olive_insurance_administration.app.dto.requests.CompanyLevelOrganizationRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.CompanyLevelOrganizationResponseDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.PointOfSaleResponseDTO;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.Company;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.CompanyLevelOrganization;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.PointOfSale;

import java.util.Set;
import java.util.stream.Collectors;

public class CompanyLevelOrganizationMapper {

    public static CompanyLevelOrganization map(
            CompanyLevelOrganizationRequestDTO dto,
            Company company,
            Set<PointOfSale> pointOfSales,
            CompanyLevelOrganization entity
    ) {
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setCompany(company);
        entity.setPointsOfSale(pointOfSales);
        return entity;
    }

    public static CompanyLevelOrganizationResponseDTO map(CompanyLevelOrganization entity) {
        Set<PointOfSaleResponseDTO> pointOfSaleResponseDTOs = entity.getPointsOfSale()
                .stream()
                .map(PointOfSaleMapper::map)
                .collect(Collectors.toSet());

        return new CompanyLevelOrganizationResponseDTO(
                entity.getUuid(),
                entity.getName(),
                entity.getDescription(),
                entity.getCompany().getUuid(),
                entity.getCompany().getName(),
                pointOfSaleResponseDTOs
        );
    }
}
