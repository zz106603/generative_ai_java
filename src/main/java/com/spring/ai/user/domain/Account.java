package com.spring.ai.user.domain;

import com.spring.ai.common.domain.BaseEntity;
import com.spring.ai.user.domain.enums.AccountLoginType;
import com.spring.ai.user.domain.enums.AccountRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "account")
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Account extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String loginId;

    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Setter
    private Long rootFolderId;

    @Column(nullable = false)
    @Builder.Default
    private boolean isDeleted = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountLoginType loginType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountRole roles;
}
