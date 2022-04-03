package com.example.demo.core.match.web;

public class MatchLastRequest{
    @NotEmpty
    private String playerSurname;

    @NotEmpty
    private String playerName;

    public String getPlayerName(){
        return playerName;
    }
    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }

    public String getPlayerSurname() {
        return playerSurname;
    }
    public void setPlayerSurname(String playerSurname) {
        this.playerSurname = playerSurname;
    }
}
