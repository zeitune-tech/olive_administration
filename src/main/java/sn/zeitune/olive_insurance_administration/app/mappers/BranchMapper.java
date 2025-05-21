package sn.zeitune.olive_insurance_administration.app.mappers;

import sn.zeitune.olive_insurance_administration.app.dto.requests.BranchRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.BranchResponseDTO;
import sn.zeitune.olive_insurance_administration.app.entities.Branch;
import sn.zeitune.olive_insurance_administration.app.entities.Category;

public class BranchMapper {

    public static Branch map(BranchRequestDTO dto, Category category, Branch branch) {
        branch.setName(dto.name());
        branch.setDescription(dto.description());
        branch.setCategory(category);
        return branch;
    }

    public static BranchResponseDTO map(Branch branch) {
        return new BranchResponseDTO(
                branch.getUuid(),
                branch.getName(),
                branch.getDescription(),
                CategoryMapper.map(branch.getCategory())
        );
    }
}
