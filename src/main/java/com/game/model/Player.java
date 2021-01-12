package com.game.model;

import java.util.Objects;

public class Player {

    private String playerId;

    public Player(String playerId) {
        this.playerId = playerId;
    }


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Player that = (Player) o;
		return Objects.equals(playerId, that.playerId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(playerId);
	}
}
