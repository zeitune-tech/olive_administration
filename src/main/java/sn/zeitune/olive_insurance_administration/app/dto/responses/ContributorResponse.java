package sn.zeitune.olive_insurance_administration.app.dto.responses;


import lombok.Builder;
import sn.zeitune.olive_insurance_administration.enums.ContributorType;

import java.util.UUID;

@Builder
public record ContributorResponse(
        UUID id,
        String firstname,
        String lastname,
        String email,
        String phone,
        ContributorTypeResponse contributorType,
        UUID managementEntityId
) {}
