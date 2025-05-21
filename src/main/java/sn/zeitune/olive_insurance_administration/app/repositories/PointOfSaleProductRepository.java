package sn.zeitune.olive_insurance_administration.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sn.zeitune.olive_insurance_administration.app.entities.PointOfSaleProduct;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.PointOfSale;

import java.util.Optional;
import java.util.UUID;

public interface PointOfSaleProductRepository extends JpaRepository<PointOfSaleProduct, Long> {

    Optional<PointOfSaleProduct> findByUuid(UUID uuid);
    Page<PointOfSaleProduct> findByCompany(UUID company, Pageable pageable);
    Page<PointOfSaleProduct> findAllByPointOfSale(PointOfSale pointOfSale, Pageable pageable);
}
