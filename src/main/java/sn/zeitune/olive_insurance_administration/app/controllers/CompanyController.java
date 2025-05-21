package sn.zeitune.olive_insurance_administration.app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.olive_insurance_administration.app.dto.requests.CompanyRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.CompanyResponseDTO;
import sn.zeitune.olive_insurance_administration.app.services.CompanyService;
import sn.zeitune.olive_insurance_administration.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/linked")
    public ResponseEntity<Page<CompanyResponseDTO>> getLinkedCompanies(
            @PageableDefault Pageable pageable,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        if (employee == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(companyService.getAllLinkedCompanies(employee.getManagementEntity(), pageable));
    }


    @PutMapping("/{uuid}")
    public ResponseEntity<CompanyResponseDTO> update(@PathVariable UUID uuid, @Valid @RequestBody CompanyRequestDTO dto) {
        return ResponseEntity.ok(companyService.update(uuid, dto));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        companyService.delete(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CompanyResponseDTO> get(@PathVariable UUID uuid) {
        return ResponseEntity.ok(companyService.getByUuid(uuid));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CompanyResponseDTO>> getAll() {
        return ResponseEntity.ok(companyService.getAll());
    }

    @GetMapping
    public ResponseEntity<Page<CompanyResponseDTO>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String acronym,
            @RequestParam(required = false) String email,
            @PageableDefault(size = 10, sort = "name") Pageable pageable
    ) {
        return ResponseEntity.ok(companyService.search(name, acronym, email, pageable));
    }

}
