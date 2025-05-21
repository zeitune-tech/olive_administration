package sn.zeitune.olive_insurance_administration.app.dto.responses;


import lombok.Builder;
import sn.zeitune.olive_insurance_administration.enums.ContributorType;

import java.util.UUID;

@Builder
public record ContributorResponse(
        UUID uuid,
        String designation,
        ContributorType type,
        UUID managementEntityId
) {}
