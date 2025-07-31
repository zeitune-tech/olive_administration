package sn.zeitune.olive_insurance_administration.app.mappers;

import sn.zeitune.olive_insurance_administration.app.dto.requests.ContributorRequest;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ContributorResponse;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ContributorTypeResponse;
import sn.zeitune.olive_insurance_administration.app.entities.Contributor;
import sn.zeitune.olive_insurance_administration.app.entities.ContributorType;

public class ContributorMapper {

    public static Contributor map(ContributorRequest request, ContributorType type) {
        return Contributor.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .phone(request.phone())
                .contributorType(type)
                .build();
    }

    public static ContributorResponse map(Contributor contributor, ContributorTypeResponse type) {
        return ContributorResponse.builder()
                .id(contributor.getUuid())
                .firstname(contributor.getFirstname())
                .lastname(contributor.getLastname())
                .email(contributor.getEmail())
                .phone(contributor.getPhone())
                .contributorType(type)
                .build();
    }
}
