package com.game.utils;

import com.game.action.KalahGame;
import com.game.model.Player;

import java.util.Map;

public class PitUtils {
    public static final int FIRST_PLAYER_HOUSE = 7;
    public static final int SECOND_PLAYER_HOUSE = 14;

    public static void firstPlayerPit(Map<Integer, Integer> pitStoneCountMap,int stoneCount) {
        for (int i = 1; i < FIRST_PLAYER_HOUSE; i++) {
            pitStoneCountMap.put(i, stoneCount);
        }
        pitStoneCountMap.put(FIRST_PLAYER_HOUSE, 0);
    }
    public static void secondPlayerpit(Map<Integer, Integer> pitStoneCountMap,int stoneCount) {
        for (int i = 8; i < SECOND_PLAYER_HOUSE; i++) {
            pitStoneCountMap.put(i, stoneCount);
        }
        pitStoneCountMap.put(SECOND_PLAYER_HOUSE, 0);

    }

    public static Player checkWinner(KalahGame game){
        long firstPlayerPitCount =  game.getPitStoneCountMap().keySet().stream().filter(x-> x< FIRST_PLAYER_HOUSE && x==0).count();
        long secondPlayerPitCount =  game.getPitStoneCountMap().keySet().stream().filter(x-> x> FIRST_PLAYER_HOUSE && x<SECOND_PLAYER_HOUSE && x==0).count();
        long firstHouse = game.getPitStoneCountMap().get(PitUtils.FIRST_PLAYER_HOUSE);
        long secondHouse = game.getPitStoneCountMap().get(PitUtils.SECOND_PLAYER_HOUSE);
        if(firstPlayerPitCount == 6 && firstHouse > secondHouse) {
            return game.getFirstPlayer();
        }else if(secondHouse == 6 && secondHouse > firstHouse){
            return game.getSecondPlayer();
        }
        return null;
    }


}
