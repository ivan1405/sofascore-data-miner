package com.sports.data.mapper;

import com.sports.data.crud.entity.Player;
import com.sports.data.model.sofascore.event.Event;
import com.sports.data.model.sofascore.team.Team;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class EventMapper extends SportMapper {

    @Override
    protected void configure(MapperFactory mapperFactory) {
        super.configure(mapperFactory);

        mapperFactory.classMap(Event.class, com.sports.data.crud.entity.Event.class)
                .field("slug", "matchTitle")
                .field("homeTeam", "homePlayer")
                .field("awayTeam", "awayPlayer")
                .field("homeScore.current", "homeSetsScore")
                .field("awayScore.current", "awaySetsScore")
                .field("homeScore.current", "homeCurrent")
                .field("homeScore.period1", "homePeriod1")
                .field("homeScore.period2", "homePeriod2")
                .field("homeScore.period3", "homePeriod3")
                .field("homeScore.period4", "homePeriod4")
                .field("homeScore.period5", "homePeriod5")
                .field("homeScore.period6", "homePeriod6")
                .field("homeScore.period1TieBreak", "homePeriod1TieBreak")
                .field("homeScore.period2TieBreak", "homePeriod2TieBreak")
                .field("homeScore.period3TieBreak", "homePeriod3TieBreak")
                .field("homeScore.period4TieBreak", "homePeriod4TieBreak")
                .field("homeScore.period5TieBreak", "homePeriod5TieBreak")
                .field("homeScore.period6TieBreak", "homePeriod6TieBreak")
                .field("awayScore.current", "awayCurrent")
                .field("awayScore.period1", "awayPeriod1")
                .field("awayScore.period2", "awayPeriod2")
                .field("awayScore.period3", "awayPeriod3")
                .field("awayScore.period4", "awayPeriod4")
                .field("awayScore.period5", "awayPeriod5")
                .field("awayScore.period6", "awayPeriod6")
                .field("awayScore.period1TieBreak", "awayPeriod1TieBreak")
                .field("awayScore.period2TieBreak", "awayPeriod2TieBreak")
                .field("awayScore.period3TieBreak", "awayPeriod3TieBreak")
                .field("awayScore.period4TieBreak", "awayPeriod4TieBreak")
                .field("awayScore.period5TieBreak", "awayPeriod5TieBreak")
                .field("awayScore.period6TieBreak", "awayPeriod6TieBreak")
                .field("status", "")
                .customize(new CustomMapper<>() {
                    @Override
                    public void mapAtoB(Event event, com.sports.data.crud.entity.Event eventEntity, MappingContext context) {
                        super.mapAtoB(event, eventEntity, context);
                        // set the winner
                        eventEntity.setWinner(event.getHomeScore().getCurrent() > event.getAwayScore().getCurrent() ?
                                event.getHomeTeam().getSlug() : event.getAwayTeam().getSlug());
                        // set the number of sets
                        eventEntity.setTotalSets(event.getHomeScore().getCurrent() + event.getAwayScore().getCurrent());
                    }
                })
                .byDefault()
                .register();

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
    }

}
