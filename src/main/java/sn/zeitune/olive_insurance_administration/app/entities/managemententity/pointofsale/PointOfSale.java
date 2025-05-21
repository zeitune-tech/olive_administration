package sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.CompanyLevelOrganization;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.ManagementEntity;
import sn.zeitune.olive_insurance_administration.enums.ManagementEntityType;
import sn.zeitune.olive_insurance_administration.enums.PointOfSaleType;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "points_de_vente")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PointOfSale extends ManagementEntity {

    @Column(name = "type_pdv")
    private PointOfSaleType typePointOfSale;

    @ManyToMany
    @JoinTable(
            name = "points_de_vente_organisations_niveau_compagnie",
            joinColumns = @JoinColumn(name = "code_pdv"),
            inverseJoinColumns = @JoinColumn(name = "code_orga_comp")
    )
    private Set<CompanyLevelOrganization> companyLevelOrganizations;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PointOfSale that)) return false;
        if (!super.equals(o)) return false;
        return typePointOfSale == that.typePointOfSale;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getUuid(), super.getId(), typePointOfSale);
    }
}
