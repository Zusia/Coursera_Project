package com.example.demo.core.match;

import com.example.demo.core.match.converter.MatchToMatchViewConerter;
import com.example.demo.core.match.web.MatchBaseRequest;
import com.example.demo.core.match.web.MatchLastRequest;
import com.example.demo.core.match.web.MatchView;
import org.aspectj.bridge.MessageUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MatchService {
    private final MatchRepository matchRepository;
    private final MatchToMatchViewConerter matchToMatchViewConerter;
    private final TeamRepository teamRepository;
    private final TournamentRepository tournamentRepository;
    private final MessageUtil messageUtil;

    public MatchService(MatchRepository matchRepository, MatchToMatchViewConerter matchToMatchViewConerter,
                        TeamRepository teamRepository, TournamentRepository tournamentRepository, MessageUtil messageUtil){
        this.matchRepository = matchRepository;
        this.matchToMatchViewConerter = matchToMatchViewConerter;
        this.teamRepository = teamRepository;
        this.tournamentRepository = tournamentRepository;
        this.messageUtil = messageUtil;
    }

    public List <MatchView> findLastMatch(MatchLastRequest request){
        List<Match> matches = matchRepository.getLastMatch(request.getPlayerSurname(), request.getPlayerName());
        List<MatchView> matchViews = new ArrayList<>();
        matches.forEach(match -> matchViews.add(matchToMatchViewConerter.convert(match)));
        return matchViews;
    }

    public Match findMatchOrThrow (Long id){
        return matchRepository.findById(id).orElseThrow(()->new EntityNotFoundException(messageUtil.getMessage("match.NotFound",id)));
    }
    public MatchView getMatch(Long id){
        Match match = findMatchOrThrow(id);
        return matchToMatchViewConerter.convert(match);
    }
    public Page<MatchView> findAllMatch(Pageable pageable){
        Page<Match> matches = matchRepository.findAll(pageable);
        List<MatchView> matchViews = new ArrayList<>();
        matches.forEach(match -> {
            MatchView matchView = matchToMatchViewConerter.convert(match);
            matchViews.add(matchView);
        });
        return new PageImpl<>(matchViews, pageable,matches.getTotalElements());
    }
    public MatchView create(MatchBaseRequest request){
        Match match=new Match();
        this.prepare(match, request);
        Match matchSave = matchRepository.save(match);
        return MatchToMatchViewConerter.convert(matchSave);
    }

    public void delete(Long id){
        try {
            matchRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException(messageUtil.getMessage("match.NotFound",id));
        }
    }
    public MatchView update(Match match, MatchBaseRequest request){
        Match newMatch = this.prepare(match, request);
        Match matchSave = matchRepository.save(newMatch);
        return matchToMatchViewConerter.convert(matchSave);
    }
    private Match prepare(Match match, MatchBaseRequest request){
        match.setMatchDate(request.getMatchDate());
        match.setScoreOwners(request.getScoreOwners());
        match.setScoreGuests(request.getScoreGuests());
        match.setTournament(tournamentRepository.getOne(request.getTournamentId()));
        match.setOwner(teamRepository.getOne(request.getTeamOwnerId()));
        match.setGuest(teamRepository.getOne(request.getTeamGuestId()));
        return match;
    }

}
