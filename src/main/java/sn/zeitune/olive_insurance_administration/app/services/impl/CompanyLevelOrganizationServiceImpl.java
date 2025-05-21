package sn.zeitune.olive_insurance_administration.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.olive_insurance_administration.app.dto.requests.CompanyLevelOrganizationRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.CompanyLevelOrganizationResponseDTO;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.Company;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.CompanyLevelOrganization;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.PointOfSale;
import sn.zeitune.olive_insurance_administration.app.exceptions.NotFoundException;
import sn.zeitune.olive_insurance_administration.app.mappers.CompanyLevelOrganizationMapper;
import sn.zeitune.olive_insurance_administration.app.repositories.CompanyLevelOrganizationRepository;
import sn.zeitune.olive_insurance_administration.app.repositories.CompanyRepository;
import sn.zeitune.olive_insurance_administration.app.repositories.PointOfSaleRepository;
import sn.zeitune.olive_insurance_administration.app.services.CompanyLevelOrganizationService;
import sn.zeitune.olive_insurance_administration.app.specifications.CompanyLevelOrganizationSpecification;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyLevelOrganizationServiceImpl implements CompanyLevelOrganizationService {

    private final PointOfSaleRepository pointOfSaleRepository;
    private final CompanyRepository companyRepository;
    private final CompanyLevelOrganizationRepository repository;

    @Override
    public CompanyLevelOrganizationResponseDTO create(CompanyLevelOrganizationRequestDTO dto, UUID companyId) {
        Company company = companyRepository.findByUuid(companyId)
                .orElseThrow(() -> new NotFoundException("Company not found"));

        Set<PointOfSale> pointsOfSales = new HashSet<>();
        if (dto.pointsOfSaleIds() != null) {
            for (UUID pointOfSaleUuid : dto.pointsOfSaleIds()) {
                PointOfSale pointOfSale = pointOfSaleRepository.findByUuid(pointOfSaleUuid)
                        .orElseThrow(() -> new NotFoundException("Point of sale not found"));
                pointsOfSales.add(pointOfSale);
            }
        }
        CompanyLevelOrganization entity = new CompanyLevelOrganization();
        CompanyLevelOrganizationMapper.map(dto, company, pointsOfSales, entity);

        return CompanyLevelOrganizationMapper.map(repository.save(entity));
    }

    @Override
    public CompanyLevelOrganizationResponseDTO update(UUID uuid, CompanyLevelOrganizationRequestDTO dto) {
        CompanyLevelOrganization entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Company-level organization not found"));

        Set<PointOfSale> pointsOfSales = new HashSet<>();
        // Clear existing points of sale
        entity.getPointsOfSale().clear();
        if (dto.pointsOfSaleIds() != null) {
            for (UUID pointOfSaleUuid : dto.pointsOfSaleIds()) {
                PointOfSale pointOfSale = pointOfSaleRepository.findByUuid(pointOfSaleUuid)
                        .orElseThrow(() -> new NotFoundException("Point of sale not found"));
                pointsOfSales.add(pointOfSale);
            }
        }

        CompanyLevelOrganizationMapper.map(dto, entity.getCompany(), pointsOfSales, entity);

        return CompanyLevelOrganizationMapper.map(repository.save(entity));
    }

    @Override
    public void delete(UUID uuid) {
        CompanyLevelOrganization entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Company-level organization not found"));
        repository.delete(entity);
    }

    @Override
    public CompanyLevelOrganizationResponseDTO getByUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .map(CompanyLevelOrganizationMapper::map)
                .orElseThrow(() -> new NotFoundException("Company-level organization not found"));
    }

    @Override
    public Page<CompanyLevelOrganizationResponseDTO> search(UUID companyUuid, String name, Pageable pageable) {
        Specification<CompanyLevelOrganization> spec =
                CompanyLevelOrganizationSpecification.withFilters(companyUuid, name);

        return repository.findAll(spec, pageable)
                .map(CompanyLevelOrganizationMapper::map);
    }
}
