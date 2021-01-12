package com.game.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * GameException of kalah game
 * Use this exception for any exceptional behaviour of the business.
 * It is handled by CustomizedResponseEntityExceptionHandler in case of a exception throw via REST.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 3779487438577449588L;

    /**
     * its define the value of ErrorType enum
     */
    private final ErrorType errorType;

    /**
     * The errorType can have message that can contain variables and it is possible to pass variables
     * see String.format for more details.
     */
    private final String[] errorVariables;

    /**
     * @param errorType a {@link ErrorType} error type.
     * @param errorVariables a {@link String} multiple value.
     */
    public ApplicationException(ErrorType errorType, String... errorVariables) {
        super(errorType.name());
        this.errorType = errorType;
        this.errorVariables = errorVariables;
    }

    /**
     * @param errorType a {@link ErrorType} error type.
     */
    public ApplicationException(ErrorType errorType) {
        super(errorType.name());
        this.errorType = errorType;
        this.errorVariables = null;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public String[] getErrorVariables() {
        return errorVariables;
    }
}
