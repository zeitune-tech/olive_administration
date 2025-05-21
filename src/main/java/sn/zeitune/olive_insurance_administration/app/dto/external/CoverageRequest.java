package sn.zeitune.olive_insurance_administration.app.dto.external;

import java.util.Set;
import java.util.UUID;

public record CoverageRequest (
        UUID product,
        Set<UUID> coverages,
        UUID managementEntity
) {
}
