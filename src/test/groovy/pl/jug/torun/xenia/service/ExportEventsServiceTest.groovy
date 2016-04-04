package pl.jug.torun.xenia.service

import pl.jug.torun.xenia.dao.EventRepository
import pl.jug.torun.xenia.dao.PrizeRepository
import pl.jug.torun.xenia.model.Event
import pl.jug.torun.xenia.model.Prize
import pl.jug.torun.xenia.model.json.EventDTO
import pl.jug.torun.xenia.model.json.factory.EventDTOFactory
import spock.lang.Specification

/**
 * Created by Lukasz on 2016-04-02.
 */
class ExportEventsServiceTest extends Specification {

    EventRepository eventRepository = Mock(EventRepository)
    PrizeRepository prizeRepository = Mock(PrizeRepository)
    EventDTOFactory eventDTOFactory = Mock(EventDTOFactory)

    ExportEventsService exportEventService = new ExportEventsService(eventRepository, prizeRepository, eventDTOFactory)

    def "should contain all events in the export result"() {
        given:
            eventRepository.findAll() >> [new Event(meetupId: 12345), new Event(meetupId: 67890)]
            eventDTOFactory.factorize(_) >> { Event arg -> new EventDTO(meetupId: arg.meetupId) }
        when:
            def expected = exportEventService.exportAllEvents()
        then:
            expected.events.size() == 2
            expected.events[0].meetupId == "12345"
            expected.events[1].meetupId == "67890"
    }

    def "should contain all prizes in the export result"() {
        given:
            prizeRepository.findAll() >> [
                    new Prize(name: "Sex for Dummies", producer: "Bookmann", id: 123, sponsorName: "JUG Torun", imageUrl: "http://sex4dummies.com/img"),
                    new Prize(name: "VI Enterprise", producer: "VI Producer", id: 666, sponsorName: "VI Sponsor", imageUrl: "http://vient.com/logo.png")
            ]
        when:
            def expected = exportEventService.exportAllEvents()
        then:
            expected.prizes.size() == 2

            def bookPrize = expected.prizes[num]
            bookPrize.id == id
            bookPrize.name == name
            bookPrize.producer == producer
            bookPrize.sponsorName == sponsorName
            bookPrize.imageUrl == imageUrl
        where:
            num | id    | name              | producer      | sponsorName  | imageUrl
            0   | "123" | "Sex for Dummies" | "Bookmann"    | "JUG Torun"  | "http://sex4dummies.com/img"
            1   | "666" | "VI Enterprise"   | "VI Producer" | "VI Sponsor" | "http://vient.com/logo.png"
    }
}
