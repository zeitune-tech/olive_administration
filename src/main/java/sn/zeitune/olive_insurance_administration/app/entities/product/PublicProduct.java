package sn.zeitune.olive_insurance_administration.app.entities.product;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.Company;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("PUBLIC")
public class PublicProduct extends Product {

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "compagnies_public_produits",
            joinColumns = @JoinColumn(name = "code_produit"),
            inverseJoinColumns = @JoinColumn(name = "code_comp")
    )
    private Set<Company> sharedWithCompanies = new HashSet<>();
}
