package com.sports.data.crud.repository;

import com.sports.data.crud.entity.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {

    Player findPlayerById(Integer id);

    Player findPlayerByPlayerId(Integer id);
}
