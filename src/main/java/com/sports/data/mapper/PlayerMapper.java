package com.sports.data.mapper;

import com.sports.data.crud.entity.Player;
import com.sports.data.model.sofascore.team.PlayerTeamInfoWrapper;
import com.sports.data.model.sofascore.team.Team;
import com.sports.data.model.sofascore.team.TeamFullInfo;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper extends SportMapper {

    @Override
    protected void configure(MapperFactory mapperFactory) {
        super.configure(mapperFactory);

        mapperFactory.classMap(TeamFullInfo.class, Player.class)
                .field("ranking", "")
                .field("team", "")
                .register();

        mapperFactory.classMap(PlayerTeamInfoWrapper.class, Player.class)
                .field("teamInfo", "")
                //.field("rankingInfo", "")
                .register();

        mapperFactory.classMap(Team.class, Player.class)
                .field("id", "playerId")
                .field("sport.name", "sport")
                .field("playerTeamInfo.residence", "residence")
                .field("playerTeamInfo.birthplace", "birthplace")
                .field("playerTeamInfo.height", "height")
                .field("playerTeamInfo.weight", "weight")
                .field("playerTeamInfo.plays", "plays")
                .field("playerTeamInfo.turnedPro", "turnedPro")
                .field("playerTeamInfo.prizeCurrent", "prizeCurrent")
                .field("playerTeamInfo.prizeTotal", "prizeTotal")
                .field("playerTeamInfo.birthDateTimestamp", "birthDateTimestamp")
                .field("country.name", "country")
                .byDefault()
                .register();

//        mapperFactory.classMap(TeamFullInfo.class, Player.class)
//                .exclude("id")
//                .byDefault()
//                .register();

        mapperFactory.classMap(Player.class, com.sports.data.model.Player.class)
                .byDefault()
                .register();
    }

}