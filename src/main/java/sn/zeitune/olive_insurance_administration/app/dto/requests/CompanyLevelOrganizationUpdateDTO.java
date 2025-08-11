package sn.zeitune.olive_insurance_administration.app.dto.requests;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.UUID;

public record CompanyLevelOrganizationUpdateDTO(
        @NotBlank String name,
        String description
) {}
