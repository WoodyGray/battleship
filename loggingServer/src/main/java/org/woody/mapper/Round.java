package org.woody.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Round {
    @JsonProperty("roundNumber")
    private int roundNumber;

    @JsonProperty("playerName")
    private String playerName;

    @JsonProperty("move")
    private String move;
}
