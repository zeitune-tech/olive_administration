package sn.zeitune.olive_insurance_administration.app.specifications;

import org.springframework.data.jpa.domain.Specification;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.Company;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.CompanyPointOfSale;

public class CompanyPointOfSaleSpecification {

    public static Specification<CompanyPointOfSale> belongsToCompany(Company company) {
        return (root, query, cb) -> cb.equal(root.get("company"), company);
    }

    public static Specification<CompanyPointOfSale> nameLike(String name) {
        return (root, query, cb) -> name == null ? null :
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<CompanyPointOfSale> withFilters(Company company, String name) {
        return Specification
                .where(belongsToCompany(company))
                .and(nameLike(name));
    }
}
