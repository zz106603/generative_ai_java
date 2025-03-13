package com.spring.ai.user.model.request;

import com.spring.ai.user.domain.enums.AccountLoginType;
import com.spring.ai.user.domain.enums.AccountRole;

public record AccountCreateRequest(
        String loginId,
        Long memberId,
        AccountLoginType loginType,
        AccountRole role
) {
}