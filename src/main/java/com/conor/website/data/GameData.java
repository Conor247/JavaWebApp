package com.conor.website.data;

public class GameData {
    String playerMove;
    String computerMove;
    String gameResult;

    public GameData() {
        // Default constructor
    }
    public GameData(String playerMove, String computerMove, String gameResult) {
        this.playerMove = playerMove;
        this.computerMove = computerMove;
        this.gameResult = gameResult;
    }

    public String getPlayerMove() {
        return playerMove;
    }

    public void setPlayerMove(String playerMove) {
        this.playerMove = playerMove;
    }

    public String getComputerMove() {
        return computerMove;
    }

    public void setComputerMove(String computerMove) {
        this.computerMove = computerMove;
    }

    public String getGameResult() {
        return gameResult;
    }

    public void setGameResult(String gameResult) {
        this.gameResult = gameResult;
    }
}
