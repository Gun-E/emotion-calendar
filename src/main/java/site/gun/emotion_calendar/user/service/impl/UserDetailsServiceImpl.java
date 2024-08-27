package site.gun.emotion_calendar.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.gun.emotion_calendar.entity.User;
import site.gun.emotion_calendar.user.domain.CustomUserDetails;
import site.gun.emotion_calendar.user.repository.UserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        if (user.getRole() == null || Objects.equals(user.getRole(),"ROLE_GUEST")) {
            throw new IllegalArgumentException("사용자의 권한이 없습니다.");
        }

        Collection<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole()));

        return new CustomUserDetails(
                user.getUserId(),
                user.getName(),
                user.getPassword(),
                user.getEmail(),
                authorities
        );
    }
}