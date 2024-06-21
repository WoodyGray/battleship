package org.woody.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PlayedGame {
    @JsonProperty("sessionId")
    private String sessionId;
    @JsonProperty("firstPlayer")
    private String firstPlayer;
    @JsonProperty("secondPlayer")
    private String secondPlayer;

    @JsonProperty("firstPlayerHasWin")
    private boolean firstPlayerHasWin;

    @JsonProperty("rounds")
    private List<Round> rounds;

    @JsonProperty("gameStartTime")
    private static String gameStartTime;
    @JsonProperty("gameEndTime")
    private static String gameEndTime;
}
