package sn.zeitune.olive_insurance_administration.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.CompanyLevelOrganization;

import java.util.Optional;
import java.util.UUID;

public interface CompanyLevelOrganizationRepository extends
        JpaRepository<CompanyLevelOrganization, Long>,
        JpaSpecificationExecutor<CompanyLevelOrganization> {

    Optional<CompanyLevelOrganization> findByUuid(UUID uuid);
}
