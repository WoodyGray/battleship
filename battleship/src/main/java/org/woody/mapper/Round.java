package org.woody.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Round {
    @JsonProperty("roundNumber")
    private int roundNumber;

    @JsonProperty("playerName")
    private String playerName;

    @JsonProperty("move")
    private String move;
}
