package com.example.demo.core.team.web;

import com.example.demo.base.BaseRequest;
import org.jetbrains.annotations.NotNull;

public class TeamBaseRequest extends BaseRequest {
    @NotEmpty
    private String name;

    @NotNull
    private int numPlayers;

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getNumPlayers(){
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }
}
