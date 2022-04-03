package com.example.demo.core.news.web;

import com.example.demo.base.BaseRequest;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Id;
import java.util.List;

public class NewsBaseRequest extends BaseRequest {
    @NotNull
    private String name;

    @NotNull
    private String content;

    @NotEmpty
    private List<@Valid Id> teams;

    public  String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }
    public List<Id> getTeams(){
        return teams;
    }

    public void setTeams(List<Id> teams){
        this.teams= teams;
    }
}

