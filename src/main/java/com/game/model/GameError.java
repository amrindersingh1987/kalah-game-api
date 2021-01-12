package com.game.model;

public class GameError {

    private int code;
    private String title;
    private String message;

    public GameError(int code, String title, String message) {
        this.code = code;
        this.title = title;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }


    public String getMessage() {
        return message;
    }

}
