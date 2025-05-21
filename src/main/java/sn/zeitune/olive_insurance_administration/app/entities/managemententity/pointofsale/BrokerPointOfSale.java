package sn.zeitune.olive_insurance_administration.app.managemententity.pointofsale;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import sn.zeitune.olive_insurance_administration.app.managemententity.Company;
import sn.zeitune.olive_insurance_administration.enums.PointOfSaleType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "points_de_vente_courtier")
public class BrokerPointOfSale extends PointOfSale{

    @ManyToMany(mappedBy = "brokerPointOfSales")
    private Set<Company> companies = new HashSet<>();

    public BrokerPointOfSale() {
        super();
    }

    @Builder
    public BrokerPointOfSale(
            String name, String acronym, String email, String phone,
            String address, String logo,String fax, String gsm
    ) {
        super(name, acronym, email, phone, address, logo, fax, gsm, PointOfSaleType.BROKER);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode()) ^ Objects.hashCode(super.hashCode());
    }
}
