package sn.zeitune.oliveinsurancesettings.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import sn.zeitune.oliveinsurancesettings.enums.ContributorType;

import java.util.UUID;

@Data
@Builder
@Entity(name = "contributor")
@NoArgsConstructor
@AllArgsConstructor
public class Contributor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;

    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }

    @Column(name = "designation", nullable = false)
    private String designation;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ContributorType type;


    private UUID pointOfSale;
    private UUID product;
    private UUID managementEntity;
}
