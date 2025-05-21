package sn.zeitune.olive_insurance_administration.app.mappers;

import sn.zeitune.olive_insurance_administration.app.dto.requests.ContributorRequest;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ContributorResponse;
import sn.zeitune.olive_insurance_administration.app.entities.Contributor;

public class ContributorMapper {

    public static Contributor map(ContributorRequest request) {
        return Contributor.builder()
                .designation(request.designation())
                .type(request.type())
                .managementEntity(request.managementEntityId())
                .build();
    }

    public static ContributorResponse map(Contributor contributor) {
        return ContributorResponse.builder()
                .uuid(contributor.getUuid())
                .designation(contributor.getDesignation())
                .type(contributor.getType())
                .managementEntityId(contributor.getManagementEntity())
                .build();
    }
}
