package com.example.demo.core.team;

import com.example.demo.core.team.converter.TeamToTeamViewConverter;
import com.example.demo.core.team.web.TeamBaseRequest;
import com.example.demo.core.team.web.TeamView;
import org.aspectj.bridge.MessageUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamToTeamViewConverter teamToTeamViewConverter;
    private final MessageUtil messageUtil;
    public TeamService(TeamRepository teamRepository, TeamToTeamViewConverter teamToTeamViewConverter, MessageUtil messageUtil){
        this.teamRepository = teamRepository;
        this.teamToTeamViewConverter = teamToTeamViewConverter;
        this.messageUtil = messageUtil;
    }
    public TeamView getTeam(Long id){
        Team team = findTeamOrThrow(id);
        return teamToTeamViewConverter.convert(team);
    }
    public Team findTeamOrThrow(Long id){
        return teamRepository.findById(id).orElseThrow(()->new EntityNotFoundException(messageUtil.getMessage("team.NotFound",id)));
    }
    public Page<TeamView> findAllTeam(Pageable pageable){
        Page<Team> teams = teamRepository.findAll(pageable);
        List<TeamView> teamViews =  new ArrayList<>();
        teams.forEach(team -> {
            TeamView teamView = teamToTeamViewConverter.convert(team);
            teamViews.add(teamView);
        });
        return new PageImpl<>(teamViews, pageable, teams.getTotalElements());
    }
    public TeamView create(TeamBaseRequest teamBaseRequest){
        Team team = new Team();
        this.prepare(team, teamBaseRequest);
        Team teamSave = teamRepository.save(team);
        return teamToTeamViewConverter.convert(teamSave);
    }
    @Transactional
    public void delete(Long id){
        try {
            teamRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException(messageUtil.getMessage("team.NotFound",id));
        }
    }
    public TeamView update(Team team, TeamBaseRequest teamBaseRequest){
        Team newTeam = this.prepare(team, teamBaseRequest);
        Team tournamentSave = teamRepository.save(newTeam);
        return teamToTeamViewConverter.convert(tournamentSave);
    }
    public Team prepare(Team team, TeamBaseRequest teamBaseRequest){
        team.setName(teamBaseRequest.getName());
        team.setNumPlayers(teamBaseRequest.getNumPlayers());
        return team;
    }
}
