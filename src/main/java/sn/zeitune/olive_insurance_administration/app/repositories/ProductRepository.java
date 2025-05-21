package sn.zeitune.olive_insurance_administration.app.repositories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.olive_insurance_administration.app.entities.Branch;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.ManagementEntity;
import sn.zeitune.olive_insurance_administration.app.entities.product.Product;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Optional<Product> findByUuid(UUID uuid);

    List<Product> findAllByOwner(ManagementEntity owner);
    Page<Product> findAllByOwner(ManagementEntity owner, Pageable pageable);

    boolean existsByNameAndBranchAndOwner(@NotNull(message = "Product name is required") @NotBlank(message = "Product name must not be blank") String name, Branch branch, ManagementEntity owner);
}
