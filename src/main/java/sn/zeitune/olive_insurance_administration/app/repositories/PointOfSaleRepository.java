package sn.zeitune.olive_insurance_administration.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.PointOfSale;

import java.util.Optional;
import java.util.UUID;

public interface PointOfSaleRepository extends JpaRepository<PointOfSale, Long> {
    Optional<PointOfSale> findByUuid(UUID uuid);
}

