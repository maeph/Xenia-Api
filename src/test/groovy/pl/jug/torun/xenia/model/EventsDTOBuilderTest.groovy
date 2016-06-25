package pl.jug.torun.xenia.model

import pl.jug.torun.xenia.model.json.DrawDTO
import pl.jug.torun.xenia.model.json.EventDTO
import pl.jug.torun.xenia.model.json.EventsDTO
import pl.jug.torun.xenia.model.json.GiveAwayDTO
import pl.jug.torun.xenia.model.json.PrizeDTO
import pl.jug.torun.xenia.rest.dto.EventResponse
import spock.lang.Specification

import java.time.LocalDateTime

/**
 * Created by Wojciech Oczkowski on 2016-06-25.
 */
public class EventsDTOBuilderTest extends Specification {
    def "Should build complete EventsDTO"() {
        given:
        def draw = new DrawDTOBuilder().confirmed(true).drawDate(LocalDateTime.now()).meetupMemberId(1L).build()
        def prizeUuid = "1111-1111-1111"
        def giveAway = new GiveAwayDTOBuilder().amount(1).draws(draw).prizeUuid(prizeUuid).buid()
        def event = new EventDTOBuilder().giveaways([giveAway]).meetupId(1L).build()
        def prize = new PrizeDTOBuilder().imageUrl("http://img.pl").name("aaaa").producer("JUG").sponsorName("JUG").uuid(prizeUuid).build()
        def builder = new EventsDTOBuilder().events([event]).prizes([prize])

        when:
        def events = builder.build()
        then:
        //TODO uzupełnić testy
        events.prizes.first().uuid == prizeUuid
        events.events.first().meetupId ==1L
    }
}