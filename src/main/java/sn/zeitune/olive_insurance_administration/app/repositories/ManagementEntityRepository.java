package sn.zeitune.olive_insurance_administration.app.repositories;

import io.netty.resolver.dns.DnsServerAddresses;
import org.springframework.data.jpa.repository.JpaRepository;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.ManagementEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ManagementEntityRepository extends JpaRepository<ManagementEntity, Long> {
    Optional<ManagementEntity> findByUuid(UUID uuid);

    List<ManagementEntity> findAllByUuidIn(List<UUID> managementEntityIds);
}
