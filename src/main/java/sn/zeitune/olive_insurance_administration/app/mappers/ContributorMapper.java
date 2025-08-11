package sn.zeitune.olive_insurance_administration.app.mappers;

import sn.zeitune.olive_insurance_administration.app.dto.requests.ContributorRequest;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ContributorResponse;
import sn.zeitune.olive_insurance_administration.app.entities.Contributor;

public class ContributorMapper {

    public static Contributor map(ContributorRequest request) {
        return Contributor.builder()

                //.type(request.type())
                .build();
    }

    public static ContributorResponse map(Contributor contributor) {
        return ContributorResponse.builder()
                .uuid(contributor.getUuid())
                //.type(contributor.getType())
                .build();
    }
}
