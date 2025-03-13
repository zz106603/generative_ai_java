package com.spring.ai.common.security;

import com.spring.ai.common.exception.base.ErrorMessages;
import com.spring.ai.user.domain.Account;
import com.spring.ai.user.domain.Member;
import com.spring.ai.user.domain.enums.AccountLoginType;
import com.spring.ai.user.domain.enums.AccountRole;
import com.spring.ai.user.repository.AccountRepository;
import com.spring.ai.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final AccountRepository accountRepository;

    private final MemberRepository memberRepository;

    private final DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String givenName = oAuth2User.getAttribute("given_name"); // OAuth2 공급자가 제공하는 first name
        String familyName = oAuth2User.getAttribute("family_name"); // OAuth2 공급자가 제공하는 last name
        String profileUrl = oAuth2User.getAttribute("picture"); // 프로필 사진 URL

        // 계정 조회
        Optional<Account> existingAccount = accountRepository.findByLoginIdAndIsDeletedFalse(email);

        // 계정 생성
        Account account = existingAccount.orElseGet(() -> createAccount(email, name, givenName, familyName, profileUrl));

        // 권한 정보 설정
        Collection<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(account.getRoles().name())
        );

        return new CustomUserDetails(
                account.getId(),
                account.getLoginId(),
                authorities,
                oAuth2User.getAttributes() // OAuth2 사용자 속성 전달
        );
    }

    public Account createAccount(String email, String name, String givenName, String familyName, String profileUrl) {
        try {
            Member newMember = saveMember(email, name, givenName, familyName, profileUrl);
            return saveAccount(email, newMember);
        } catch (Exception e) {
            log.error("Error occurred during OAuth2 registration: {}", e.getMessage(), e);
            throw new OAuth2AuthenticationException(ErrorMessages.GENERAL_CREATION_FAILED.getMessage());
        }
    }

    // 회원 생성 및 저장 메서드
    private Member saveMember(String email, String name, String givenName, String familyName, String profileUrl) {
        Member member = Member.builder()
                .email(email)
                .name(name)
                .givenName(givenName)
                .familyName(familyName)
                .profileUrl(profileUrl)
                .build();
        return memberRepository.save(member);
    }

    // 계정 생성 및 저장 메서드
    private Account saveAccount(String email, Member member) {
        Account newAccount = Account.builder()
                .loginId(email)
                .password("oauth_password")
                .member(member)
                .loginType(AccountLoginType.GOOGLE)  // 로그인 타입 고정값
                .roles(AccountRole.ROLE_USER)        // 권한 고정값
                .build();
        return accountRepository.save(newAccount);
    }
}
