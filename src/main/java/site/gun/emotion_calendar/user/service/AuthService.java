package site.gun.emotion_calendar.user.service;

import site.gun.emotion_calendar.user.domain.CustomUserDetails;

public interface AuthService {
    String generateToken(CustomUserDetails authentication);

    void logout(String token);
}
