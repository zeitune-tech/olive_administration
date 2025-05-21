package sn.zeitune.olive_insurance_administration.app.services;

import sn.zeitune.olive_insurance_administration.app.dto.requests.ContributorRequest;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ContributorResponse;

import java.util.List;
import java.util.UUID;

public interface ContributorService {
    ContributorResponse create(ContributorRequest request, UUID managementEntity);

    ContributorResponse getByUuid(UUID uuid);
    List<ContributorResponse> getAll();
    void delete(UUID uuid);
}