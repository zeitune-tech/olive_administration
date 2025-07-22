package sn.zeitune.olive_insurance_administration.app.services;


import sn.zeitune.olive_insurance_administration.app.dto.requests.ClosureRequest;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ClosureResponse;

import java.util.List;
import java.util.UUID;

public interface ClosureService {

    ClosureResponse create(ClosureRequest request, UUID managementEntity);

    ClosureResponse getByUuid(UUID uuid);

    List<ClosureResponse> getAll(UUID managementEntity);

    ClosureResponse update(UUID uuid, ClosureRequest request);

    void delete(UUID uuid);
}
