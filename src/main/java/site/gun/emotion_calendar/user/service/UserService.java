package site.gun.emotion_calendar.user.service;


import site.gun.emotion_calendar.user.dto.RegisterRequestDto;

public interface UserService {

    void register(RegisterRequestDto registerRequestDto);

//    void modify(UserDetailDto userModifyDto);
//
//    void delete(long userId);
    boolean emailDuplicateCheck(String email);
}
