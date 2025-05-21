package sn.zeitune.olive_insurance_administration.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.MarketLevelOrganization;
import sn.zeitune.olive_insurance_administration.app.mappers.MarketLevelOrganizationMapper;

import java.util.Optional;
import java.util.UUID;

public interface MarketLevelOrganizationRepository extends
        JpaRepository<MarketLevelOrganization, Long>,
        JpaSpecificationExecutor<MarketLevelOrganization> {

    Optional<MarketLevelOrganization> findByUuid(UUID uuid);

    boolean existsByNameOrEmail(String name, String email);

    Optional<MarketLevelOrganization> findByName(String name);
}
