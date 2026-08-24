package com.example.janken.model;

import java.util.Random;

public class JankenGame {

    public enum Hand {
        ROCK, SCISSORS, PAPER, UNKNOWN
    }

    public enum Result {
        WIN, LOSE, DRAW
    }

    private int playerScore = 0;
    private int cpuScore = 0;
    private static final int WIN_COUNT = 3;
    private final Random random = new Random();

    public Hand decideCpuHand() {
        int r = random.nextInt(3);
        switch (r) {
            case 0: return Hand.ROCK;
            case 1: return Hand.SCISSORS;
            default: return Hand.PAPER;
        }
    }

    public Result judge(Hand player, Hand cpu) {
        if (player == cpu) return Result.DRAW;
        if ((player == Hand.ROCK && cpu == Hand.SCISSORS)
                || (player == Hand.SCISSORS && cpu == Hand.PAPER)
                || (player == Hand.PAPER && cpu == Hand.ROCK)) {
            return Result.WIN;
        }
        return Result.LOSE;
    }

    public void applyResult(Result result) {
        if (result == Result.WIN) {
            playerScore++;
        } else if (result == Result.LOSE) {
            cpuScore++;
        }
    }

    public boolean isPlayerWinner() {
        return playerScore >= WIN_COUNT;
    }

    public boolean isCpuWinner() {
        return cpuScore >= WIN_COUNT;
    }

    public boolean isGameOver() {
        return isPlayerWinner() || isCpuWinner();
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getCpuScore() {
        return cpuScore;
    }

    public void reset() {
        playerScore = 0;
        cpuScore = 0;
    }

    public static String handToString(Hand hand) {
        switch (hand) {
            case ROCK:     return "グー";
            case SCISSORS: return "チョキ";
            case PAPER:    return "パー";
            default:       return "不明";
        }
    }

    public static String resultToString(Result result) {
        switch (result) {
            case WIN:  return "勝ち";
            case LOSE: return "負け";
            default:   return "引き分け";
        }
    }
}
