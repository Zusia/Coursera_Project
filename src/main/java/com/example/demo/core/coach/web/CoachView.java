package com.example.demo.core.coach.web;

import com.example.demo.core.team.web.TeamView;

public class CoachView {
    private long id;
    private String name;
    private String surname;
    private int age;
    private int experience;
    private TeamView team;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getSurname(){
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public TeamView getTeam() {
        return team;
    }

    public void setTeam(TeamView team) {
        this.team = team;
    }

    public void setAge(int age) { this.age = age;
    }
}
