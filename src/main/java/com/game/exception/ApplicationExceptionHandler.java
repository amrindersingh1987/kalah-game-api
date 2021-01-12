package com.game.exception;

import com.game.model.GameError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.MessageFormat;

/**
 * @author asingh
 * To Handle the Gloabl Exception and return the exception in GameError Model
 */
@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<GameError> handleGlobalException(Exception ex) {
        HttpStatus status = null;
        GameError gameError = null;

        if (ex instanceof ApplicationException) {
            ApplicationException applicationException = (ApplicationException) ex;
            status = applicationException.getErrorType().getStatus();
            gameError = new GameError(status.value(), applicationException.getErrorType().getTitle(),
                                  constructErrorMessage(applicationException));
        }
        return new ResponseEntity<>(gameError, status);
    }

    private String constructErrorMessage(ApplicationException applicationException) {
        String errorMessage;
        if (applicationException.getErrorVariables() != null) {
            MessageFormat messageFormat = new MessageFormat(applicationException.getErrorType().getMessage());
            errorMessage = messageFormat.format(applicationException.getErrorVariables());
        } else {
            errorMessage = applicationException.getErrorType().getMessage();
        }
        return errorMessage;
    }

}
