package sn.zeitune.olive_insurance_administration.app.specifications;

import org.springframework.data.jpa.domain.Specification;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.Company;

public class CompanySpecification {

    public static Specification<Company> nameContains(String name) {
        return (root, query, cb) -> name == null ? null :
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Company> acronymContains(String acronym) {
        return (root, query, cb) -> acronym == null ? null :
                cb.like(cb.lower(root.get("acronym")), "%" + acronym.toLowerCase() + "%");
    }

    public static Specification<Company> emailContains(String email) {
        return (root, query, cb) -> email == null ? null :
                cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    public static Specification<Company> withFilters(String name, String acronym, String email) {
        return Specification.where(nameContains(name))
                .and(acronymContains(acronym))
                .and(emailContains(email));
    }
}
