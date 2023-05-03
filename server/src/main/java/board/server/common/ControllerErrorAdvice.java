package board.server.common;

import board.server.common.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerErrorAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse handleUserNotFoundException() {
        return new ErrorResponse("해당 사용자를 찾을 수 없습니다.");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BoardNotFoundException.class)
    public ErrorResponse handleBoardNotFoundException() {
        return new ErrorResponse("해당 게시글을 찾을 수 없습니다.");
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateEmailException.class)
    public ErrorResponse handleDuplicateEmailException() {
        return new ErrorResponse("이미 존재하는 이메일입니다.");
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateUserNameException.class)
    public ErrorResponse handleDuplicateUserNameException() {
        return new ErrorResponse("이미 존재하는 닉네임입니다.");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UserNotBoardAuthorException.class)
    public ErrorResponse handleUserNotBoardAuthorException() {
        return new ErrorResponse("해당 사용자는 게시글 작성자가 아니므로 권한이 없습니다.");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UserNotCommentAuthorException.class)
    public ErrorResponse handleUserNotCommentAuthorException() {
        return new ErrorResponse("해당 사용자는 댓글 작성자가 아니므로 권한이 없습니다.");
    }
}
