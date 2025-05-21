package sn.zeitune.olive_insurance_administration.app.services;

import sn.zeitune.olive_insurance_administration.app.dto.responses.ManagementEntityResponse;

import java.util.UUID;

public interface ManagementEntityService {

    ManagementEntityResponse get(UUID uuid);
}
