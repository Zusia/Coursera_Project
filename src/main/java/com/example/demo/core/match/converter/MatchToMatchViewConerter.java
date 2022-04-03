package com.example.demo.core.match.converter;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.persistence.Converter;

@Component
public class MatchToMatchViewConerter implements Converter<Match, MatchView> {
    private final TeamToTeamViewConverter teamToTeamViewConverter;
    private final TournamentToTournamentViewConverter tournamentToTournamentViewConverter;
    public MatchToMatchViewConerter(TeamToTeamViewConverter teamToTeamViewConverter,
                                    TournamentToTournamentViewConverter tournamentToTournamentViewConverter){
        this.teamToTeamViewConverter = teamToTeamViewConverter;
        this.tournamentToTournamentViewConverter = tournamentToTournamentViewConverter;
    }
    @Override
    public MatchView convert(@NonNull Match match){
        MatchView view = new MatchView();
        view.setId(match.getId());
        view.setMatchDate(match.getMatchDate());
        view.setScoreOwners(match.getScoreOwners());
        view.setScoreGuests(match.getScoreGuests());
        Team guest = match.getGuest();
        view.setTeamGuest(teamToTeamViewConverter.convert(guest));
        Team owner = match.getOwner();
        view.setTeamOwner(teamToTeamViewConverter.convert(owner));
        Tournament tournament = match.getTournament();

        view.setTournament(tournamentToTournamentViewConverter.convert(tournament));
        return view;
    }
}
