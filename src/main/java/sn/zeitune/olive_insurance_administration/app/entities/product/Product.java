package sn.zeitune.olive_insurance_administration.app.entities.product;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sn.zeitune.olive_insurance_administration.app.entities.Branch;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.ManagementEntity;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produits")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "visibilite", discriminatorType = DiscriminatorType.STRING)
public abstract class Product {

    @Id
    @Column(name = "code_produit")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;

    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }

    @Column(name = "designation")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "code_bran")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "code_entite")
    private ManagementEntity owner;

    @Column(name = "min_risque")
    private Integer minRisk;
    @Column(name = "max_risque")
    private Integer maxRisk;
    @Column(name = "nombre_min_garantie")
    private Integer minimumGuaranteeNumber;
    @Column(name = "cat_flotte")
    private Boolean fleet;
    @Column(name = "flag_redu_majo")
    private boolean hasReduction;

    @Column(name = "visibilite", insertable = false, updatable = false)
    private String visibility;
}