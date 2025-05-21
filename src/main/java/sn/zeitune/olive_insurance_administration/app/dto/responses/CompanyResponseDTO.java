package sn.zeitune.olive_insurance_administration.app.dto.responses;

import java.util.UUID;

public record CompanyResponseDTO(
        UUID id,
        String name,
        String acronym,
        String email,
        String phone,
        String address,
        String logo,
        String fax,
        String gsm,
        String legalStatus,
        String registrationNumber
) {}
