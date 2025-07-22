package sn.zeitune.olive_insurance_administration.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.olive_insurance_administration.app.dto.requests.ClosureRequest;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ClosureResponse;
import sn.zeitune.olive_insurance_administration.app.entities.Closure;
import sn.zeitune.olive_insurance_administration.app.mappers.ClosureMapper;
import sn.zeitune.olive_insurance_administration.app.repositories.ClosureRepository;
import sn.zeitune.olive_insurance_administration.app.services.ClosureService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClosureServiceImpl implements ClosureService {

    private final ClosureRepository closureRepository;

    @Override
    public ClosureResponse create(ClosureRequest request, UUID managementEntity) {
        Closure closure = ClosureMapper.map(request);
        closure.setManagementEntity(managementEntity);

        closure = closureRepository.save(closure);
        return ClosureMapper.map(closure);
    }

    @Override
    public ClosureResponse getByUuid(UUID uuid) {
        Closure closure = closureRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Closure not found with UUID: " + uuid));
        return ClosureMapper.map(closure);
    }

    @Override
    public List<ClosureResponse> getAll(UUID managementEntity) {
        return closureRepository.findAllByManagementEntity(managementEntity).stream()
                .map(ClosureMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public ClosureResponse update(UUID uuid, ClosureRequest request) {
        Closure existing = closureRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Closure not found with UUID: " + uuid));

        existing.setType(request.type());
        existing.setDate(request.date());

        return ClosureMapper.map(closureRepository.save(existing));
    }

    @Override
    public void delete(UUID uuid) {
        Closure closure = closureRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Closure not found with UUID: " + uuid));
        closureRepository.delete(closure);
    }
}
