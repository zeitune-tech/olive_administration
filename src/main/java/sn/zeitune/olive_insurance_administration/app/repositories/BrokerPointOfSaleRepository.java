package sn.zeitune.olive_insurance_administration.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.BrokerPointOfSale;

import java.util.Optional;
import java.util.UUID;

public interface BrokerPointOfSaleRepository extends
        JpaRepository<BrokerPointOfSale, Long>,
        JpaSpecificationExecutor<BrokerPointOfSale> {

    Optional<BrokerPointOfSale> findByUuid(UUID uuid);


    boolean existsByNameOrEmail(String name, String email);
}
