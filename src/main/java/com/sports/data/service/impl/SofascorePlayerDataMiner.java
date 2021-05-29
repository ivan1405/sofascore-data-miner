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
        Player entityPlayer = playerRepository.findPlayerByPlayerId(teamId);
        TeamFullInfo teamFullInfo = getSofascoreTeamDetail(teamId);
        Player newPlayerInfo = playerMapper.map(teamFullInfo, Player.class);
        playerRepository.save(entityPlayer == null ? newPlayerInfo : updatePlayerData(entityPlayer, newPlayerInfo));
    }

    /**
     * Updates current player with new information
     *
     * @param oldPlayer current information of the player
     * @param newPlayer possible new information to update
     * @return the updated player
     */
    private Player updatePlayerData(Player oldPlayer, Player newPlayer) {
        log.info(oldPlayer.getName() + " is being updated");
        oldPlayer.setBestRanking(newPlayer.getBestRanking());
        oldPlayer.setRanking(newPlayer.getRanking());
        oldPlayer.setPoints(newPlayer.getPoints());
        oldPlayer.setPreviousRanking(newPlayer.getPreviousRanking());
        oldPlayer.setPrizeCurrent(newPlayer.getPrizeCurrent());
        oldPlayer.setPrizeTotal(newPlayer.getPrizeTotal());
        oldPlayer.setTournamentsPlayed(newPlayer.getTournamentsPlayed());
        oldPlayer.setWeight(newPlayer.getWeight());
        return oldPlayer;
    }

}
