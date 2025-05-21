package sn.zeitune.olive_insurance_administration.app.dto.external;

import sn.zeitune.olive_insurance_administration.enums.ManagementEntityType;

import java.util.UUID;

public record CreateUserRequest(
        String email,
        String name,
        UUID managementEntity,
        ManagementEntityType accessLevel,
        ManagementEntityType type
) {}
