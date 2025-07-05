package sn.zeitune.olive_insurance_administration.app.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.zeitune.olive_insurance_administration.app.clients.UserClient;
import sn.zeitune.olive_insurance_administration.app.dto.external.CreateUserRequest;
import sn.zeitune.olive_insurance_administration.app.dto.requests.MarketLevelOrganizationRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.MarketLevelOrganizationResponseDTO;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.Company;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.CompanyMarketLevelOrganizations;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.ManagementEntity;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.MarketLevelOrganization;
import sn.zeitune.olive_insurance_administration.app.exceptions.NotFoundException;
import sn.zeitune.olive_insurance_administration.app.mappers.MarketLevelOrganizationMapper;
import sn.zeitune.olive_insurance_administration.app.repositories.CompanyMarketLevelOrganizationRepository;
import sn.zeitune.olive_insurance_administration.app.repositories.CompanyRepository;
import sn.zeitune.olive_insurance_administration.app.repositories.MarketLevelOrganizationRepository;
import sn.zeitune.olive_insurance_administration.app.services.MarketLevelOrganizationService;
import sn.zeitune.olive_insurance_administration.app.specifications.MarketLevelOrganizationSpecification;
import sn.zeitune.olive_insurance_administration.enums.ManagementEntityType;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MarketLevelOrganizationServiceImpl implements MarketLevelOrganizationService {

    private final MarketLevelOrganizationRepository repository;
    private final UserClient userClient;
    private final CompanyRepository companyRepository;
    private final CompanyMarketLevelOrganizationRepository associationRepository;

    @Override
    public MarketLevelOrganizationResponseDTO create(MarketLevelOrganizationRequestDTO dto) {

        MarketLevelOrganization organization = MarketLevelOrganizationMapper.map(dto, new MarketLevelOrganization());

        // Check if an organization with the same name already exists
        if (repository.existsByNameOrEmail(organization.getName(), organization.getEmail())) {
            throw new IllegalArgumentException("An organization with the same name or email already exists");
        }

        MarketLevelOrganization saved = repository.save(organization);

        if (saved.getEmail() != null && !saved.getEmail().isBlank()) {
            CreateUserRequest userRequest = new CreateUserRequest(
                    saved.getEmail(),
                    saved.getName(),
                    saved.getUuid(),
                    ManagementEntityType.MARKET_LEVEL_ORGANIZATION,
                    ManagementEntityType.MARKET_LEVEL_ORGANIZATION
            );
            try {
                userClient.createUser(userRequest);
            } catch (Exception e) {
                System.err.println("Failed to create user: " + e.getMessage());
            }
        }

        return MarketLevelOrganizationMapper.map(saved);
    }


    @Override
    public MarketLevelOrganizationResponseDTO update(UUID uuid, MarketLevelOrganizationRequestDTO dto) {
        MarketLevelOrganization entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Market-level org not found"));
        MarketLevelOrganizationMapper.map(dto, entity);
        return MarketLevelOrganizationMapper.map(repository.save(entity));
    }

    @Override
    public void delete(UUID uuid) {
        MarketLevelOrganization entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Market-level org not found"));
        repository.delete(entity);
    }

    @Override
    public MarketLevelOrganizationResponseDTO getByUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .map(MarketLevelOrganizationMapper::map)
                .orElseThrow(() -> new NotFoundException("Market-level org not found"));
    }

    @Override
    public List<MarketLevelOrganizationResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(MarketLevelOrganizationMapper::map)
                .toList();
    }

    @Override
    public Page<MarketLevelOrganizationResponseDTO> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(MarketLevelOrganizationMapper::map);
    }

    @Override
    public Page<MarketLevelOrganizationResponseDTO> search(String name, String email, String acronym, Pageable pageable) {
        return repository
                .findAll(MarketLevelOrganizationSpecification.withFilters(name, email, acronym), pageable)
                .map(MarketLevelOrganizationMapper::map);
    }

    @Override
    public void assignCompanies(UUID organizationUuid, Set<UUID> companyUuids) {
        MarketLevelOrganization org = repository.findByUuid(organizationUuid)
                .orElseThrow(() -> new NotFoundException("Market-level organization not found"));

        List<Company> companies = companyRepository.findAllByUuidIn(companyUuids);
        if (companies.isEmpty()) throw new NotFoundException("No valid companies found");

        Set<CompanyMarketLevelOrganizations> associations = companies.stream()
                .map(company -> CompanyMarketLevelOrganizations.builder()
                        .company(company)
                        .marketLevelOrganization(org)
                        .build())
                .collect(Collectors.toSet());

        associationRepository.saveAll(associations);
    }

    @Override
    public void removeCompany(UUID orgUuid, UUID companyUuid) {
        MarketLevelOrganization org = repository.findByUuid(orgUuid)
                .orElseThrow(() -> new NotFoundException("Market-level organization not found"));

        Company company = companyRepository.findByUuid(companyUuid)
                .orElseThrow(() -> new NotFoundException("Company not found"));

        Optional<CompanyMarketLevelOrganizations> association =
                associationRepository.findByCompanyAndMarketLevelOrganization(company, org);

        if (association.isEmpty()) {
            throw new NotFoundException("Association not found between this organization and company");
        }

        associationRepository.delete(association.get());
    }

    @Override
    public Page<MarketLevelOrganizationResponseDTO> getLinkedMarketLevelOrganizations(UUID managementEntity, Pageable pageable) {
        Company company = companyRepository.findByUuid(managementEntity)
                .orElseThrow(() -> new NotFoundException("Management entity not found"));
        return associationRepository
                .findAllByCompany(company, pageable)
                .map(CompanyMarketLevelOrganizations::getMarketLevelOrganization)
                .map(MarketLevelOrganizationMapper::map);
    }

    @Override
    public MarketLevelOrganizationResponseDTO getByName(String name) {
        MarketLevelOrganization organization = repository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Market-level org not found"));
        return MarketLevelOrganizationMapper.map(organization);
    }

}
