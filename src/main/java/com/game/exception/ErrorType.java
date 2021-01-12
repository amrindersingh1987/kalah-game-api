package com.game.exception;

import org.springframework.http.HttpStatus;

/**
 * @author asingh
 */
public enum ErrorType {

    /**
     * Error types used in KalahGameException
     */
    INVALID_PLAYER(HttpStatus.BAD_REQUEST, "Invalid Player", "Invalid Player blank"),
    INVALID_PIT_VALUE(HttpStatus.BAD_REQUEST, "pitid cannot be house", "pitid cannot be house"),
    INVALID_PIT_MOVED(HttpStatus.BAD_REQUEST, "Invalid Pit Moved", "Invalid moved gameId {0} pitId {1}"),
    INVALID_MOVED(HttpStatus.BAD_REQUEST, "Invalid Moved", "Invalid moved pit value should not house "),
    NOT_ALLOWED_ZERO_MOVED(HttpStatus.BAD_REQUEST, "Zero stone", "Not allowed pit with zero stone"),
    GAME_OVER(HttpStatus.BAD_REQUEST, "Game Over", "Game already over"),

    INVALID_GAME(HttpStatus.NOT_FOUND, "Game not found", "Game not found, please check the game {0}"),
    GAME_IN_WAITING_STATE(HttpStatus.OK, "Waiting for 2nd player", "Game cannot start, waiting for 2nd player");

    private HttpStatus status;
    private String title;

    /**
     * The message can contain variables and in the KalahGameException it is possible to pass variables
     */
    private String message;

    ErrorType(HttpStatus status, String title, String message) {
        this.status = status;
        this.title = title;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
