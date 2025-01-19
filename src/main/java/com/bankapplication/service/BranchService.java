package com.bankapplication.service;

import com.bankapplication.dto.RequstBranchDto;
import com.bankapplication.dto.ResponseBranchDto;
import com.bankapplication.model.Branch;

import java.util.List;

public interface BranchService {
    public List<Branch> getAllBranch();

    public Branch getBranchById(int id);

    public RequstBranchDto branchToRequestDTO(Branch branch);

    public ResponseBranchDto branchToResponseDto(Branch branch);

    public boolean processAndUpdateBranch(ResponseBranchDto responseBranchDto);

    public ResponseBranchDto branchToResponseDto2(Branch branch);

    public boolean removeManagerFromBranch(String branchId);

    public Branch getBranchByManagerId(int managerId);

    public boolean removeBranch(int id);

}
