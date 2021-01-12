package com.game.service;

import com.game.exception.ApplicationException;
import com.game.exception.ErrorType;
import com.game.types.GameStatus;
import com.game.utils.PitUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.dao.KalahGameRepository;
import com.game.action.KalahGame;
import com.game.model.Player;

/**
 * @author asingh
 */
@Service("kalahService")
public class KalahGameService {

    @Autowired
    private KalahGameRepository repository;

    /**
     * Get or create the kalah game
     * @return return {@link KalahGame} the waiting game or create new game with waiting status
     */
    public KalahGame createOrGetGame() {
        KalahGame game = repository.createOrGetGame(GameStatus.WAITING);
        return game;
    }

    /** If player joins kalah game
     * @param game {@link KalahGame} game information
     * @param player {@link Player} requested  player information
     * @return return {@link KalahGame} the game object
     */
    public KalahGame joinPlayer(KalahGame game,Player player) {
        return game.joinPlayer(player);
    }

    /**
     * Get or create the kalah game
     *
     * @param player {@link Player} requested  player information
     * @param gameId  game id
     * @param pitId pit id define the move of pit stone
     * @return return {@link KalahGame} with updated status
     */
    public KalahGame playGame(Player player, String gameId, int pitId) {
        KalahGame game = repository.getGame(gameId);
        if(game == null){
            throw new ApplicationException(ErrorType.INVALID_GAME, gameId);
        }
        moveValidation(game, player,pitId);
        game.playGame(player, pitId);
        game.setGameWinner();
        return game;
    }

    /**
     * Validate the moved action
     * @param game {@link KalahGame} Game info
     * @param player {@link Player} player info
     * @param pitId id of pit
     */
    private void moveValidation(KalahGame game, Player player, int pitId) {
        if(game.getSecondPlayer() ==  null){
            throw new ApplicationException(ErrorType.GAME_IN_WAITING_STATE);
        } else if(checkGameOver(game)){
            throw new ApplicationException(ErrorType.GAME_OVER);
        }else if (player == null) {
            throw new ApplicationException(ErrorType.INVALID_PLAYER);
        } else if (pitId == PitUtils.FIRST_PLAYER_HOUSE || pitId == PitUtils.SECOND_PLAYER_HOUSE) {
            throw new ApplicationException(ErrorType.INVALID_PIT_VALUE);
        } else if (game.getPitStoneCountMap().get(pitId) == null) {
            throw new ApplicationException(ErrorType.INVALID_PIT_MOVED, game.getGameId(), pitId + "");
        } else if (game.getPitStoneCountMap().get(pitId) == 0) {
            throw new ApplicationException(ErrorType.NOT_ALLOWED_ZERO_MOVED);
        } else if ((player.equals(game.getFirstPlayer()) && pitId > 6) || (player.equals(game.getSecondPlayer()) && pitId < 7)) {
            throw new ApplicationException(ErrorType.INVALID_MOVED);
        }
    }
    public boolean checkGameOver(KalahGame game){
        return game.checkGameOver();
    }

}
