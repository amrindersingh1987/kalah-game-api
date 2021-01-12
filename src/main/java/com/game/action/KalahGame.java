package com.game.action;

import com.game.exception.ApplicationException;
import com.game.exception.ErrorType;
import com.game.model.Player;
import com.game.types.GameStatus;
import com.game.types.KalahGameType;
import com.game.utils.PitUtils;
import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

/**
 * @author asingh
 * kalahGame is action class have the value of about game with action
 */
public final class KalahGame implements Serializable {
    private static final long serialVersionUID = 1L;

    private String gameId;
    private GameStatus gameStatus;
    private KalahGameType gameType;
    private final Map<Integer, Integer> pitStoneCountMap;


    private Player firstPlayer;
    private Player secondPlayer;
    private MoveStrategy action;
    private Player winner;

    private KalahGame(KalahGameType kalahType) {
        this.gameId = UUID.randomUUID().toString();
        this.gameStatus = GameStatus.WAITING;
        this.gameType = kalahType;
        this.pitStoneCountMap = Maps.newHashMap();
        this.action = new MoveStrategy(this);

    }

    public static KalahGame getInstance(KalahGameType kalahType) {
        KalahGame game = new KalahGame(kalahType);
        return game;
    }

    /**
     *
     * @param player {@link Player} Player who join the game
     * @return KalahGame
     */
    public KalahGame joinPlayer(Player player) {
        if (this.pitStoneCountMap.size() == 0) {
            PitUtils.firstPlayerPit(this.pitStoneCountMap, this.gameType.getStoneCount());
            this.firstPlayer = player;
        } else {
            PitUtils.secondPlayerpit(this.pitStoneCountMap, this.gameType.getStoneCount());
            this.secondPlayer = player;
        }
        this.updateState();
        return this;
    }

    /**
     * Update the game status to start once both player joined
     */
    private void updateState() {
        if (this.firstPlayer != null && this.secondPlayer != null) {
            startGame();
        }
    }

    /**
     *  update game status to started
     */

    private void startGame() {
        this.gameStatus = GameStatus.STARTED;
    }

    public String getGameId() {
        return gameId;
    }

    public Map<Integer, Integer> getPitStoneCountMap() {
        return pitStoneCountMap;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public void playGame(Player player, int pitId) {
        this.action.makeMove(pitId, player);
    }

    public boolean hasGameStatus(GameStatus gameStatus) {
        return this.gameStatus == gameStatus;
    }

    public void setGameWinner() {
        winner = PitUtils.checkWinner(this);
        if(winner != null){
            this.gameStatus =GameStatus.GAMEOVER;
        }
    }

    public boolean checkGameOver(){
        return this.gameStatus == GameStatus.GAMEOVER;
    }

}
