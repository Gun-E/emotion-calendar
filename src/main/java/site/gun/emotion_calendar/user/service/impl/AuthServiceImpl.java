package site.gun.emotion_calendar.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.gun.emotion_calendar.user.domain.CustomUserDetails;
import site.gun.emotion_calendar.user.exception.LogoutException;
import site.gun.emotion_calendar.user.service.AuthService;
import site.gun.emotion_calendar.user.util.JwtTokenUtil;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtTokenUtil jwtTokenUtil;
    @Override
    public String generateToken(CustomUserDetails authentication){
        return jwtTokenUtil.generateToken(authentication);
    }
    @Override
    public void logout(String token){
        if (!jwtTokenUtil.invalidateToken(token)) {
            throw new LogoutException();
        }
    }
}
