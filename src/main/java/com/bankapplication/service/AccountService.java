package com.bankapplication.service;

import com.bankapplication.dto.request.RequestAccountDto;
import com.bankapplication.dto.response.ResponseAccountDto;
import com.bankapplication.model.Account;
import com.bankapplication.model.Users;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public interface AccountService {

    public Users getUserByEmail(String email);

    public List<String> getNonExistingAccount(Users users);

    public Account saveNewAccount(RequestAccountDto requestAccountDto);

    public ResponseAccountDto getAccountDetailsByAccountNumber(String accountNo);

    public void deleteAccountByAccountNumber(String accountNumber);

    public void depositMoney(String accountNo, BigDecimal amount);

    public void withdrawMoney(String accountNo, BigDecimal amount);


    public List<ResponseAccountDto> getAccountsWithDisablesStatus(int managerid);

    public void enableAccount(String accountNo);

    public ResponseAccountDto getResponseDtoByAccount(Account account);

}
