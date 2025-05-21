package sn.zeitune.olive_insurance_administration.app.managemententity.pointofsale;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import sn.zeitune.olive_insurance_administration.app.managemententity.CompanyLevelOrganization;
import sn.zeitune.olive_insurance_administration.app.managemententity.ManagementEntity;
import sn.zeitune.olive_insurance_administration.enums.ManagementEntityType;
import sn.zeitune.olive_insurance_administration.enums.PointOfSaleType;

import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@Entity
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

    protected PointOfSale() {
        super();
    }

    public PointOfSale(
            String name, String acronym, String Email, String phone,
            String address, String logo, String fax, String gsm,
            PointOfSaleType typePointOfSale
    ) {
        super(name, acronym, Email, phone, address, logo, fax, gsm, ManagementEntityType.POINT_OF_SALE);
        this.typePointOfSale = typePointOfSale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PointOfSale that)) return false;
        if (!super.equals(o)) return false;
        return typePointOfSale == that.typePointOfSale;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId(), typePointOfSale);
    }
}
