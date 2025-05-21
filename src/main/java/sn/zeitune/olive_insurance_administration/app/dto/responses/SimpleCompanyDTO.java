package sn.zeitune.olive_insurance_administration.app.dto.responses;

import java.util.UUID;

public record SimpleCompanyDTO(
        UUID id,
        String name,
        String email
) {}
