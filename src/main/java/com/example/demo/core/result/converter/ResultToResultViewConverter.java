package com.example.demo.core.result.converter;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.persistence.Converter;

@Component
public class ResultToResultViewConverter implements Converter<Result, ResultView> {
    private final TeamToTeamViewConverter teamToTeamViewConverter;
    private final TournamentToTournamentViewConverter tournamentToTournamentViewConverter;

    public ResultToResultViewConverter(TeamToTeamViewConverter teamToTeamViewConverter,
                                       TournamentToTournamentViewConverter tournamentToTournamentViewConverter) {
        this.teamToTeamViewConverter = teamToTeamViewConverter;
        this.tournamentToTournamentViewConverter = tournamentToTournamentViewConverter;
    }

    @Override
    public ResultView convert(@NonNull Result result) {
        ResultView view = new ResultView();
        view.setId(result.getId());
        view.setDraw(result.getDraw());
        view.setGoals(result.getGoals());
        view.setGoalsMissed(result.getGoalsMissed());
        view.setLoses(result.getLoses());
        view.setMissed(result.getMissed());
        view.setPlace(result.getPlace());
        view.setWins(result.getWins());
        view.setPoints(result.getPoints());
        Team team = result.getTeam();
        TeamView teamView = teamToTeamViewConverter.convert(team);
        view.setTeam(teamView);
        Tournament tournament = result.getTournament();
        tournamentToTournamentViewConverter.convert(tournament);
        view.setTournament(tournamentView);
        return view;
    }
}
