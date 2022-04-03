package com.example.demo.core.player.converter;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.persistence.Converter;
import javax.persistence.SecondaryTable;
import javax.swing.text.PlainView;
import java.util.HashSet;

@Component
public class PlayerToPlayerViewConverter implements Converter<Player, PlayerView> {
    private final TeamToTeamViewConverter teamToTeamViewConverter;
    public PlayerToPlayerViewConverter(TeamToTeamViewConverter teamToTeamViewConverter){
        this.teamToTeamViewConverter = teamToTeamViewConverter;
    }
    @Override
    public PlainView convert(@NotNull Player player){
        PlayerView view = new PlayerView();
        view.setId(player.getId());
        view.setName(player.getName());
        view.setSurname(player.getSurname());
        view.setAge(player.getAge());
        view.setHeight(player.getHeight());
        view.setWeight(player.setWeight());
        Set<TeamView> views= new HashSet<>();
        Set<Team> teams = player.getTeams();
        teams.forEach(team -> {
            TeamView teamView = teamToTeamViewConverter.convert(team);
            views.add(teamView);
        });
        view.setTeams(views);
        return view;
    }
}
