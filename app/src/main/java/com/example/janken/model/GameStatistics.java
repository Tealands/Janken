package com.example.janken.model;

import java.util.List;

public class GameStatistics {

    private int totalGames;
    private int rockCount;
    private int scissorsCount;
    private int paperCount;
    private int wins;
    private int losses;
    private int draws;
    private int rockWins;
    private int scissorsWins;
    private int paperWins;

    public GameStatistics(List<GameRecord> records) {
        totalGames = records.size();
        for (GameRecord r : records) {
            String hand = r.getPlayerHand();
            String result = r.getResult();
            if ("勝ち".equals(result)) {
                wins++;
            } else if ("負け".equals(result)) {
                losses++;
            } else if ("引き分け".equals(result)) {
                draws++;
            }
            if ("グー".equals(hand)) {
                rockCount++;
                if ("勝ち".equals(result)) rockWins++;
            } else if ("チョキ".equals(hand)) {
                scissorsCount++;
                if ("勝ち".equals(result)) scissorsWins++;
            } else if ("パー".equals(hand)) {
                paperCount++;
                if ("勝ち".equals(result)) paperWins++;
            }
        }
    }

    public double getRockRate() {
        return totalGames == 0 ? 0 : (double) rockCount / totalGames * 100;
    }

    public double getScissorsRate() {
        return totalGames == 0 ? 0 : (double) scissorsCount / totalGames * 100;
    }

    public double getPaperRate() {
        return totalGames == 0 ? 0 : (double) paperCount / totalGames * 100;
    }

    public double getRockWinRate() {
        return rockCount == 0 ? 0 : (double) rockWins / rockCount * 100;
    }

    public double getScissorsWinRate() {
        return scissorsCount == 0 ? 0 : (double) scissorsWins / scissorsCount * 100;
    }

    public double getPaperWinRate() {
        return paperCount == 0 ? 0 : (double) paperWins / paperCount * 100;
    }

    public int getTotalGames() { return totalGames; }
    public int getWins() { return wins; }
    public int getLosses() { return losses; }
    public int getDraws() { return draws; }
}
