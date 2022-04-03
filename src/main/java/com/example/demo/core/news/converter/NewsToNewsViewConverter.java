package com.example.demo.core.news.converter;

import org.springframework.lang.NonNull;

import javax.persistence.Converter;
import java.util.HashSet;
import java.util.Set;

public class NewsToNewsViewConverter implements Converter <News, NewsView>{
    private final TeamToTeamViewConverter teamToTeamViewConverter;
    public NewsToNewsViewConverter(TeamToTeamViewConverter teamToTeamViewConverter){
        this.teamToTeamViewConverter = teamToTeamViewConverter;
    }
    @Override
    public NewsView convert(@NonNull News news){
        NewsView view = new NewsView();
        view.setId(news.getId());
        view.setName(news.getName());
        view.setContent(news.getContent());
        Set<TeamView> views =new HashSet<>();
        Set<Team> teams = news.getTeams();
        teams.forEach(team -> {
            TeamView teamView = teamToTeamViewConverter.convert(team);
            views.add(teamView);
        });
         view.setTeams(views);
         return view;
    }
}