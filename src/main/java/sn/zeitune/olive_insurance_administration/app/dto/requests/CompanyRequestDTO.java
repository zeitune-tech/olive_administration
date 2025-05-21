package sn.zeitune.olive_insurance_administration.app.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CompanyRequestDTO(
        @NotNull(message = "Name cannot be null")
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotNull(message = "Acronym cannot be null")
        String acronym,
        @NotNull(message = "Email cannot be null")
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        String email,
        String phone,
        String address,
        String logo,
        String fax,
        String gsm,
        String legalStatus,
        String registrationNumber
) {}
