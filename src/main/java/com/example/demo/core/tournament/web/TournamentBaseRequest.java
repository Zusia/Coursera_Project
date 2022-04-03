package com.example.demo.core.tournament.web;

import com.example.demo.base.BaseRequest;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class TournamentBaseRequest extends BaseRequest implements Serializable {
    @NotEmpty
    private String name;

    @NotNull
    private String country;

    private List<@Valid Id> teams;
    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public String getCountry() {
        return country;
    }

    public void setCountry(@NotNull String country) {
        this.country = country;
    }

    public List<Id> getTeams() {
        return teams;
    }

    public void setTeams(List<Id> teams) {
        this.teams = teams;
    }
}
