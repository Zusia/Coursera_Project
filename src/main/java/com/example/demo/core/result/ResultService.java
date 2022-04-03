package com.example.demo.core.result;

import com.example.demo.core.result.converter.ResultToResultViewConverter;
import com.example.demo.core.result.web.ResultBaseRequest;
import com.example.demo.core.result.web.ResultView;
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
public class ResultService {
    private final ResultRepository resultRepository;
    private final ResultToResultViewConverter resultToResultViewConverter;
    private final TeamRepository teamRepository;
    private final TournamentRepository tournamentRepository;
    private final MessageUtil messageUtil;
    public ResultService(ResultRepository resultRepository, ResultToResultViewConverter resultToResultViewConverter,
                         TeamRepository teamRepository, TournamentRepository tournamentRepository, MessageUtil messageUtil){
        this.resultRepository = resultRepository;
        this.resultToResultViewConverter = resultToResultViewConverter;
        this.teamRepository = teamRepository;
        this.tournamentRepository = tournamentRepository;
        this.messageUtil = messageUtil;
    }
    public ResultView getResult(Long id){
        Result result = findResultOrThrow(id);
        return  resultToResultViewConverter.convert(result);
    }
    public Page<ResultView> findAllResult(Pageable pageable){
        Page<Result> Results = resultRepository.findAll(pageable);
        List<ResultView> resultViews = new ArrayList<>();
        Results.forEach(result -> {
            ResultView resultView = resultToResultViewConverter.convert(result);
            resultViews.add(resultView);
        });
        return new PageImpl<>(resultViews, pageable, results.getTotalElements());
    }
    public ResultView create(ResultBaseRequest resultBaseRequest){
        Result result = new Result();
        this.prepare(result, resultBaseRequest);
        Result resultSave = resultRepository.save(result);
        return resultToResultViewConverter.convert(resultSave);
    }
    @Transactional
    public void delete(Long id){
        try {
            resultRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException(messageUtil.getMessage("result.NotFound", id));
        }
    }
    public ResultView update(Result result, ResultBaseRequest resultBaseRequest){
        Result newResult = this.prepare(result,resultBaseRequest);
        Result resultSave = resultRepository.save(newResult);
        return resultToResultViewConverter.convert(resultSave);
    }
    public Result prepare(Result result, ResultBaseRequest resultBaseRequest){
        result.setDraw(resultBaseRequest.getDraw());
        result.setGoals(resultBaseRequest.getGoals());
        result.setGoals_missed(resultBaseRequest.getGoalsMissed());
        result.setLoses(resultBaseRequest.getGoals());
        result.setMissed(resultBaseRequest.getLoses());
        result.setMissed(resultBaseRequest.getMissed());
        result.setPlace(resultBaseRequest.getPlace());
        result.setWins(resultBaseRequest.getWins());
        result.setPoints(resultBaseRequest.getPoints());

        result.setTeam(teamRepository.getOne(resultBaseRequest.getTeamId()));
        result.setTournament(tournamentRepository.getOne(resultBaseRequest.getTournamentId()));
        return result;
    }
}
