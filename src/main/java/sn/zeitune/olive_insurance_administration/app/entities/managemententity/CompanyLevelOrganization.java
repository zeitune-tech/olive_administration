package sn.zeitune.olive_insurance_administration.app.managemententity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.zeitune.olive_insurance_administration.app.managemententity.pointofsale.PointOfSale;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "organisations_niveau_compagnie")
public class CompanyLevelOrganization implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "code_orga_com")
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;

    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }

    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_comp")
    private Company company;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<PointOfSale> pointsOfSale;
}
