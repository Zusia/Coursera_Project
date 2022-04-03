package com.example.demo.core.team.converter;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.persistence.Converter;

@Component
public class TeamToTeamViewConverter implements Converter<Team, TeamView> {
    @Override
    public TeamView convert(@NotNull Team team){
        TeamView view = new TeamView();
        view.setId(team.getId());
        view.setName(team.getName());
        view.setNumPlayers(team.getNumPlayers());
        return view;
    }
}
