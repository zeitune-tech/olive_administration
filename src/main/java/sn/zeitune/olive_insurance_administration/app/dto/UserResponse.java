package sn.zeitune.olive_insurance_administration.app.dto;

public record UserResponse(
        String email,
        String name,
        String managementEntity,
        String type,
        String accessLevel
) {
}
