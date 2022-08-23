package team1.Daangn_Clonecoding.web.member;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team1.Daangn_Clonecoding.web.exception.DuplicatedException;
import team1.Daangn_Clonecoding.web.response.ErrorResult;

@RestControllerAdvice
public class MemberExHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicatedException.class)
    public ErrorResult duplicatedHandler(DuplicatedException e) {
        return new ErrorResult(false , "BAD_REQUEST", e.getMessage());
    }




}
