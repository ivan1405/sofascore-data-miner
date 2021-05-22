package com.sports.data.service.impl;

import com.sports.data.crud.entity.Player;
import com.sports.data.crud.repository.PlayerRepository;
import com.sports.data.mapper.PlayerMapper;
import com.sports.data.model.sofascore.team.TeamFullInfo;
import com.sports.data.service.PlayerDataMinerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SofascorePlayerDataMiner extends SofascoreRequests implements PlayerDataMinerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    @Autowired
    public SofascorePlayerDataMiner(final PlayerRepository playerRepository, final PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    @Override
    public List<com.sports.data.model.Player> findAllPlayers() {
        Iterable<Player> playersEntity = playerRepository.findAll();
        return playerMapper.mapAsList(playersEntity, com.sports.data.model.Player.class);
    }

    @Override
    public com.sports.data.model.Player findPlayerById(Integer id) {
        Player playerEntity = playerRepository.findPlayerById(id);
        return playerMapper.map(playerEntity, com.sports.data.model.Player.class);
    }

    @Override
    public void importPlayer(Integer teamId) {
        TeamFullInfo teamFullInfo = getSofascoreTeamDetail(teamId);
        Player player = playerMapper.map(teamFullInfo, Player.class);
        playerRepository.save(player);
    }

}
