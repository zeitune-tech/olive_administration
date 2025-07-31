package sn.zeitune.olive_insurance_administration.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sn.zeitune.olive_insurance_administration.app.dto.requests.ContributorRequest;
import sn.zeitune.olive_insurance_administration.app.dto.requests.ContributorTypeRequest;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ContributorResponse;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ContributorTypeResponse;
import sn.zeitune.olive_insurance_administration.app.entities.Contributor;
import sn.zeitune.olive_insurance_administration.app.entities.ContributorType;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.ManagementEntity;
import sn.zeitune.olive_insurance_administration.app.mappers.ContributorMapper;
import sn.zeitune.olive_insurance_administration.app.repositories.ContributorRepository;
import sn.zeitune.olive_insurance_administration.app.repositories.ContributorTypeRepository;
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
    private final ContributorTypeRepository contributorTypeRepository;
    private final ManagementEntityRepository managementEntityRepository;

    @Override
    public ContributorResponse create(ContributorRequest request, UUID managementEntityId) {

        ManagementEntity managementEntity = managementEntityRepository.findByUuid(managementEntityId)
                .orElseThrow(() -> new IllegalArgumentException("Management entity not found"));

        ContributorType contributorType = contributorTypeRepository.findByUuid(request.contributorTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Contributor type not found"));

        Contributor entity = ContributorMapper.map(request, contributorType);
        entity.setManagementEntity(managementEntity);
        entity = repository.save(entity);
        return ContributorMapper.map(entity, ContributorTypeResponse.builder()
                .id(contributorType.getUuid())
                .label(contributorType.getLabel())
                .build());
    }

    @Override
    public ContributorResponse update(UUID uuid, ContributorRequest request) {
        Contributor entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Contributor not found"));


        entity = repository.save(entity);
        return ContributorMapper.map(entity, ContributorTypeResponse.builder()
                .id(entity.getContributorType().getUuid())
                .label(entity.getContributorType().getLabel())
                .build());
    }

    @Override
    public ContributorResponse getByUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .map(contributor -> ContributorMapper.map(contributor, ContributorTypeResponse.builder()
                        .id(contributor.getContributorType().getUuid())
                        .label(contributor.getContributorType().getLabel())
                        .build()))
                .orElseThrow(() -> new IllegalArgumentException("Contributor not found"));
    }

    @Override
    public List<ContributorResponse> getAll() {
        return repository.findAll().stream()
                .map(contributor -> ContributorMapper.map(contributor, ContributorTypeResponse.builder()
                        .id(contributor.getContributorType().getUuid())
                        .label(contributor.getContributorType().getLabel())
                        .build()))
                .collect(Collectors.toList());
    }


    @Override
    public ContributorTypeResponse createContributorType(ContributorTypeRequest request, UUID managementEntityId) {

        ManagementEntity managementEntity = managementEntityRepository.findByUuid(managementEntityId)
                .orElseThrow(() -> new IllegalArgumentException("Management entity not found"));
        // Assuming ContributorType is a valid entity and ContributorTypeMapper exists
        ContributorType contributorType = ContributorType.builder()
                .label(request.label())
                .build();
        contributorType.setManagementEntity(managementEntity);

        ContributorType savedContributorType = contributorTypeRepository.save(contributorType);

        return ContributorTypeResponse.builder()
                .id(savedContributorType.getUuid())
                .label(savedContributorType.getLabel())
                .build();
    }

    @Override
    public ContributorTypeResponse updateContributorType(ContributorTypeRequest request, UUID managementEntityId, UUID uuid) {

        ManagementEntity managementEntity = managementEntityRepository.findByUuid(managementEntityId)
                .orElseThrow(() -> new IllegalArgumentException("Management entity not found"));

        ContributorType contributorType = contributorTypeRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Contributor Type not found"));

        contributorType.setLabel(request.label());
        contributorType.setManagementEntity(managementEntity);

        ContributorType updatedContributorType = contributorTypeRepository.save(contributorType);

        return ContributorTypeResponse.builder()
                .id(updatedContributorType.getUuid())
                .label(updatedContributorType.getLabel())
                .build();
    }

    @Override
    public ContributorTypeResponse getContributorTypeByUuid(UUID uuid) {
        return contributorTypeRepository.findByUuid(uuid)
                .map(contributorType -> ContributorTypeResponse.builder()
                        .id(contributorType.getUuid())
                        .label(contributorType.getLabel())
                        .build())
                .orElseThrow(() -> new RuntimeException("Contributor Type not found"));
    }

    @Override
    public List<ContributorTypeResponse> getAllContributorTypes(UUID managementEntityId) {
        ManagementEntity managementEntity = managementEntityRepository.findByUuid(managementEntityId)
                .orElseThrow(() -> new IllegalArgumentException("Management entity not found"));

        return contributorTypeRepository.findByManagementEntity(managementEntity)
                .stream()
                .map(contributorType -> ContributorTypeResponse.builder()
                        .id(contributorType.getUuid())
                        .label(contributorType.getLabel())
                        .build())
                .toList();
    }

    @Override
    public void delete(UUID uuid) {
        Contributor entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Contributor not found"));
        repository.delete(entity);
    }
}
