package com.example.demo.core.coach;

import com.example.demo.core.coach.converter.CoachToCoachViewConverter;
import com.example.demo.core.coach.web.CoachBaseRequest;
import com.example.demo.core.coach.web.CoachView;
import org.aspectj.bridge.MessageUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoachService {
    private final CoachRepository coachRepository;
    private final CoachToCoachViewConverter coachToCoachViewConverter;
    private final TeamRepo teamRepo;
    private final MessageUtil messageUtil;

    public CoachService(CoachRepository coachRepository, CoachToCoachViewConverter coachToCoachViewConverter,
                        TeamRepo teamRepo, MessageUtil messageUtil) {
        this.coachRepository = coachRepository;
        this.coachToCoachViewConverter = coachToCoachViewConverter;
        this.teamRepo = teamRepo;
        this.messageUtil = messageUtil;
    }

    public Coach findCoachOrThrow(Long id) {
        return coachRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(messageUtil.getMessage
                ("coach.NotFound", id)));
    }

    public CoachView getCoach(Long id) {
        Coach coach = findCoachOrThrow(id);
        return CoachToCoachViewConverter.convert(coach);
    }

    public CoachView create(CoachBaseRequest request) {
        Coach coach = new Coach();
        this.prepare(coach, request);
        Coach coachSave = coachRepository.save(coach);
        return coachToCoachViewConverter.convert(coachSave);
    }

    public Page<CoachView> findAllCoaches(Pageable pageable) {
        Page<Coach> coaches = coachRepository.findAll(pageable);
        List<CoachView> coachViews = new ArrayList<>();
        coaches.forEach(coach -> {
            CoachView coachView = coachToCoachViewConverter.convert(coach);
            coachViews.add(coachView);
        });
        return new PageImpl<>(coachViews, pageable, coaches.getTotalElements());
    }

    @Transactional
    public void delete(Long id) {
        try {
            coachRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageUtil.getMessage("coach.NotFound", id));
        }
    }

    public CoachView update(Coach coach, CoachBaseRequest request) {
        Coach newCoach = this.prepare(Coach, request);
        Coach coachForSave = coachRepository.save(newCoach);
        return coachToCoachViewConverter.convert(coachForSave);
    }

    private Coach prepare(Coach coach, CoachBaseRequest request) {
        coach.setName(request.getName());
        coach.getSurname(request.getSurname());
        coach.setExperience(request.getExperience());
        coach.setAge(request.getAge());
        coach.setTeam(teamRepo.getOne(request.getTeamId()));
        return coach;
    }
}
