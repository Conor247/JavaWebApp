package com.conor.website.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RockPaperScissorsRequest {
    @JsonProperty(value = "rpsPlayerRequest")
    public String rpsPlayerMove;
}
