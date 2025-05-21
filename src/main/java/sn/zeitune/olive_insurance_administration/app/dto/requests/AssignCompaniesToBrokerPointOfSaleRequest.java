package sn.zeitune.olive_insurance_administration.app.dto.requests;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;
import java.util.UUID;

public record AssignCompaniesToBrokerPointOfSaleRequest(
        @NotEmpty Set<UUID> companyUuids
) {}
