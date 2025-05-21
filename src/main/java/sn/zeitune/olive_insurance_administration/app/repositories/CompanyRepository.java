package sn.zeitune.olive_insurance_administration.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.Company;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.BrokerPointOfSale;
import sn.zeitune.olive_insurance_administration.app.mappers.CompanyMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {
    Optional<Company> findByUuid(UUID uuid);
    List<Company> findAllByUuidIn(Set<UUID> uuids);

    boolean existsByNameOrEmail(String name, String email);

    Optional<Company> findByName(String name);

    Page<Company> findAllByBrokerPointOfSalesContains(BrokerPointOfSale entity, Pageable pageable);
}
