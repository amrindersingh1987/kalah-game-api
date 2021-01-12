package com.game.action;


import com.game.model.Player;
import com.game.utils.PitUtils;

/**
 * @author asingh
 * MoveStrategy class help to moved the stone from different pit alonge side in house
 * on bases of player and pit id
 */
public class MoveStrategy {

    private KalahGame game;

    public MoveStrategy(KalahGame kalahGame) {
        this.game = kalahGame;
    }

    /**
     * Move the stones
     *
     * @param pitId
     * @param player
     */
    public void makeMove(int pitId, Player player) {
        int startPit = game.getPitStoneCountMap().get(pitId);
        game.getPitStoneCountMap().put(pitId, 0);
        while (startPit > 0) {
            int currentPit = ++pitId;
            if (checkOpponentsHouse(player, currentPit)) {
                int currentStoneCount = game.getPitStoneCountMap().get(currentPit);
                game.getPitStoneCountMap().put(currentPit, currentStoneCount + 1);
                startPit--;
            }
        }
    }

    /**
     * Skip the Opponents House
     *
     * @param player
     * @param currentPit
     * @return boolean
     */
    private boolean checkOpponentsHouse(Player player, int currentPit) {
        if ((game.getFirstPlayer().equals(player) && currentPit != PitUtils.SECOND_PLAYER_HOUSE)
                || (game.getSecondPlayer().equals(player) && currentPit != PitUtils.FIRST_PLAYER_HOUSE)) {
            return true;
        }
        return false;
    }
}
