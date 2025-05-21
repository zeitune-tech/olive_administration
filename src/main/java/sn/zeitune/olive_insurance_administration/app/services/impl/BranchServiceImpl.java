package sn.zeitune.olive_insurance_administration.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.olive_insurance_administration.app.dto.requests.BranchRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.BranchResponseDTO;
import sn.zeitune.olive_insurance_administration.app.entities.Branch;
import sn.zeitune.olive_insurance_administration.app.entities.Category;
import sn.zeitune.olive_insurance_administration.app.exceptions.NotFoundException;
import sn.zeitune.olive_insurance_administration.app.mappers.BranchMapper;
import sn.zeitune.olive_insurance_administration.app.repositories.BranchRepository;
import sn.zeitune.olive_insurance_administration.app.repositories.CategoryRepository;
import sn.zeitune.olive_insurance_administration.app.services.BranchService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public BranchResponseDTO create(BranchRequestDTO dto) {
        Category category = categoryRepository.findByUuid(dto.categoryUuid())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        Branch branch = BranchMapper.map(dto, category, new Branch());
        return BranchMapper.map(branchRepository.save(branch));
    }

    @Override
    public BranchResponseDTO update(UUID uuid, BranchRequestDTO dto) {
        Branch branch = branchRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Branch not found"));

        Category category = categoryRepository.findByUuid(dto.categoryUuid())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        BranchMapper.map(dto, category, branch);
        return BranchMapper.map(branchRepository.save(branch));
    }

    @Override
    public void delete(UUID uuid) {
        Branch branch = branchRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Branch not found"));
        branchRepository.delete(branch);
    }

    @Override
    @Transactional(readOnly = true)
    public BranchResponseDTO getByUuid(UUID uuid) {
        Branch branch = branchRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Branch not found"));
        return BranchMapper.map(branch);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchResponseDTO> getAll() {
        return branchRepository.findAll().stream()
                .map(BranchMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public BranchResponseDTO getByName(String name) {
        return branchRepository.findByName(name)
                .map(BranchMapper::map)
                .orElseThrow(() -> new NotFoundException("Branch not found"));
    }
}
