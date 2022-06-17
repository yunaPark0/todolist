package example.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodValidException(MethodArgumentNotValidException e,
                                                              HttpServletRequest request) {
        log.warn("MethodArgumentNotValidException 발생!!! url:{}, trace:{}",
                request.getRequestURI(),e.getStackTrace());
        ErrorResponse errorResponse = makeErrorResponse(e.getBindingResult());

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse makeErrorResponse(BindingResult bindingResult) {
        String error = "";

        if (bindingResult.hasErrors()) {
            error = bindingResult.getFieldError().getDefaultMessage();
        }
        return new ErrorResponse(error);
    }

    ErrorResponse errorResponse = new ErrorResponse("");

    @ExceptionHandler(BadRequestException.class)
    public @ResponseBody ErrorResponse duplicate(BadRequestException e){
        errorResponse.setError("이미 사용중인 이메일 입니다.");
        return errorResponse;
    }

    @ExceptionHandler(Exception1.class)
    public @ResponseBody ErrorResponse genderChoose(Exception1 e){
        errorResponse.setError("성별은 '남자', '여자', '중성' 으로 입력하시오");
        return errorResponse;
    }

    @ExceptionHandler(Exception2.class)
    public @ResponseBody ErrorResponse locationChoose(Exception2 e) {
        errorResponse.setError("지역을 다시 입력해주십시오.");
        return errorResponse;
    }

    @ExceptionHandler(EmailLoginFailedExc.class)
    protected @ResponseBody ErrorResponse emailFailed(EmailLoginFailedExc e) {
        errorResponse.setError("로그인 실패 : 가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.");
        return errorResponse;
    }

    @ExceptionHandler(UserNotFoundExc.class)
    protected @ResponseBody ErrorResponse userNotFound(UserNotFoundExc e) {
        errorResponse.setError("회원을 조회할 수 없습니다.");
        return errorResponse;
    }
}
