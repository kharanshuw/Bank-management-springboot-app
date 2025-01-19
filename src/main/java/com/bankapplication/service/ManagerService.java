package com.bankapplication.service;

import com.bankapplication.dto.ManagerDto;
import com.bankapplication.dto.ResponseBranchDto;
import com.bankapplication.dto.response.ResponseAccountDto;
import com.bankapplication.model.Users;

import java.util.List;

public interface ManagerService {
    public List<ManagerDto> getAllManagers();

    public ResponseBranchDto getBranchByManagerId(Users users);

    public List<ResponseAccountDto> getAllAccounts(int managerid);
}
