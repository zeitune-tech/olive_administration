package sn.zeitune.olive_insurance_administration.app.services;

import sn.zeitune.olive_insurance_administration.app.dto.requests.ContributorRequest;
import sn.zeitune.olive_insurance_administration.app.dto.requests.ContributorTypeRequest;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ContributorResponse;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ContributorTypeResponse;

import java.util.List;
import java.util.UUID;

public interface ContributorService {
    ContributorResponse create(ContributorRequest request, UUID managementEntity);

    ContributorResponse update(UUID uuid, ContributorRequest request);

    ContributorResponse getByUuid(UUID uuid);
    List<ContributorResponse> getAll();

    ContributorTypeResponse createContributorType(ContributorTypeRequest request, UUID managementEntity);
    ContributorTypeResponse updateContributorType(ContributorTypeRequest request, UUID managementEntity, UUID uuid);
    ContributorTypeResponse getContributorTypeByUuid(UUID uuid);
    List<ContributorTypeResponse> getAllContributorTypes(UUID managementEntity);

    void delete(UUID uuid);
}