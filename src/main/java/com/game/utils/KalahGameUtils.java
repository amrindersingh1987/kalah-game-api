package com.game.utils;

import java.net.InetAddress;

public class KalahGameUtils {

    public static String getGameUrl(final String gameId) {
        return String.format("http://%s:8080/games/%s", InetAddress.getLoopbackAddress().getHostName(),
                 gameId);
    }
}


