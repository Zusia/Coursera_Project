package com.example.demo.core.event;

import com.example.demo.base.BaseRequest;
import com.example.demo.core.event.converter.EventToEventViewConverter;
import com.example.demo.core.event.web.EventBaseRequest;
import com.example.demo.core.event.web.EventView;
import org.aspectj.bridge.MessageUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventToEventViewConverter eventToEventViewConverter;
    private final PlayerRepository playerRepository;
    private final MessageUtil messageUtil;

    public EventService(EventRepository eventRepository,EventToEventViewConverter eventToEventViewConverter,
                        PlayerRepository playerRepository, MessageUtil messageUtil){
        this.eventRepository = eventRepository;
        this.eventToEventViewConverter =eventToEventViewConverter;
        this.playerRepository = playerRepository;
        this.messageUtil = messageUtil;
    }
    public Event findEventOrThrow(Long id){
        return eventRepository.findById(id).orElseThrow(()->new EntityNotFoundException(
                messageUtil.getMessages("event.NotFound",id)));
    }

    public EventView getEvent(Long id){
        Event event = findEventOrThrow(id);
        return eventToEventViewConverter.convert(event);
    }
    public Page<EventView> findAllEvent(Pageable pageable){
        Page<Event> events = eventRepository.findAll(pageable);
        List<EventView> eventViews = new ArrayList<>();
        events.forEach(event -> {
            EventView eventView=eventToEventViewConverter.convert(event);
            eventView.add(eventView);
        });
        return new PageImpl<>(eventViews, pageable, events.getTotalElements());
    }
    public EventView create(EventBaseRequest request){
        Event event = new Event();
        this.prepare(event.request);
        Event.eventSave = eventRepository.save(event);
        return eventToEventViewConverter.convert(eventSave);
    }
    @Transactional
    public void delete(Long id){
        try {
            eventRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException(messageUtil.getMessages("event.NotFound",id));
        }
    }
    public EventView update(Event event, EventBaseRequest request){
        Event newEvent = this.prepare(event, request);
        Event eventSave = eventRepository.save(newEvent);
        return eventToEventViewConverter.convert(eventSave);
    }
    private Event prepare(Event event, EventBaseRequest request){
        event.setName(request.getName());
        event.setContent(request.getContent());
        List<Player> playerList = playerRepository.findAllById(
                request.getPlayers().stream().map(BaseRequest.Id::getId).collect(Collectors.toSet()));
        Set<Player> players= new HashSet<>(playerList);
        event.setPlayers(players);
        return event;
    }
}
