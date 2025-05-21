package sn.zeitune.olive_insurance_administration.app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.olive_insurance_administration.app.dto.requests.ProductRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ProductResponseDTO;
import sn.zeitune.olive_insurance_administration.app.services.ProductService;
import sn.zeitune.olive_insurance_administration.security.Employee;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/app/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(
            @Valid @RequestBody ProductRequestDTO dto,
            Authentication authentication
    ) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Employee employee = (Employee) userDetails;

        return ResponseEntity.ok(productService.createProduct(dto, employee.getManagementEntity()));
    }


    @PutMapping("/{uuid}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable UUID uuid,
            @Valid @RequestBody ProductRequestDTO dto
    ) {
        return ResponseEntity.ok(productService.updateProduct(uuid, dto));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProductResponseDTO> getByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(productService.getByUuid(uuid));
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String branchName,
            @RequestParam(required = false) Integer minRisk,
            @RequestParam(required = false) Integer maxRisk,
            @RequestParam(required = false) Boolean fleet,
            @RequestParam(required = false) Boolean hasReduction,
            @PageableDefault(size = 10) Pageable pageable,
            Authentication authentication
    ) {

        Employee employee = (Employee) authentication.getPrincipal();
        Page<ProductResponseDTO> result = productService.search(
                name, branchName, minRisk, maxRisk, fleet, hasReduction, pageable, employee.getManagementEntity()
        );
        return ResponseEntity.ok(result);
    }


    @GetMapping("/all")
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @PatchMapping("/{uuid}/share")
    public ResponseEntity<Void> sharePublicProduct(
            @PathVariable UUID uuid,
            @RequestBody Set<UUID> companyUuids
    ) {
        productService.sharePublicProductWithCompanies(uuid, companyUuids);
        return ResponseEntity.noContent().build();
    }
}
