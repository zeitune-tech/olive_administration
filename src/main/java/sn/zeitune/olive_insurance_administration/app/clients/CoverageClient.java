package sn.zeitune.olive_insurance_administration.app.clients;

import sn.zeitune.olive_insurance_administration.app.dto.external.CoverageReferenceResponse;
import sn.zeitune.olive_insurance_administration.app.dto.external.CoverageRequest;

import java.util.List;
import java.util.UUID;


public interface CoverageClient {
    void createCoverages(CoverageRequest request);
    List<CoverageReferenceResponse> getCoverageReferences(UUID managementEntity);
    List<CoverageReferenceResponse> initCoverageReferences(UUID managementEntity);

}
