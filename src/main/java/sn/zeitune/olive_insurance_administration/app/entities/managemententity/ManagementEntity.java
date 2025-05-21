package sn.zeitune.olive_insurance_administration.app.managemententity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.zeitune.olive_insurance_administration.enums.ManagementEntityType;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "entites_de_gestion")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ManagementEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "code_entite")
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;

    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }

    @Column(name = "nom", nullable = false)
    private String name;
    @Column(name = "sigle")
    private String acronym;
    @Column(name = "email")
    private String email;
    @Column(name = "tel")
    private String phone;
    @Column(name = "adresse")
    private String address;
    @Column(name = "logo")
    private String logo;
    @Column(name = "fax")
    private String fax;
    @Column(name = "gsm")
    private String gsm;
    @Column(name = "type_entite")
    private ManagementEntityType type;


    protected ManagementEntity(
            String name, String acronym, String email, String phone, String address, String logo,
            String fax, String gsm, ManagementEntityType type
    ) {
        this.name = name;
        this.acronym = acronym;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.logo = logo;
        this.fax = fax;
        this.gsm = gsm;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManagementEntity managementEntity = (ManagementEntity) o;
        return uuid != null && uuid.equals(managementEntity.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
