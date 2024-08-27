package site.gun.emotion_calendar.user.exception;

public final class LoginException extends RuntimeException{
    public LoginException(){
        super("아이디 및 비밀번호를 확인하세요.");
    }
}
