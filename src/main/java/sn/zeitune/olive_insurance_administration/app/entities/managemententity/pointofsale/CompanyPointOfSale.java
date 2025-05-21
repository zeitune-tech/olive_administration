package sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.Company;
import sn.zeitune.olive_insurance_administration.enums.ManagementEntityType;
import sn.zeitune.olive_insurance_administration.enums.PointOfSaleType;

import java.util.Objects;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "points_de_vente_compagnie")
public class CompanyPointOfSale  extends PointOfSale{

    @ManyToOne
    @JoinColumn(name = "code_comp")
    private Company company;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyPointOfSale that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(company, that.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getUuid(), super.getId(), ManagementEntityType.POINT_OF_SALE);
    }
}
