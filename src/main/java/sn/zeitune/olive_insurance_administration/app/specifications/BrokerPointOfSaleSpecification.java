package sn.zeitune.olive_insurance_administration.app.specifications;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.Company;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.BrokerPointOfSale;
import sn.zeitune.olive_insurance_administration.enums.PointOfSaleType;


public class BrokerPointOfSaleSpecification {

    public static Specification<BrokerPointOfSale> nameLike(String name) {
        return (root, query, cb) -> name == null ? null :
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<BrokerPointOfSale> typeEquals(PointOfSaleType type) {
        return (root, query, cb) -> type == null ? null :
                cb.equal(root.get("typePointOfSale"), type);
    }

    public static Specification<BrokerPointOfSale> linkedToCompany(Company company) {
        return (root, query, cb) -> {
            Join<Object, Object> join = root.join("companies", JoinType.INNER);
            return cb.equal(join, company);
        };
    }

    public static Specification<BrokerPointOfSale> withFilters(String name, PointOfSaleType type) {
        return Specification.where(nameLike(name)).and(typeEquals(type));
    }

    public static Specification<BrokerPointOfSale> withCompanyAndFilters(Company company, String name, PointOfSaleType type) {
        return Specification.where(linkedToCompany(company))
                .and(nameLike(name))
                .and(typeEquals(type));
    }
}
