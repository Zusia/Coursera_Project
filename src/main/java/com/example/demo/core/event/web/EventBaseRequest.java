package com.example.demo.core.event.web;

import com.example.demo.base.BaseRequest;
import com.sun.istack.NotNull;

import java.util.List;

public class EventBaseRequest extends BaseRequest {
    @NotNull
    private String name;

    @NotNull
    private String content;
    @NotEmpty
    private List<@Valid Id> players;

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Id> getPlayers() {
        return players;
    }

    public void setPlayers(List<Id> players) {
        this.players = players;
    }
}
