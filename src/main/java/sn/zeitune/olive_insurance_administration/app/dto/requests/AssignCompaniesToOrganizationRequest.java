package sn.zeitune.olive_insurance_administration.app.dto.requests;

import jakarta.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.UUID;

public record AssignCompaniesToOrganizationRequest(
        @NotEmpty(message = "At least one company UUID is required")
        Set<UUID> companyUuids
) {}
