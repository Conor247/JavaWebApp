package com.conor.website.service;

import com.conor.website.database.DatabaseConnection;
import com.conor.website.model.GameData;
import com.conor.website.model.RockPaperScissorsRequest;
import com.conor.website.model.RockPaperScissorsResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class RockPaperScissorsService {

    public int computerRockMoves;
    public int computerPaperMoves;
    public int computerScissorsMoves;
    public int playerStreak;
    public double odds;
    public double oddsPercent;

    private static final DecimalFormat df = new DecimalFormat("0.00000000");
    public String currentStreak;
    public String computerMove;
    public List<String> previousCpuMovesList = new ArrayList<>();
    public List<String> previousResultsList = new ArrayList<>();
    public List<GameData> previousGameResultsList = new ArrayList<>();
    private final Random rand = new Random();
    private static final String[] MOVES = {"Rock", "Paper", "Scissors"};
    private static final String[] RESULTS = {"Win", "Loss", "Draw"};

    RockPaperScissorsResponse rockPaperScissorsResponse = new RockPaperScissorsResponse();

    public ResponseEntity<RockPaperScissorsResponse> playRockPaperScissors(RockPaperScissorsRequest rpsPlayerRequest) {

        updateComputerMove(rand.nextInt(3));

        if (rpsPlayerRequest.rpsPlayerMove.equalsIgnoreCase(computerMove)) {
            updateResultStreak(RESULTS[2]);
            updateGameResultList(rpsPlayerRequest.rpsPlayerMove, computerMove, RESULTS[2]);
        } else if (
                (rpsPlayerRequest.rpsPlayerMove.equalsIgnoreCase(MOVES[0])
                && computerMove.equalsIgnoreCase(MOVES[2])) ||
                (rpsPlayerRequest.rpsPlayerMove.equalsIgnoreCase(MOVES[1])
                        && computerMove.equalsIgnoreCase(MOVES[0])) ||
                (rpsPlayerRequest.rpsPlayerMove.equalsIgnoreCase(MOVES[2])
                        && computerMove.equalsIgnoreCase(MOVES[1]))) {
            updateResultStreak(RESULTS[0]);
            updateGameResultList(rpsPlayerRequest.rpsPlayerMove, computerMove, RESULTS[0]);
        } else {
            updateResultStreak(RESULTS[1]);
            updateGameResultList(rpsPlayerRequest.rpsPlayerMove, computerMove, RESULTS[1]);
        }

        rockPaperScissorsResponse.setYouChose(rpsPlayerRequest.rpsPlayerMove);
        rockPaperScissorsResponse.setComputerChose(computerMove);
        rockPaperScissorsResponse.setStreak(currentStreak);
        rockPaperScissorsResponse.setOdds("1 in " + (int) odds + " : " + df.format(oddsPercent) + "%");
        rockPaperScissorsResponse.setComputerRockMoves(computerRockMoves);
        rockPaperScissorsResponse.setComputerPaperMoves(computerPaperMoves);
        rockPaperScissorsResponse.setComputerScissorsMoves(computerScissorsMoves);
        rockPaperScissorsResponse.setPreviousGameResultsList(previousGameResultsList);

        return ResponseEntity.ok(rockPaperScissorsResponse);
    }

    private void updateComputerMove(int cpuMove) {

        computerRockMoves = 0;
        computerPaperMoves = 0;
        computerScissorsMoves = 0;

        computerMove = MOVES[cpuMove];
        previousCpuMovesList.add(MOVES[cpuMove]);

        //count the computer moves for statistics
        for (String previousMove : previousCpuMovesList) {
            if (previousMove.equalsIgnoreCase(MOVES[0])) {
                computerRockMoves += 1;
            } else if (previousMove.equalsIgnoreCase(MOVES[1])) {
                computerPaperMoves += 1;
            } else {
                computerScissorsMoves += 1;
            }
        }
    }

    private void updateResultStreak(String result) {
        rockPaperScissorsResponse.setResult(result);
        previousResultsList.add(result);

        //initiated as 1 because the loop will only count when there is a streak (consecutive equal result > 1)
        playerStreak = 1;

        /*
        This loop iterates through the result list in reverse order.
        Starting from the second-to-last element (recentResultsList.size() - 2) and moving towards the beginning (i--).
        This is done so that if the result of i is not greater than 1 it means there is nothing to compare.
        It will then break the loop and not add to the streak count in the list.
        Else, it will add to the streak count until the consecutive results no longer match.
        */
        for (int i = previousResultsList.size() - 2; i >= 0; i--) {
            if (previousResultsList.get(i).equals(previousResultsList.get(i + 1))) {
                playerStreak++;
            } else {
                break; // Stop counting when a different element is encountered
            }
        }

        //will still say a streak of 1 if it is not a streak as it ties into the odds information
        currentStreak = result + " streak of " + playerStreak;

        //calculate the odds of the streak
        odds = Math.pow(3, playerStreak);

        oddsPercent = 1/odds;

    }

    private void updateGameResultList(String playerMove, String computerMove, String gameResult) {
        GameData gameData = new GameData(playerMove, computerMove, gameResult);
        previousGameResultsList.add(0, gameData);
        if(previousGameResultsList.size() > 5) {
            previousGameResultsList.remove(previousGameResultsList.size() - 1);
        }
        updateGameResultDatabaseTable(gameData);
    }

    private void updateGameResultDatabaseTable(GameData gameData) {
        try (Connection connection = DatabaseConnection.connect()) {
            String sql = "INSERT INTO gameresults (player_move, computer_move, game_result) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Set values for the parameters
                preparedStatement.setString(1, gameData.getPlayerMove());
                preparedStatement.setString(2, gameData.getComputerMove());
                preparedStatement.setString(3, gameData.getGameResult());

                // Execute the insert statement
                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println(rowsAffected + " row(s) inserted successfully.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
