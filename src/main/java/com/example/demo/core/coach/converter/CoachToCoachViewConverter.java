package com.example.demo.core.coach.converter;

import com.example.demo.core.coach.Coach;
import com.example.demo.core.coach.web.CoachView;
import com.example.demo.core.team.Team;
import com.example.demo.core.team.converter.TeamToTeamViewConverter;
import com.sun.istack.NotNull;
import org.springframework.stereotype.Component;

import javax.persistence.Converter;
import java.lang.annotation.Annotation;

@Component
public class CoachToCoachViewConverter implements Converter<Coach, CoachView> {
    private final TeamToTeamViewConverter teamToTeamViewConverter;
    public CoachToCoachViewConverter(TeamToTeamViewConverter teamToTeamViewConverter){
        this.teamToTeamViewConverter = teamToTeamViewConverter;
    }
    @Override
    public CoachView convert(@NotNull Coach coach){
        CoachView view = new CoachView();
        view.setId(coach.getId());
        view.setName(coach.getName());
        view.setSurname(coach.getSurname());
        view.setAge(coach.getAge());
        view.setExperience(coach.getExperience());
        Team team = coach.getTeam();
        view.setTeam(teamToTeamViewConverter.convert(team));
        return view;
    }
}
