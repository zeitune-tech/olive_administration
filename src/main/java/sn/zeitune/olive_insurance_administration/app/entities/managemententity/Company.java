package sn.zeitune.olive_insurance_administration.app.managemententity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import sn.zeitune.olive_insurance_administration.app.managemententity.pointofsale.BrokerPointOfSale;
import sn.zeitune.olive_insurance_administration.app.managemententity.pointofsale.CompanyPointOfSale;
import sn.zeitune.olive_insurance_administration.enums.ManagementEntityType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
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

    protected Company () {
        super();
    }

    @Builder
    public Company(
            String name, String acronym, String email, String phone, String address, String logo,
            String fax, String gsm, String legalStatus, String registrationNumber
    ) {
        super(name, acronym, email, phone, address, logo, fax, gsm, ManagementEntityType.COMPANY);
        this.legalStatus = legalStatus;
        this.registrationNumber = registrationNumber;
    }

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
