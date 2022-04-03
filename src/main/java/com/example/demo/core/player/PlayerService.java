package com.example.demo.core.player;

import com.example.demo.base.BaseRequest;
import com.example.demo.core.player.converter.PlayerToPlayerViewConverter;
import com.example.demo.core.player.web.PlayerBaseRequest;
import com.example.demo.core.player.web.PlayerView;
import org.aspectj.bridge.MessageUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerToPlayerViewConverter playerToPlayerViewConverter;
    private final TeamRepository teamRepository;
    private final MessageUtil messageUtil;
    public PlayerService (PlayerRepository playerRepository, PlayerToPlayerViewConverter playerToPlayerViewConverter,
                          TeamRepository teamRepository, MessageUtil messageUtil){
        this.playerRepository = playerRepository;
        this.playerToPlayerViewConverter = playerToPlayerViewConverter;
        this.teamRepository = teamRepository;
        this.messageUtil = messageUtil;
    }
    public Player findPlayerOrThrow( Long id){
        return playerRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(messageUtil.getMessage("player.NotFound", id)));
    }
    public PlayerView getPlayer(Long id){
        Player player = findPlayerOrThrow(id);
        return playerToPlayerViewConverter.convert(player);
    }
    public Page<PlayerView> findAllPlayer(Pageable pageable){
        Page<Player> players = playerRepository.findAll(pageable);
        List<PlayerView> playerViews = new ArrayList<>();
        players.forEach(player -> {
            PlayerView playerView = playerToPlayerViewConverter.convert(player);
            playerViews.add(playerView);
        });
        return new PageImpl<>(playerViews, pageable, players.getTotalElements());
    }

    public PlayerView create(PlayerBaseRequest request){
        Player player=new Player();
        this.prepare(player, request);
        Player playerSave = playerRepository.save(player);
        return playerToPlayerViewConverter.convert(playerSave);
    }
    @Transactional
    public void delete(Long id){
        try {
            playerRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException(messageUtil.getMessage("player.NotFound", id));
        }
    }
    public PlayerView update(Player player, PlayerBaseRequest request){
        Player newPlayer = this.prepare(player, request);
        Player playerSave = playerRepository.save(newPlayer);
        return playerToPlayerViewConverter.convert(playerSave);
    }
    public Player prepare(Player player, PlayerBaseRequest playerBaseRequest){
        player.setName(playerBaseRequest.getName());
        player.setSurname(playerBaseRequest.getSurname());
        player.setWeight(playerBaseRequest.getWeight());
        player.setHeight(playerBaseRequest.getHeight());
        player.setAge(playerBaseRequest.getAge());
        List<Team> teamList = teamRepository.findAllById(playerBaseRequest.getTeams().stream().map(BaseRequest.id::getId).collect(Collectors.toSet()));
        Set<Team> teams = new HashSet<>(teamList);
        player.setTeams(teams);
        return player;
    }
}
