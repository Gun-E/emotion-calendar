package site.gun.emotion_calendar.user.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.gun.emotion_calendar.entity.User;
import site.gun.emotion_calendar.user.dto.RegisterRequestDto;
import site.gun.emotion_calendar.user.exception.UserAlreadyExist;
import site.gun.emotion_calendar.user.repository.UserRepository;
import site.gun.emotion_calendar.user.service.UserService;
import site.gun.emotion_calendar.user.type.USER_ROLE;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void register(RegisterRequestDto registerDto) {
        if (userRepository.existsUserByEmailLike(registerDto.email())) {
            throw new UserAlreadyExist();
        }
        String name = registerDto.name();
        String email = registerDto.email();
        String password = registerDto.password();
        password = passwordEncoder.encode(password);
        String phone = registerDto.phone();
        User user = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .phone(phone)
                .createdDate(LocalDateTime.now())
                .role(String.valueOf(USER_ROLE.USER.getRole()))
                .modifiedDate(LocalDateTime.now())
                .build();
        userRepository.save(user);
    }


//    @Override
//    public void modify(UserDetailDto userModifyDto) {
//
//    }
//
//    @Override
//    public void delete(long userId) {
//
//    }

    @Override
    public boolean emailDuplicateCheck(String email) {
        return userRepository.existsUserByEmailLike(email);
    }

}
