package com.example.demo.core.coach.web;

import com.example.demo.base.BaseRequest;
import com.sun.istack.NotNull;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CoachBaseRequest extends BaseRequest {
    @NotEmpty
    private String name;

    @NotEmpty
    private String surname;

    private int age;
    private int experience;

    @NotNull
    private Long teamId;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getSurname(){
        return surname;
    }
    public void setSurname(String surname){
        this.surname = surname;
    }

    public int getAge(int age){
        this.age = age;
    }

    public int getExperience(){
        return experience;
    }
    public void setExperience(int experience){
        this.experience = experience;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
}
