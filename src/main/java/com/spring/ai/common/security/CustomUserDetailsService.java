package com.spring.ai.common.security;

import com.spring.ai.common.exception.base.ErrorMessages;
import com.spring.ai.user.domain.Account;
import com.spring.ai.user.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Account account = accountRepository.findByLoginIdAndIsDeletedFalse(loginId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.NOT_FOUND_ENTITY.format(Account.class.getSimpleName(), loginId)));

        GrantedAuthority authority = new SimpleGrantedAuthority(account.getRoles().name());
        Collection<GrantedAuthority> authorities = Collections.singletonList(authority);

        return new CustomUserDetails(
                account.getId(),
                account.getLoginId(),
                authorities,
                Collections.emptyMap()
        );
    }
}
