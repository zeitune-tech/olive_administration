package sn.zeitune.olive_insurance_administration.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.CompanyMarketLevelOrganizations;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.Company;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.ManagementEntity;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.MarketLevelOrganization;
import sn.zeitune.olive_insurance_administration.app.mappers.CompanyMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyMarketLevelOrganizationRepository
        extends JpaRepository<CompanyMarketLevelOrganizations, Long> {

    Optional<CompanyMarketLevelOrganizations> findByUuid(UUID uuid);
    void deleteByCompanyAndMarketLevelOrganization(Company company, MarketLevelOrganization organization);
    Optional<CompanyMarketLevelOrganizations> findByCompanyAndMarketLevelOrganization(Company company, MarketLevelOrganization organization);

    boolean existsByCompanyAndMarketLevelOrganization(Company company, MarketLevelOrganization organization);

    List<CompanyMarketLevelOrganizations> findAllByMarketLevelOrganization(MarketLevelOrganization organization);

    Page<CompanyMarketLevelOrganizations> findAllByCompany(Company company, Pageable pageable);

    Page<CompanyMarketLevelOrganizations> findAllByMarketLevelOrganization(MarketLevelOrganization entity, Pageable pageable);
}
