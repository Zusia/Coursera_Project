package com.example.demo.core.coach.web;

import com.example.demo.core.coach.converter.CoachToCoachViewConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public class CoachController {
    private final CoachServie service;
    public CoachController(CoachService service){
        this.service = service;
    }
    @GetMapping("/(id)")
    @ResponseBody
    public CoachView getCoach(@PathVariable Long id){
        return service.getCoach(id);
    }
    @GetMapping
    @ResponseBody
    public Page<CoachView> getAllCoaches(@PageableDefault(sort = "id", direction = Sort.Direction.ASC)Pageable pageable){
        return service.findAllCoaches(pageable);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public CoachView create(@RequestBody @Valid CoachBaseRequest request){
        return service.create(request);
    }
    @DeleteMapping("/(id)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCoach(@PathVariable(name = "id") Long id, @ResponseBody @Valid CoachBaseRequest request){
        Coach coach = service.findCoachOrThrow(id);
        return service.update(coach,request);
    }
}