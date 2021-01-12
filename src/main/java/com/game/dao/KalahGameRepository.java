package com.game.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.game.types.GameStatus;
import org.springframework.stereotype.Component;

import com.game.action.KalahGame;
import com.game.types.KalahGameType;

/**
 * @author asingh
 * Repository class for kalah game
 */
@Component
public class KalahGameRepository {

    private final Map<String, KalahGame> games = new HashMap<>();

    /**
     * Get or create the kalah game
     *
     * @param gameStatus {@link GameStatus} get waiting game.
     * @return return {@link KalahGame} the waiting game or create new game with waiting status
     */

    public KalahGame createOrGetGame(GameStatus gameStatus) {
        Optional<KalahGame> kalah =  games.values().stream().filter(x-> x.hasGameStatus(gameStatus)).findFirst();
        if(kalah.isPresent()) {
            return kalah.get();
        }
        KalahGame game = KalahGame.getInstance(KalahGameType.SIX);
        games.put(game.getGameId(),game);
        return game;
    }

    /**
     * Get the existing game
     *
     * @param gameId id of the game to get.
     * @return return {@link KalahGame} the game detail
     */
    public KalahGame getGame(String gameId) {
        return games.get(gameId);
    }
}
