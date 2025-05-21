package sn.zeitune.olive_insurance_administration.app.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDTO(
        @NotBlank(message = "Name is mandatory")
        String name,

        String description
) {}
