package sn.zeitune.olive_insurance_administration.app.entities;

import jakarta.persistence.*;
import lombok.*;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.PointOfSale;
import sn.zeitune.olive_insurance_administration.app.entities.product.Product;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "points_de_vente_produits")
public class PointOfSaleProduct {

    @Id
    @Column(name = "code_pdv_prod")
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

    @ManyToOne
    @JoinColumn(name = "code_pdvc", referencedColumnName = "code_entite", nullable = false)
    private PointOfSale pointOfSale;

    @Column(nullable = false, name = "code_comp")
    private UUID company;

    @ManyToOne
    @JoinColumn(name = "code_produit", referencedColumnName = "code_produit", nullable = false)
    private Product product;
}
