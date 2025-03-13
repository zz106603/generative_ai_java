package com.spring.ai.user.service;

import com.spring.ai.user.model.response.AccountResponse;

public interface AccountService {
    // Account 조회
    AccountResponse findOneAccount(Long accountId);
}
