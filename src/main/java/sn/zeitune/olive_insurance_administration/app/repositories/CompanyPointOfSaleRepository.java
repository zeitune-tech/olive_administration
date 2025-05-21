package sn.zeitune.olive_insurance_administration.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.CompanyPointOfSale;

import java.util.Optional;
import java.util.UUID;

public interface CompanyPointOfSaleRepository extends
        JpaRepository<CompanyPointOfSale, Long>,
        JpaSpecificationExecutor<CompanyPointOfSale> {

    Optional<CompanyPointOfSale> findByUuid(UUID uuid);
}


