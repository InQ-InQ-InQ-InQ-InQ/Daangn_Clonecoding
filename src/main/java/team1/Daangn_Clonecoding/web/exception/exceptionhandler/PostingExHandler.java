package team1.Daangn_Clonecoding.web.exception.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team1.Daangn_Clonecoding.web.exception.AlreadyExistBuyerException;
import team1.Daangn_Clonecoding.web.exception.FileEmptyException;
import team1.Daangn_Clonecoding.web.exception.FileTransferException;
import team1.Daangn_Clonecoding.web.exception.NotExistPkException;
import team1.Daangn_Clonecoding.web.response.ErrorResult;

@RestControllerAdvice
public class PostingExHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FileEmptyException.class)
    public ErrorResult fileEmptyHandler(FileEmptyException e) {
        return new ErrorResult(false, "BAD_REQUEST", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FileTransferException.class)
    public ErrorResult transferErrorHandler(FileTransferException e) {
        return new ErrorResult(false, "INTERNAL_SERVER_ERROR", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AlreadyExistBuyerException.class)
    public ErrorResult alreadyExistBuyerHandler(AlreadyExistBuyerException e) {
        return new ErrorResult(false, "BAD_REQUEST", e.getMessage());
    }
}
