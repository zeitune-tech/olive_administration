package sn.zeitune.olive_insurance_administration.app.repositories;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import sn.zeitune.olive_insurance_administration.app.entities.Category;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByUuid(UUID uuid);

    boolean existsByName(@NotBlank(message = "Name is mandatory") String name);
}
