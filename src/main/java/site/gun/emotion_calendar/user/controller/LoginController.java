package site.gun.emotion_calendar.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.gun.emotion_calendar.user.domain.CustomUserDetails;
import site.gun.emotion_calendar.user.dto.LoginRequestDto;
import site.gun.emotion_calendar.user.util.JwtTokenUtil;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) {
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        try {
            authentication = authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            log.warn("로그인 실패 : {}", "아이디 및 비밀번호를 확인하세요.");
            return ResponseEntity.badRequest().body("아이디 및 비밀번호를 확인하세요.");
        } catch (AccountExpiredException | LockedException | DisabledException e) {
            log.warn("Authentication failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("계정 사용에 제한이 있습니다.");
        } catch (AuthenticationException e) {
            log.error("Authentication failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("관리자 승인 진행 중 입니다.");
        }

        String token = jwtTokenUtil.generateToken((CustomUserDetails) authentication.getPrincipal());
        log.info("Login token : {}", token);
        return ResponseEntity.ok(token);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody String token) {
        log.info("Logout Token : {}", token);
        if (jwtTokenUtil.invalidateToken(token)) {
            log.info("토큰 무효화 성공");
            return ResponseEntity.ok("로그아웃 성공");
        }
        log.info("토큰 무효화 실패 (이미 무효 또는 유효하지 않은 토큰)");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그아웃 실패");
    }
}