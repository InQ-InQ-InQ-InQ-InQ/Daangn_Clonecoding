package team1.Daangn_Clonecoding.web.exception.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team1.Daangn_Clonecoding.web.exception.NotExistPkException;
import team1.Daangn_Clonecoding.web.response.ErrorResult;

@RestControllerAdvice
public class CommonExHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NotExistPkException.class)
    public ErrorResult fileEmptyHandler(NotExistPkException e) {
        return new ErrorResult(false, "INTERNAL_SERVER_ERROR", e.getMessage());
    }
}
