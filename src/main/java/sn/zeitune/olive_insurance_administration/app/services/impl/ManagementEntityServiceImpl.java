package sn.zeitune.olive_insurance_administration.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ManagementEntityResponse;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.ManagementEntity;
import sn.zeitune.olive_insurance_administration.app.mappers.ManagementEntityMapper;
import sn.zeitune.olive_insurance_administration.app.repositories.ManagementEntityRepository;
import sn.zeitune.olive_insurance_administration.app.services.ManagementEntityService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManagementEntityServiceImpl implements ManagementEntityService {

    private final ManagementEntityRepository managementEntityRepository;

    @Override
    public ManagementEntityResponse get(UUID uuid) {
        ManagementEntity managementEntity = managementEntityRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("ManagementEntity with uuid " + uuid + " not found"));

        return ManagementEntityMapper.map(managementEntity);
    }
}
