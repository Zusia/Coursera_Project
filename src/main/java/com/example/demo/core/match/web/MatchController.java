package com.example.demo.core.match.web;

import com.example.demo.core.match.converter.MatchToMatchViewConerter;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchController {
    private final MatchService service;
    public MatchController(MatchService service){
        this.service = service;
    }
    @GetMapping("/last")
    @ResponseBody
    public List<MatchView> getLast(@ResponseBody @Valid MatchLastRequest request){
        return service.findLastMatch(request);
    }
    @GetMapping("/{id}")
    @ResponseBody
    public MatchView getMatch(@PathVariable Long id){
        return service.getMatch(id);
    }
    @GetMapping({"","/"})
    @ResponseBody
    public Page<MatchView> getAllMatch(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return service.findAllMatch(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public MatchView create(@RequestBody @Valid MatchBaseRequest request){
        return service.create(request);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMatch(@PathVariable Long id){
        service.delete(id);
    }
    @PutMapping("/{id}")
    public MatchView updateTournament(@PathVariable(name = "id") Long id,@RequestBody @Valid MatchBaseRequest request){
        Match match = service.findMatchOrThrow(id);
        return service.update(match,request);
    }

}
