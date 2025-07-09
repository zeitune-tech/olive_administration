package sn.zeitune.olive_insurance_administration.app.mappers;

import sn.zeitune.olive_insurance_administration.app.dto.responses.ManagementEntityResponse;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.ManagementEntity;

public class ManagementEntityMapper {

    public static ManagementEntityResponse map(ManagementEntity entity) {
        return new ManagementEntityResponse(
                entity.getUuid(),
                entity.getName(),
                entity.getAcronym(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getAddress(),
                entity.getLogo(),
                entity.getFax(),
                entity.getGsm(),
                entity.getType(),
                null
        );
    }

    public static ManagementEntityResponse map(ManagementEntity entity, ManagementEntityResponse superiorEntity) {
        return new ManagementEntityResponse(
                entity.getUuid(),
                entity.getName(),
                entity.getAcronym(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getAddress(),
                entity.getLogo(),
                entity.getFax(),
                entity.getGsm(),
                entity.getType(),
                superiorEntity
        );
    }
}
