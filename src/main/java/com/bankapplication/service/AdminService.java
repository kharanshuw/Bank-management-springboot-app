package com.bankapplication.service;

import com.bankapplication.dto.RequstBranchDto;
import com.bankapplication.dto.ResponseDto;
import com.bankapplication.model.Users;

import java.util.List;

public interface AdminService {
    public String activateUser(String action, String id);

    public ResponseDto convertUserToResponseDto(Users users);

    public List<String> findRole(String idString);

    public List<String> findUnassignedRoles(String userid);

    public String addRoleToUser(String rolename, String userid);

    public String removeRoleFromUser(String rolename, String userid);

    // public Users updateAdmin(ProfileUpdateDto profileUpdateDto);

    public int getIdByEmail(String email);

    public Users getUserById(int id);

    public boolean processBranchCreation(RequstBranchDto requstBranchDto);

    public Users getUserByEmail(String email);

    public boolean deleteAccount(int id);
}
