package com.testing_company.case_management.service;

import com.testing_company.case_management.enums.Role;
import com.testing_company.case_management.repository.UserRepository;
import com.testing_company.case_management.model.User;
import com.testing_company.case_management.util.LogUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException{
        LogUtils.logRequest(log, this, "Loading user details for username：{}", username);
        User user=userRepository.findByUsername(username).orElseThrow(()->{
                log.error("User not found：{}", username);
        return new UsernameNotFoundException("User not found："+username);
        });

        if(user.getRole().equals(Role.RESIGNED)||user.getRole().equals(Role.SUSPENDED)){
            log.warn("User account is disabled：{}", username);
            throw new DisabledException("User account is disabled；"+username);
        }
        List<SimpleGrantedAuthority> authorities =List.of(
                new SimpleGrantedAuthority("ROLE_"+user.getRole().name())
        );
        log.info("User {} laoded with role：{}",username, user.getRole());

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(user.getRole().equals(Role.RESIGNED)||user.getRole().equals(Role.SUSPENDED))
                .build();
    }
}
