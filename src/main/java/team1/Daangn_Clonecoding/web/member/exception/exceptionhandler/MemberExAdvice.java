package team1.Daangn_Clonecoding.web.member.exception.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team1.Daangn_Clonecoding.web.member.exception.DuplicatedLoginIdException;
import team1.Daangn_Clonecoding.web.response.ErrorResult;

@RestControllerAdvice
public class MemberExAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicatedLoginIdException.class)
    public ErrorResult duplicatedLoginIdHandler(DuplicatedLoginIdException e) {
        return new ErrorResult("Duplicated_LoginId", "아이디 중복입니다.");
    }

}
