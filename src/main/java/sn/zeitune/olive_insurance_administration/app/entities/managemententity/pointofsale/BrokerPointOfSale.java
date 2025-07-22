package sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.Company;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "points_de_vente_courtier")
public class BrokerPointOfSale extends PointOfSale{

    @ManyToMany(mappedBy = "brokerPointOfSales")
    private Set<Company> companies = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode()) ^ Objects.hashCode(super.hashCode());
    }
}
