package com.sports.data.mapper;

import com.sports.data.crud.entity.Player;
import com.sports.data.model.Ranking;
import com.sports.data.model.Team;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.OrikaSystemProperties;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper extends ConfigurableMapper {

    @Override
    protected void configureFactoryBuilder(DefaultMapperFactory.Builder factoryBuilder) {
        super.configureFactoryBuilder(factoryBuilder);

        System.setProperty(OrikaSystemProperties.WRITE_SOURCE_FILES, "true");
        System.setProperty(OrikaSystemProperties.WRITE_CLASS_FILES, "true");
    }

    @Override
    protected void configure(MapperFactory mapperFactory) {
        super.configure(mapperFactory);

        mapperFactory.classMap(Team.class, Player.class)
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

        mapperFactory.classMap(Ranking.class, Player.class)
                .byDefault()
                .register();
    }

}