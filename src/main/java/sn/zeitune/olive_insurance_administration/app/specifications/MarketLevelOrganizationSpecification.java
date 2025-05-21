package sn.zeitune.olive_insurance_administration.app.specifications;

import org.springframework.data.jpa.domain.Specification;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.MarketLevelOrganization;

public class MarketLevelOrganizationSpecification {

    public static Specification<MarketLevelOrganization> nameContains(String name) {
        return (root, query, cb) -> name == null ? null :
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<MarketLevelOrganization> emailContains(String email) {
        return (root, query, cb) -> email == null ? null :
                cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    public static Specification<MarketLevelOrganization> acronymContains(String acronym) {
        return (root, query, cb) -> acronym == null ? null :
                cb.like(cb.lower(root.get("acronym")), "%" + acronym.toLowerCase() + "%");
    }

    public static Specification<MarketLevelOrganization> withFilters(String name, String email, String acronym) {
        return Specification
                .where(nameContains(name))
                .and(emailContains(email))
                .and(acronymContains(acronym));
    }
}
