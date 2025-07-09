package sn.zeitune.olive_insurance_administration.app.services;

import sn.zeitune.olive_insurance_administration.app.dto.responses.ManagementEntityResponse;

import java.util.List;
import java.util.UUID;

public interface ManagementEntityService {

    ManagementEntityResponse get(UUID uuid);

    List<ManagementEntityResponse> getAllByIds(List<UUID> managementEntityIds);
}
