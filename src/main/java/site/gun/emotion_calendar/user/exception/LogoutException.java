package site.gun.emotion_calendar.user.exception;

public final class LogoutException extends RuntimeException{
    public LogoutException(){
        super("로그아웃 에러");
    }
}
