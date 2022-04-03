package com.example.demo.core.event.converter;

import com.sun.istack.NotNull;
import com.sun.tools.javac.util.Convert;
import jdk.internal.event.Event;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class EventToEventViewConverter implements Converter<Event, EventView> {
    private final PlayerToPlayerViewConverter playerToPlayerViewConverter;
    public EventToEventViewConverter(PlayerToPlayerViewConverter playerToPlayerViewConverter){
        this.playerToPlayerViewConverter=playerToPlayerViewConverter;
    }
    @Override
    public EventView convert(@NotNull Event event){
        EventView view = new EventView();
        view.setId(event.getId());
        view.setName(event.getName());
        view.setContent(event.getContent());
        Set<PlayerView> views = new HashSet<>();
        Set<Player> players = event.getPlayers();
        players.forEach(player ->(views.add(playerToPlayerViewConverter.convert(player))));
        views.setPlayers(views);
        return view;

    }
}
