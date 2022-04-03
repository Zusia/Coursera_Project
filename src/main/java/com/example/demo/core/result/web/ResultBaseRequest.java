package com.example.demo.core.result.web;

import com.example.demo.base.BaseRequest;
import org.jetbrains.annotations.NotNull;

public class ResultBaseRequest extends BaseRequest {
    private int points;
    private int place;
    private int wins;
    private int loses;
    private int draw;
    private int goalsMissed;
    private int missed;
    private int goals;

    @NotNull
    private Long tournamentId;

    @NotNull
    private Long teamId;

    public int getPlace(){
        return place;
    }
    public void setPlace(int place){
        this.place=place;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getGoalsMissed() {
        return goalsMissed;
    }

    public void setGoalsMissed(int goalsMissed) {
        this.goalsMissed = goalsMissed;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getMissed() {
        return missed;
    }

    public void setMissed(int missed) {
        this.missed = missed;
    }

    @NotNull
    public Long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(@NotNull Long tournamentId) {
        this.tournamentId = tournamentId;
    }

    @NotNull
    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(@NotNull Long teamId) {
        this.teamId = teamId;
    }
}
