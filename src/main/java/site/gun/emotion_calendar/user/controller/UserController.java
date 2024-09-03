package site.gun.emotion_calendar.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import site.gun.emotion_calendar.user.dto.EmailCheckDto;
import site.gun.emotion_calendar.user.dto.RegisterRequestDto;
import site.gun.emotion_calendar.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequestDto registerRequestDto) {
        log.info("RegisterRequestDto: {}", registerRequestDto);
        userService.register(registerRequestDto);
    }

//    @PutMapping("/modify")
//    public void modify(@RequestBody UserDetailDto userDetailDto) {
//        userService.modify(userDetailDto);
//    }
//
//    @DeleteMapping("/delete/{userId}")
//    public void delete(@PathVariable long userId) {
//        userService.delete(userId);
//    }

    @PostMapping("/email-duplicate-check")
    public boolean checkDuplicateCheck(@RequestBody EmailCheckDto email) {
        log.info("EmailCheckDto: {}", email);
        return userService.emailDuplicateCheck(email);
    }
}
