package sn.zeitune.olive_insurance_administration.app.controllers.interservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ProductResponseDTO;
import sn.zeitune.olive_insurance_administration.app.services.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/interservices/products")
@RequiredArgsConstructor
public class ProductInterServiceController {

    private final ProductService productService;

    @GetMapping("/{uuid}")
    public ResponseEntity<List<ProductResponseDTO>> getByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(productService.getByManagementEntityUuid(uuid));
    }
}
