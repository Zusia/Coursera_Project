package com.example.demo.core.tournament;

import com.example.demo.base.BaseRequest;
import com.example.demo.core.team.Team;
import com.example.demo.core.team.TeamRepository;
import com.example.demo.core.tournament.converter.TournamentToTournamentViewConverter;
import com.example.demo.core.tournament.web.TournamentBaseRequest;
import com.example.demo.core.tournament.web.TournamentView;
import org.aspectj.bridge.MessageUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import com.example.demo.error.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.parser.Entity;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TournamentService {
    private final TournamentRepository tournamentRepository;
    private final TournamentToTournamentViewConverter tournamentToTournamentViewConverter;
    private final TeamRepository teamRepository;
    private final MessageUtil messageUtil;

    public TournamentService(TournamentRepository tournamentRepository, TeamRepository teamRepository,
                             TournamentToTournamentViewConverter tournamentToTournamentViewConverter,
                             MessageUtil messageUtil){
        this.tournamentRepository = tournamentRepository;
        this.teamRepository = teamRepository;
        this.tournamentToTournamentViewConverter = tournamentToTournamentViewConverter;
        this.messageUtil = messageUtil;
    }
    public TournamentView getTournament(Long id) {
        Tournament tournament = findTournamentOrThrow(id);
        return tournamentToTournamentViewConverter.convert(tournament);
    }
        public Tournament findTournamentOrThrow(Long id){
            return tournamentRepository.findById(id).orElseThrow(()->new
                    EntityNotFoundException(messageUtil.getMessage("tournament.NotFound", id)));
        }
        public Page<TournamentView> findAllTournament(Pageable pageable){
            Page<Tournament> tournaments = tournamentRepository.findAll(pageable);
            List<TournamentView> tournamentViews = new ArrayList<>();
            tournaments.forEach(tournament -> {
                TournamentView tournamentView = tournamentToTournamentViewConverter.convert(tournament);
                tournamentViews.add(tournamentView);
            });
            return new PageImpl<>(tournamentViews, pageable, tournaments.getTotalElements());
        }
        public TournamentView create(TournamentBaseRequest tournamentBaseRequest){
            Tournament tournament = new Tournament();
            this.prepare(tournament, tournamentBaseRequest);
            Tournament tournamentSave = tournamentRepository.save(tournament);
            return tournamentToTournamentViewConverter.convert(tournamentSave);
        }
        @Transactional
        public void delete(Long id){
            try {
                tournamentRepository.deleteById(id);
            }catch (EmptyResultDataAccessException e){
                throw new EntityNotFoundException(messageUtil.getMessage("tournament.NotFound", id));
            }
        }
        public TournamentView update(Tournament tournament, TournamentBaseRequest tournamentBaseRequest){
            Tournament newTournament = this.prepare(tournament, tournamentRequest);
            Tournament tournamentSave = tournamentRepository.save(newTournament);
            return tournamentToTournamentViewConverter.convert(tournamentSave);
        }
        public Tournament prepare(Tournament tournament, TournamentBaseRequest tournamentBaseRequest){
            tournament.setName(tournamentRequest.getName());
            tournament.setCountry(tournamentRequest.getCountry());
            List<Team> teamList = teamRepository.findAllById(tournamentBaseRequest.getTeams().steam().map(BaseRequest.Id::getId).collect(Collectors.toSet()));
            Set<Team> teams = new HashSet<>(teamList);
            tournament.setTeams(teams);
            return tournament;
        }
}
