package sn.zeitune.olive_insurance_administration.app.entities.managemententity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.BrokerPointOfSale;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.CompanyPointOfSale;
import sn.zeitune.olive_insurance_administration.enums.ManagementEntityType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "compagnies")
public class Company extends ManagementEntity{

    @JoinTable(
            name = "compagnies_points_de_vente_coutiers",
            joinColumns = @JoinColumn(name = "code_comp"),
            inverseJoinColumns = @JoinColumn(name = "code_pdv_coutier")
    )
    @ManyToMany
    private Set<BrokerPointOfSale> brokerPointOfSales;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private Set<CompanyPointOfSale> companyPointOfSales = new HashSet<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private Set<CompanyMarketLevelOrganizations> companyMarketLevelOrganizations = new HashSet<>();

    @Column(name = "stat_jur")
    private String legalStatus;
    @Column(name = "num_reg")
    private String registrationNumber;


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId(), ManagementEntityType.COMPANY);
    }

    @Override
    public String toString() {
        return "Company{" +
                "name" + super.getName() +
                ", acronym" + super.getAcronym() +
                ", email" + super.getEmail() +
                ", phone" + super.getPhone() +
                ", address" + super.getAddress() +
        '}';
    }
}
