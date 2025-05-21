package sn.zeitune.olive_insurance_administration.app.entities.managemententity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sn.zeitune.olive_insurance_administration.enums.ManagementEntityType;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "organisations_niveau_marche")
public class MarketLevelOrganization extends ManagementEntity {

    @OneToMany(mappedBy = "marketLevelOrganization")
    private Set<CompanyMarketLevelOrganizations> companyMarketLevelOrganizations;

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId(), ManagementEntityType.MARKET_LEVEL_ORGANIZATION);
    }

    @Override
    public String toString() {
        return "MarketLevelOrganization{" +
                "name" + super.getName() +
                ", acronym" + super.getAcronym() +
                ", email" + super.getEmail() +
                ", phone" + super.getPhone() +
                ", address" + super.getAddress() +
                '}';
    }
}
