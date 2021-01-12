package com.game.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameResponse {

    private final String id;
    private final String uri;
    private final Map<Integer, Integer> status;

    public GameResponse(final String id, final String uri) {
        this(id, uri, null);
    }

    public GameResponse(final String id, final String uri, final Map<Integer, Integer> status) {
        this.id = id;
        this.uri = uri;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public Map<Integer, Integer> getStatus() {
        return status;
    }
}
