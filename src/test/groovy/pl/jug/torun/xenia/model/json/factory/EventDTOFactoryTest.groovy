package pl.jug.torun.xenia.model.json.factory

import pl.jug.torun.xenia.model.Event
import pl.jug.torun.xenia.model.GiveAway
import pl.jug.torun.xenia.model.Prize
import pl.jug.torun.xenia.model.json.GiveAwayDTO
import spock.lang.Specification

/**
 * Created by lsaw on 4/4/16.
 */
class EventDTOFactoryTest extends Specification {

    GiveAwayDTOFactory giveAwayDTOFactory = Mock(GiveAwayDTOFactory)

    EventDTOFactory eventDTOFactory = new EventDTOFactory(giveAwayDTOFactory)

    def "should create event DTO"() {
        given:
            def event = new Event(
                    meetupId: 1234,
                    giveAways: [
                            new GiveAway(prize: new Prize(id: "12345")),
                            new GiveAway(prize: new Prize(id: "67890"))
                    ])
            giveAwayDTOFactory.factorize(_) >> { GiveAway arg -> new GiveAwayDTO(prizeId: arg.prize.id) }
        when:
            def expected = eventDTOFactory.factorize(event)
        then:
            expected.meetupId == "1234"
            expected.giveaways[0].prizeId == "12345"
            expected.giveaways[1].prizeId == "67890"
    }
}
