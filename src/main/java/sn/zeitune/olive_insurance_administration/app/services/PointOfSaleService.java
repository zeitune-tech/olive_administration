package sn.zeitune.olive_insurance_administration.app.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.zeitune.olive_insurance_administration.app.dto.requests.BrokerPointOfSaleRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.requests.CompanyPointOfSaleRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.PointOfSaleResponseDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.SimpleCompanyDTO;
import sn.zeitune.olive_insurance_administration.enums.PointOfSaleType;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface PointOfSaleService {
    PointOfSaleResponseDTO createBrokerPdv(BrokerPointOfSaleRequestDTO dto);
    PointOfSaleResponseDTO createCompanyPdv(CompanyPointOfSaleRequestDTO dto, UUID managementEntity);

    PointOfSaleResponseDTO update(UUID uuid, CompanyPointOfSaleRequestDTO dto);
    PointOfSaleResponseDTO getByUuid(UUID uuid);
    List<PointOfSaleResponseDTO> getAll();
    Page<PointOfSaleResponseDTO> search(String name, PointOfSaleType type, Pageable pageable);
    Page<PointOfSaleResponseDTO> searchByCompany(UUID companyUuid, String name, Pageable pageable);
    Page<PointOfSaleResponseDTO> searchBrokers(String name, PointOfSaleType type, Pageable pageable);
    Page<PointOfSaleResponseDTO> searchBrokersByCompany(UUID companyUuid, String name, PointOfSaleType type, Pageable pageable);
    void assignCompaniesToBrokerPdv(UUID brokerPdvUuid, Set<UUID> companyUuids);
    void removeCompanyFromBrokerPdv(UUID brokerPdvUuid, UUID companyUuid);
    List<SimpleCompanyDTO> getCompaniesOfBroker(UUID brokerPdvUuid);
}

