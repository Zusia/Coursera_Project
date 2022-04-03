package com.example.demo.core.team.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
public class TeamController {
    private final TeamService service;
    public TeamController(TeamService service){
        this.service = service;
    }
    @GetMapping("/{id}")
    @ResponseBody
    public TeamView getTeam(@PathVariable Long id){
        return service.getTeam(id);
    }
    @GetMapping
    @ResponseBody
    public Page<TeamView> getAllTeam(@PageableDefault(sort = "id", direction = Sort.Direction.ASC)Pageable pageable){
        return service.findAllTeam(pageable);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public TeamView create(@RequestBody @Valid TeamBaseRequest teamBaseRequest){
        return service.create(teamBaseRequest);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeam(@PathVariable Long id){
        service.delete(id);
    }
    @PutMapping("/{id}")
    public TeamView updateTeam(@PathVariable(name = "id") Long id, @ResponseBody @Valid TeamBaseRequest teamBaseRequest){
        Team team = service.findTeamOrThrow(id);
        return service.update(team, teamBaseRequest);
    }
}
