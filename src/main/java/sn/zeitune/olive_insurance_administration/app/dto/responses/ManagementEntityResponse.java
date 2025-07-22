package sn.zeitune.olive_insurance_administration.app.dto.responses;

import lombok.Builder;
import sn.zeitune.olive_insurance_administration.enums.ManagementEntityType;

import java.util.UUID;

@Builder
public record ManagementEntityResponse(
        UUID id,
        String name,
        String acronym,
        String email,
        String phone,
        String address,
        String logo,
        String fax,
        String gsm,
        ManagementEntityType type,
        ManagementEntityResponse superiorEntity
) {
}
