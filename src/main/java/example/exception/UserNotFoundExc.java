package example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserNotFoundExc extends RuntimeException{


    public UserNotFoundExc(String userEmail) {
    }
}
