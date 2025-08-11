package sn.zeitune.olive_insurance_administration.app.dto.responses;


import lombok.Builder;
import sn.zeitune.olive_insurance_administration.enums.ClosureType;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record ClosureResponse(
        UUID id,
        ClosureType type,
        LocalDate date,
        UUID managementEntity
) {}

