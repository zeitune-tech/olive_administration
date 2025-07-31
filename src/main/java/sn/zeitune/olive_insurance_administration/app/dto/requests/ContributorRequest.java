package sn.zeitune.olive_insurance_administration.app.dto.requests;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import sn.zeitune.olive_insurance_administration.enums.ContributorType;

import java.util.UUID;

@Builder
public record ContributorRequest(

        String firstname,
        String lastname,

        String email,

        String phone,

        UUID contributorTypeId
) {}
