package team1.Daangn_Clonecoding.web.member.exception.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team1.Daangn_Clonecoding.web.member.exception.DuplicatedException;
import team1.Daangn_Clonecoding.web.response.ErrorResult;

@RestControllerAdvice
public class MemberExAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicatedException.class)
    public ErrorResult duplicatedHandler(DuplicatedException e) {
        return new ErrorResult(e.getMessage(), "중복입니다.");
    }

}
