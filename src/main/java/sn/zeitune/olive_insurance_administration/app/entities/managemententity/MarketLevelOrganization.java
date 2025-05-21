package sn.zeitune.olive_insurance_administration.app.managemententity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import sn.zeitune.olive_insurance_administration.enums.ManagementEntityType;

import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "organisations_niveau_marche")
public class MarketLevelOrganization extends ManagementEntity {

    @OneToMany(mappedBy = "marketLevelOrganization")
    private Set<CompanyMarketLevelOrganizations> companyMarketLevelOrganizations;

    protected MarketLevelOrganization () {
        super();
    }

    @Builder
    public MarketLevelOrganization(
            String name, String acronym, String email, String phone, String address, String logo,
            String fax, String gsm, ManagementEntityType type
    ) {
        super(name, acronym, email, phone, address, logo, fax, gsm, type);
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode()) ^ Objects.hashCode(super.hashCode());
    }
}
