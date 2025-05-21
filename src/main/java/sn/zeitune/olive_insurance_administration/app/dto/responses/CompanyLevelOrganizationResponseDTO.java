package sn.zeitune.olive_insurance_administration.app.dto.responses;

import java.util.Set;
import java.util.UUID;

public record CompanyLevelOrganizationResponseDTO(
        UUID id,
        String name,
        String description,
        UUID companyUuid,
        String companyName,
        Set<PointOfSaleResponseDTO> pointsOfSale
) {}
