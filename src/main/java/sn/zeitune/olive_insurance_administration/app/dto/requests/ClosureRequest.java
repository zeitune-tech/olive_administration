package sn.zeitune.olive_insurance_administration.app.dto.requests;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import sn.zeitune.olive_insurance_administration.enums.ClosureType;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record ClosureRequest(

        @NotNull(message = "Closure type must not be null")
        ClosureType type,

        @NotNull(message = "Effective date must not be null")
        @PastOrPresent(message = "Effective date cannot be in the future")
        LocalDate date,

        @NotNull(message = "Product not null")
        UUID productId
) {}

