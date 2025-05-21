package sn.zeitune.olive_insurance_administration.app.specifications;

import org.springframework.data.jpa.domain.Specification;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.CompanyLevelOrganization;

import java.util.UUID;

public class CompanyLevelOrganizationSpecification {

    public static Specification<CompanyLevelOrganization> belongsToCompany(UUID uuid) {
        return (root, query, cb) -> uuid == null ? null :
                cb.equal(root.get("company").get("uuid"), uuid);
    }

    public static Specification<CompanyLevelOrganization> nameLike(String name) {
        return (root, query, cb) -> name == null ? null :
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<CompanyLevelOrganization> withFilters(UUID companyUuid, String name) {
        return Specification.where(belongsToCompany(companyUuid)).and(nameLike(name));
    }
}

