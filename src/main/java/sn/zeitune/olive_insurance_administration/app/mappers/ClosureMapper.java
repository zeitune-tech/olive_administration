package sn.zeitune.olive_insurance_administration.app.mappers;

import sn.zeitune.olive_insurance_administration.app.dto.requests.ClosureRequest;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ClosureResponse;
import sn.zeitune.olive_insurance_administration.app.entities.Closure;

public class ClosureMapper {

    public static Closure map(ClosureRequest request) {
        return Closure.builder()
                .type(request.type())
                .date(request.date())
                .build();
    }

    public static ClosureResponse map(Closure closure) {
        return ClosureResponse.builder()
                .id(closure.getUuid())
                .type(closure.getType())
                .date(closure.getDate())
                .managementEntity(closure.getManagementEntity())
                .build();
    }
}

