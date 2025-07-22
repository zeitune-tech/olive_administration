package sn.zeitune.olive_insurance_administration.app.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record CompanyLevelOrganizationRequestDTO(
        @NotBlank String name,
        String description,

        Set<UUID> pointsOfSaleIds
) {}
