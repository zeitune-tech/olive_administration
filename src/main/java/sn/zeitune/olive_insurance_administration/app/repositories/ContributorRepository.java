package sn.zeitune.olive_insurance_administration.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.olive_insurance_administration.app.entities.Contributor;

import java.util.Optional;
import java.util.UUID;

public interface ContributorRepository extends JpaRepository<Contributor, Long>, JpaSpecificationExecutor<Contributor> {
    Optional<Contributor> findByUuid(UUID uuid);
}
