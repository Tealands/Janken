package com.example.janken.model;

public class GameRecord {
    private long id;
    private String playerHand;
    private String cpuHand;
    private String result;
    private String timestamp;

    public GameRecord() {}

    public GameRecord(String playerHand, String cpuHand, String result, String timestamp) {
        this.playerHand = playerHand;
        this.cpuHand = cpuHand;
        this.result = result;
        this.timestamp = timestamp;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getPlayerHand() { return playerHand; }
    public void setPlayerHand(String playerHand) { this.playerHand = playerHand; }

    public String getCpuHand() { return cpuHand; }
    public void setCpuHand(String cpuHand) { this.cpuHand = cpuHand; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
