package sn.zeitune.olive_insurance_administration.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sn.zeitune.olive_insurance_administration.app.dto.requests.ContributorRequest;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ContributorResponse;
import sn.zeitune.olive_insurance_administration.app.entities.Contributor;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.ManagementEntity;
import sn.zeitune.olive_insurance_administration.app.mappers.ContributorMapper;
import sn.zeitune.olive_insurance_administration.app.repositories.ContributorRepository;
import sn.zeitune.olive_insurance_administration.app.repositories.ManagementEntityRepository;
import sn.zeitune.olive_insurance_administration.app.services.ContributorService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ContributorServiceImpl implements ContributorService {

    private final ContributorRepository repository;
    private final ManagementEntityRepository managementEntityRepository;

    @Override
    public ContributorResponse create(ContributorRequest request, UUID managementEntityId) {

        ManagementEntity managementEntity = managementEntityRepository.findByUuid(managementEntityId)
                .orElseThrow(() -> new IllegalArgumentException("Management entity not found"));

        Contributor entity = ContributorMapper.map(request);
        entity.setManagementEntity(managementEntity);
        entity = repository.save(entity);
        return ContributorMapper.map(entity);
    }

    @Override
    public ContributorResponse update(UUID uuid, ContributorRequest request) {
        Contributor entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Contributor not found"));


        entity = repository.save(entity);
        return ContributorMapper.map(entity);
    }

    @Override
    public ContributorResponse getByUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .map(ContributorMapper::map)
                .orElseThrow(() -> new IllegalArgumentException("Contributor not found"));
    }

    @Override
    public List<ContributorResponse> getAll() {
        return repository.findAll().stream()
                .map(ContributorMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID uuid) {
        Contributor entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Contributor not found"));
        repository.delete(entity);
    }
}
