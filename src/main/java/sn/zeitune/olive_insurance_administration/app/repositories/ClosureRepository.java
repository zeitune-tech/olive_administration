package sn.zeitune.olive_insurance_administration.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.zeitune.olive_insurance_administration.app.entities.Closure;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClosureRepository extends JpaRepository<Closure, Long> {
    Optional<Closure> findByUuid(UUID uuid);

    List<Closure> findAllByManagementEntity(UUID managementEntity);
}