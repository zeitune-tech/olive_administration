package sn.zeitune.olive_insurance_administration.app.dto.requests;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import sn.zeitune.olive_insurance_administration.enums.ContributorType;

import java.util.UUID;

@Builder
public record ContributorRequest(

        @NotBlank(message = "Designation must not be blank")
        String designation,

        @NotNull(message = "Contributor type must not be null")
        ContributorType type,

        @NotNull(message = "Management entity ID must not be null")
        UUID managementEntityId
) {}
