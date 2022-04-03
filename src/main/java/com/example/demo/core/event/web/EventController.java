package com.example.demo.core.event.web;

import com.example.demo.core.event.converter.EventToEventViewConverter;
import jdk.internal.event.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping9("/event")
public class EventController {
    private final EventService service;
    public EventController(final EventService service){
        this.service = service;
    }
    @GetMapping("/{id}")
    @ResponseBody
    public EventView getEvent(@PathVariable Long id){
        return service.getEvent(id);
    }
    @GetMapping
    @ResponseBody
    public Page<EventView> getAllEvent(@PageableDefault (sort="id", direction = Sort.Direction.ASC)Pageable pageable){
        return service.findAllEvent(pageable);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public EventView create(@RequestBody @Valid EventBaseRequest request){
        return service.create(request);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable Long id){
        service.delete(id);
    }
    @PutMapping("/{id}")
    public EventView updateEvent(@PathVariable(name = "id") Long id, @RequestBody @Valid EventBaseRequest request){
        Event event = service.findEventOrThrow(id);
        return service.update(event, request);
    }
}
