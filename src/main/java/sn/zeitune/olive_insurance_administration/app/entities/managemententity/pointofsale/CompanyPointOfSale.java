package sn.zeitune.olive_insurance_administration.app.managemententity.pointofsale;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import sn.zeitune.olive_insurance_administration.app.managemententity.Company;
import sn.zeitune.olive_insurance_administration.enums.ManagementEntityType;
import sn.zeitune.olive_insurance_administration.enums.PointOfSaleType;

import java.util.Objects;

@Data
@Entity
@Table(name = "points_de_vente_compagnie")
public class CompanyPointOfSale  extends PointOfSale{

    @ManyToOne
    @JoinColumn(name = "code_comp")
    private Company company;

    @Builder
    public CompanyPointOfSale(
            String name, String acronym, String email, String phone,
            String address, String logo, String fax, String gsm,
            PointOfSaleType type, Company company
    ) {
        super(name, acronym, email, phone, address, logo, fax, gsm, type);
        this.company = company;
    }

    protected CompanyPointOfSale() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyPointOfSale that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(company, that.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId(), ManagementEntityType.POINT_OF_SALE);
    }
}
