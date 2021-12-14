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
    NOT_FOUND_MENU(HttpStatus.NOT_FOUND, "해당 MENU를 찾을 수 없습니다."),
    NOT_FOUND_BASKET(HttpStatus.NOT_FOUND, "해당 BASKET을 찾을 수 없습니다."),
    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "해당 ORDER를 찾을 수 없습니다."),
    NOT_FOUND_RIDER(HttpStatus.NOT_FOUND, "해당 RIDER를 찾을 수 없습니다."),

    BAD_REQUEST_MENU(HttpStatus.BAD_REQUEST, "MENU에 대해 잘못된 요청을 하였습니다."),

    NEED_TO_LOGIN_USER(HttpStatus.UNAUTHORIZED, "USER 로그인이 필요합니다."),
    NEED_TO_LOGIN_OWNER(HttpStatus.UNAUTHORIZED, "OWNER 로그인이 필요합니다."),
    NEED_TO_LOGIN_RIDER(HttpStatus.UNAUTHORIZED, "RIDER 로그인이 필요합니다."),

    BAD_REQUEST_ORDER(HttpStatus.UNAUTHORIZED, "잘못된 ORDER 요청을 하였습니다."),

    BAD_REQUEST_STORAGE(HttpStatus.BAD_REQUEST, "잘못된 STORAGE 요청을 하였습니다."),
    BAD_REQUEST_FIRESTORE(HttpStatus.BAD_REQUEST, "잘못된 FIRESTORE 요청을 하였습니다."),

    BAD_REQUEST_ADDRESS(HttpStatus.BAD_REQUEST, "잘못된 ADDRESS 요청을 하였습니다.");

    private final HttpStatus httpStatus;
    private final String detail;

}
