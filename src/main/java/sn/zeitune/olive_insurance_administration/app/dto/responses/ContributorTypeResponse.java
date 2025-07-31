package sn.zeitune.olive_insurance_administration.app.dto.responses;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ContributorTypeResponse(
        UUID managementEntity,
        UUID id,
        String label
) {
}
