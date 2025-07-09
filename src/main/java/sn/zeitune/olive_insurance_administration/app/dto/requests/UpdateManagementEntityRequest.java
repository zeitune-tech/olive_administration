package sn.zeitune.olive_insurance_administration.app.dto.requests;

public record UpdateManagementEntityRequest(
        String name,
        String acronym,
        String email,
        String phone,
        String address,
        String logo,
        String fax,
        String gsm
) {}
