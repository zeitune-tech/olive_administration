package sn.zeitune.olive_insurance_administration.app.services;

import sn.zeitune.olive_insurance_administration.app.dto.requests.BranchRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.BranchResponseDTO;

import java.util.List;
import java.util.UUID;

public interface BranchService {

    BranchResponseDTO create(BranchRequestDTO dto);
    BranchResponseDTO update(UUID uuid, BranchRequestDTO dto);
    void delete(UUID uuid);
    BranchResponseDTO getByUuid(UUID uuid);
    List<BranchResponseDTO> getAll();

    BranchResponseDTO getByName(String name);
}
