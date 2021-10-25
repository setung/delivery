package setung.delivery.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "USER 계정을 찾을 수 없습니다. email과 password를 확인해 주세요."),
    NOT_FOUND_OWNER(HttpStatus.NOT_FOUND, "OWNER 계정을 찾을 수 없습니다. email과 password를 확인해 주세요."),
    NOT_FOUND_RESTAURANT(HttpStatus.NOT_FOUND, "해당 RESTAURANT를 찾을 수 없습니다."),

    NEED_TO_LOGIN_USER(HttpStatus.UNAUTHORIZED, "USER 로그인이 필요합니다."),
    NEED_TO_LOGIN_OWNER(HttpStatus.UNAUTHORIZED, "OWNER 로그인이 필요합니다.");


    private final HttpStatus httpStatus;
    private final String detail;

}
