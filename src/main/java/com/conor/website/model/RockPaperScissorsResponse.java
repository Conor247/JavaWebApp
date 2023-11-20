package com.conor.website.model;

import com.conor.website.data.GameData;
import lombok.Getter;

import java.util.List;

public class RockPaperScissorsResponse {

    public String youChose;
    public String computerChose;
    public String result;
    public String streak;
    public String odds;
    public int computerRockMoves;
    public int computerPaperMoves;
    public int computerScissorsMoves;
    public List<GameData> previousGameResultsList;

    public void setResult(String result) {
        this.result = result;
    }
    public void setComputerChose(String computerChose) {
        this.computerChose = computerChose;
    }
    public void setYouChose(String youChose) {
        this.youChose = youChose;
    }
    public void setStreak(String streak) {
        this.streak = streak;
    }
    public void setOdds(String odds) {
        this.odds = odds;
    }
    public void setComputerRockMoves(int computerRockMoves) {
        this.computerRockMoves = computerRockMoves;
    }
    public void setComputerPaperMoves(int computerPaperMoves) {
        this.computerPaperMoves = computerPaperMoves;
    }
    public void setComputerScissorsMoves(int computerScissorsMoves) {
        this.computerScissorsMoves = computerScissorsMoves;
    }
    public void setPreviousGameResultsList(List<GameData> previousGameResultsList) {
        this.previousGameResultsList = previousGameResultsList;
    }

}
