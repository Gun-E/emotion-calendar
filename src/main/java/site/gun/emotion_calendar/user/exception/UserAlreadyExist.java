package site.gun.emotion_calendar.user.exception;

public class UserAlreadyExist extends RuntimeException{
    public UserAlreadyExist() {
        super("이미 존재하는 유저 입니다");
    }
}
