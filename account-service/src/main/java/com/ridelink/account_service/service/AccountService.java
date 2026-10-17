package com.ridelink.account_service.service;

import com.ridelink.account_service.dto.*;
import com.ridelink.account_service.model.AccountStatus;

public interface AccountService {
    AccountResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    AccountResponse getAccountById(String id);
    AccountResponse updateAccount(String id, UpdateAccountRequest request);
    AccountResponse updateAccountStatus(String id, AccountStatus status);
    AccountResponse validateToken(String token);
}