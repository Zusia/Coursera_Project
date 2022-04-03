package com.example.demo.core.tournament.web;

import com.example.demo.core.tournament.Tournament;
import com.example.demo.core.tournament.TournamentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tournament")
public class TournamentController {
    private final TournamentService service;
    public TournamentController(TournamentService service){
        this.service = service;
    }
    @GetMapping("/{id}")
    @ResponseBody
    public TournamentView getTournament(@PathVariable Long id){
        return service.getTournament(id);
    }

    @GetMapping
    @ResponseBody
    public Page<TournamentView>
    getAllTournament(@PageableDefault(sort = "id", direction = Sort.Direction.ASC)Pageable pageable){
        return service.findAllTournament(pageable);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public TournamentView create(@RequestBody @Valid TournamentBaseRequest tournamentBaseRequest){
        return service.create(tournamentBaseRequest);
    }
    @DeleteMapping("/{id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTournament(@PathVariable Long id){
        service.delete(id);
    }

    @PutMapping("/{id}")
    public TournamentView updateTournament(@PathVariable(name = "id") Long id, @RequestBody @Valid TournamentBaseRequest tournamentBaseRequest){
        Tournament tournament = service.findTournamentOrThrow(id);
        return service.update(tournament, tournamentBaseRequest);
    }
}
