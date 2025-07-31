package sn.zeitune.olive_insurance_administration.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.olive_insurance_administration.app.entities.ContributorType;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.ManagementEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContributorTypeRepository extends JpaRepository<ContributorType, Long>, JpaSpecificationExecutor<ContributorType> {

    Optional<ContributorType> findByUuid(UUID uuid);
    List<ContributorType> findByManagementEntity(ManagementEntity managementEntity);

}
