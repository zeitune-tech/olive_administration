package sn.zeitune.olive_insurance_administration.app.mappers;

import sn.zeitune.olive_insurance_administration.app.dto.requests.ProductRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ProductResponseDTO;
import sn.zeitune.olive_insurance_administration.app.entities.Branch;
import sn.zeitune.olive_insurance_administration.app.entities.product.Product;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.ManagementEntity;
import sn.zeitune.olive_insurance_administration.app.entities.product.PublicProduct;

import java.util.List;
import java.util.UUID;

public class ProductMapper {

    public static Product map(
            ProductRequestDTO dto,
            Branch branch,
            ManagementEntity owner,
            Product product // si c’est une update, sinon passe un constructeur
    ) {
        product.setBranch(branch);
        product.setOwner(owner);
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setMinRisk(dto.minRisk());
        product.setMaxRisk(dto.maxRisk());
        product.setMinimumGuaranteeNumber(dto.minimumGuaranteeNumber());
        product.setFleet(dto.fleet());
        product.setHasReduction(dto.hasReduction());
        return product;
    }

    public static ProductResponseDTO map(Product product) {

        UUID ownerUuid = product.getOwner() != null ? product.getOwner().getUuid() : null;
        String ownerName = product.getOwner() != null ? product.getOwner().getName() : null;

        List<UUID> sharedWith;
        if (product instanceof PublicProduct) {
            sharedWith = ((PublicProduct) product).getSharedWithCompanies()
                    .stream()
                    .map(ManagementEntity::getUuid)
                    .toList();
        } else {
            sharedWith = List.of();
        }

        return ProductResponseDTO.builder()
                .id(product.getUuid())
                .name(product.getName())
                .description(product.getDescription())
                .branch(BranchMapper.map(product.getBranch()))
                .ownerId(ownerUuid)
                .ownerName(ownerName)
                .minRisk(product.getMinRisk())
                .maxRisk(product.getMaxRisk())
                .minimumGuaranteeNumber(product.getMinimumGuaranteeNumber())
                .fleet(product.getFleet())
                .hasReduction(product.isHasReduction())
                .visibility(product.getVisibility())
                .sharedWith(sharedWith)
                .build();
    }
}
