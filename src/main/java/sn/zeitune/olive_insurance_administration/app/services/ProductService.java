package sn.zeitune.olive_insurance_administration.app.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.zeitune.olive_insurance_administration.app.dto.requests.ProductRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ProductResponseDTO;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO dto, UUID ownerUuid);
    ProductResponseDTO updateProduct(UUID uuid, ProductRequestDTO dto);
    void sharePublicProductWithCompanies(UUID productUuid, Set<UUID> companyUuids);
    ProductResponseDTO getByUuid(UUID uuid);
    List<ProductResponseDTO> getAll();

    List<ProductResponseDTO> getByManagementEntityUuid(UUID uuid);

    Page<ProductResponseDTO> search(
            String name,
            String branchUuid,
            Integer minRisk,
            Integer maxRisk,
            Boolean fleet,
            Boolean hasReduction,
            Pageable pageable,
            UUID ownerUuid
    );
}
