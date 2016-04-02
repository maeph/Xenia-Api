package pl.jug.torun.xenia.rest

import pl.jug.torun.xenia.dao.EventRepository
import pl.jug.torun.xenia.dao.PrizeRepository
import pl.jug.torun.xenia.model.Event
import pl.jug.torun.xenia.model.Prize
import spock.lang.Specification

/**
 * Created by Lukasz on 2016-04-02.
 */
class ExportEventServiceTest extends Specification {

    EventRepository eventRepository = Mock(EventRepository)
    PrizeRepository prizeRepository = Mock(PrizeRepository)

    def "should return events"() {

        given:
            def exportEventService = new ExportEventService(eventRepository, prizeRepository)
            eventRepository.findAll() >> [
                    new Event(meetupId: 1234, title: "JUG Meeting"),
                    new Event(meetupId: 2343, title: "JUG Get Source")
            ]
            prizeRepository.findAll() >> [
                    new Prize(name: "Sex for Dummies", producer: "Bookmann", id: 123, sponsorName: "JUG Torun", imageUrl: "http://sex4dummies.com/img")
            ]

        when:
            def expected = exportEventService.exportAllEvents()

        then:
            expected
            expected.prizes.size() == 1

            def bookPrize = expected.prizes[0]
            bookPrize.id == "123"
            bookPrize.name == "Sex for Dummies"
            bookPrize.producer == "Bookmann"
            bookPrize.sponsorName == "JUG Torun"
            bookPrize.imageUrl == "http://sex4dummies.com/img"
    }
}
