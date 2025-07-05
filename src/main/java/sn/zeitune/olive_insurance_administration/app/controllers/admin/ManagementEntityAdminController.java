package sn.zeitune.olive_insurance_administration.app.controllers.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.olive_insurance_administration.app.dto.requests.BrokerPointOfSaleRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.requests.CompanyRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.requests.MarketLevelOrganizationRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.CompanyResponseDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.MarketLevelOrganizationResponseDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.PointOfSaleResponseDTO;
import sn.zeitune.olive_insurance_administration.app.services.CompanyService;
import sn.zeitune.olive_insurance_administration.app.services.MarketLevelOrganizationService;
import sn.zeitune.olive_insurance_administration.app.services.PointOfSaleService;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ManagementEntityAdminController {

    private final CompanyService companyService;
    private final MarketLevelOrganizationService marketLevelOrganizationService;
    private final PointOfSaleService pointOfSaleService;


    @GetMapping("/companies")
    public Page<CompanyResponseDTO> getAllCompanies(Pageable pageable) {
        return companyService.getAllCompanies(pageable);
    }

    @PostMapping("/companies")
    public ResponseEntity<CompanyResponseDTO> create(@Valid @RequestBody CompanyRequestDTO dto) {
        return ResponseEntity.ok(companyService.create(dto));
    }

    @GetMapping("/market-level-organizations")
    public Page<MarketLevelOrganizationResponseDTO> getAllMarketLevelOrganizationResponseDTO(Pageable pageable) {
        return marketLevelOrganizationService.getAll(pageable);
    }

    @PostMapping("/market-level-organizations")
    public ResponseEntity<MarketLevelOrganizationResponseDTO> createMarketLevelOrganization(@Valid @RequestBody MarketLevelOrganizationRequestDTO dto) {
        return ResponseEntity.ok(marketLevelOrganizationService.create(dto));
    }


    @GetMapping("/brokers")
    public Page<PointOfSaleResponseDTO> getAllPointOfSale(Pageable pageable) {
        return pointOfSaleService.getAll(pageable);
    }

    @PostMapping("/brokers")
    public ResponseEntity<PointOfSaleResponseDTO> createPointOfSale(@Valid @RequestBody BrokerPointOfSaleRequestDTO dto) {
        return ResponseEntity.ok(pointOfSaleService.createBrokerPdv(dto));
    }
}
