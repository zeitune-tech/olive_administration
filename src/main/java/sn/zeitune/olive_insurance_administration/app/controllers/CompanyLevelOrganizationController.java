package sn.zeitune.olive_insurance_administration.app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.olive_insurance_administration.app.dto.requests.CompanyLevelOrganizationRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.CompanyLevelOrganizationResponseDTO;
import sn.zeitune.olive_insurance_administration.app.services.CompanyLevelOrganizationService;
import sn.zeitune.olive_insurance_administration.security.Employee;

import java.util.UUID;

@RestController
@RequestMapping("/app/company-level-organizations")
@RequiredArgsConstructor
public class CompanyLevelOrganizationController {

    private final CompanyLevelOrganizationService service;

    @PostMapping
    public ResponseEntity<CompanyLevelOrganizationResponseDTO> create(
            @Valid @RequestBody CompanyLevelOrganizationRequestDTO dto,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        return ResponseEntity.ok(service.create(dto, employee.getManagementEntity()));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CompanyLevelOrganizationResponseDTO> update(
            @PathVariable UUID uuid,
            @Valid @RequestBody CompanyLevelOrganizationRequestDTO dto) {
        return ResponseEntity.ok(service.update(uuid, dto));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CompanyLevelOrganizationResponseDTO> get(@PathVariable UUID uuid) {
        return ResponseEntity.ok(service.getByUuid(uuid));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        service.delete(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<CompanyLevelOrganizationResponseDTO>> search(
            @RequestParam(required = false) String name,
            @PageableDefault(size = 10) Pageable pageable,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        return ResponseEntity.ok(service.search(employee.getManagementEntity(), name, pageable));
    }
}
