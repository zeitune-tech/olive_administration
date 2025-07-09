package sn.zeitune.olive_insurance_administration.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ManagementEntityResponse;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.ManagementEntity;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.CompanyPointOfSale;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.PointOfSale;
import sn.zeitune.olive_insurance_administration.app.mappers.ManagementEntityMapper;
import sn.zeitune.olive_insurance_administration.app.repositories.ManagementEntityRepository;
import sn.zeitune.olive_insurance_administration.app.services.ManagementEntityService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManagementEntityServiceImpl implements ManagementEntityService {

    private final ManagementEntityRepository managementEntityRepository;

    @Override
    public ManagementEntityResponse get(UUID uuid) {
        ManagementEntity managementEntity = managementEntityRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("ManagementEntity with uuid " + uuid + " not found"));

        if (managementEntity instanceof CompanyPointOfSale pointOfSale) {
            return ManagementEntityMapper.map(managementEntity, ManagementEntityMapper.map(pointOfSale.getCompany()));
        }

        return ManagementEntityMapper.map(managementEntity);
    }

    @Override
    public List<ManagementEntityResponse> getAllByIds(List<UUID> managementEntityIds) {
        return managementEntityRepository.findAllByUuidIn(managementEntityIds)
                .stream()
                .map(ManagementEntityMapper::map)
                .toList();
    }
}
