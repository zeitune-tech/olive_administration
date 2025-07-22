package sn.zeitune.olive_insurance_administration.app.dto.requests;

import java.util.Set;
import java.util.UUID;

public record CompanyLevelOrganizationPointsOfSaleRequestDTO (
    Set<UUID> pointsOfSaleIds
) {
}
