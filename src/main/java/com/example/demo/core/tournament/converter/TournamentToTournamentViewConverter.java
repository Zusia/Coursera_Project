package com.example.demo.core.tournament.converter;

import com.example.demo.core.team.converter.TeamToTeamViewConverter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Converter;

@Component
public class TournamentToTournamentViewConverter implements Converter<Tournament, TournamentView> {
    private final TeamToTeamViewConverter teamToTeamViewConverter;
    public TournamentToTournamentViewConverter(TeamToTeamViewConverter teamToTeamViewConverter){
        this.teamToTeamViewConverter = teamToTeamViewConverter;
    }
    @Override
    public TournamentView convert(@NotNull Tournament tournament){
        TournamentView view = new TournamentView();
        view.setId(tournament.getId());
        view.setName(tournament.getName());
        view.setCountry(tournament.getCountry());
        Set<TeamView> views = new HashSet<>();
        Set<Team> teams = tournament.getTeams();
        teams.forEach(team -> views.add(teamToTeamViewConverter.convert(team)));
        view.setTeams(views);
        return view;
    }
}
