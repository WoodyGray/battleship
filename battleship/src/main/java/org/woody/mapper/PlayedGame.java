package org.woody.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
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
    private final List<Round> rounds;

    @JsonProperty("gameStartTime")
    private String gameStartTime;
    @JsonProperty("gameEndTime")
    private String gameEndTime;

    public PlayedGame(String firstPlayer){
        this.firstPlayer = firstPlayer;
        this.rounds = new ArrayList<>();
    }

    public void addRound(int roundNumber, String playerName, String move){
        rounds.add(Round.builder()
                .move(move)
                .playerName(playerName)
                .roundNumber(roundNumber)
                .build());
    }

}
