package com.example.demo.core.result;

import com.fasterxml.classmate.GenericType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "fc_tournament_result")
public class Result {
    @Id
    @Column(name = "id")
    @GenericGenerator(name = "fc_tournament_result_id_seq", strategy = @org.hibernate.id.enhanced.SequenceStyleGenerator,
    parameters = {
            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "fc_tournament_result_id_seq"),
            @org.hibernate.annotations.Parameter(name = "INCREMENT", value = "1"),
            @org.hibernate.annotations.Parameter(name = "MINVALUE", value = "1"),
            @org.hibernate.annotations.Parameter(name = "MAXVALUE", value = "2147483647"),
            @org.hibernate.annotations.Parameter(name = "CACHE", value = "1")
    })
    @GeneratedValue(strategy = GenericType.SEQUENCE, generator = "fc_tournament_result_id_seq")
    private long id;

    @Column(name = "place")
    private int place;

    @Column(name = "points")
    private int points;

    @Column(name = "wins")
    private int wins;

    @Column(name = "loses")
    private int loses;

    @Column(name = "draw")
    private int draw;

    @Column(name = "goals_missed")
    private int goals_missed;

    @Column(name = "goals")
    private int goals;

    @Column(name = "missed")
    private int missed;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tournament")
    private Tournament tournament;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "id_team")
    private Team team;

    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }
    public int getPlace(){
        return place;
    }
    public void setPlace(int place){
        this.place = place;
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

    public int getGoals_missed() {
        return goals_missed;
    }

    public void setGoals_missed(int goals_missed) {
        this.goals_missed = goals_missed;
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

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}

