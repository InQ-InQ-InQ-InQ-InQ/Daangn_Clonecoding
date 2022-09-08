package team1.Daangn_Clonecoding.web.exception.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team1.Daangn_Clonecoding.web.exception.LoginException;
import team1.Daangn_Clonecoding.web.response.ErrorResult;

@RestControllerAdvice
public class LoginExHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LoginException.class)
    public ErrorResult loginExceptionHandler(LoginException e) {
        return new ErrorResult(false, "BAD_REQUEST", e.getMessage());
    }
}
