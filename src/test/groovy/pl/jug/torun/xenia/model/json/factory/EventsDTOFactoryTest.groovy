package pl.jug.torun.xenia.model.json.factory

import pl.jug.torun.xenia.dao.PrizeRepository
import pl.jug.torun.xenia.model.Event
import pl.jug.torun.xenia.model.Prize
import pl.jug.torun.xenia.model.json.EventDTO
import spock.lang.Specification

/**
 * Created by lsaw on 4/12/16.
 */
class EventsDTOFactoryTest extends Specification {

    PrizeRepository prizeRepository = Mock(PrizeRepository)

    EventDTOFactory eventDTOFactory = Mock(EventDTOFactory)

    EventsDTOFactory eventsDTOFactory = new EventsDTOFactory(eventDTOFactory, prizeRepository)

    def 'should contain all events in the export result'() {
        given:
            def eventsList = [new Event(meetupId: 12345), new Event(meetupId: 67890)]
            eventDTOFactory.factorize(_) >> { Event arg -> new EventDTO(meetupId: arg.meetupId) }
        when:
            def expected = eventsDTOFactory.factorize(eventsList)
        then:
            expected.events.size() == 2
            expected.events[0].meetupId == '12345'
            expected.events[1].meetupId == '67890'
    }

    def 'should contain all prizes in the export result'() {
        given:
            prizeRepository.findAll() >> [
                    new Prize(name: 'Sex for Dummies', producer: 'Bookmann', uuid: '123', sponsorName: 'JUG Torun', imageUrl: 'http://sex4dummies.com/img'),
                    new Prize(name: 'VI Enterprise', producer: 'VI Producer', uuid: '666', sponsorName: 'VI Sponsor', imageUrl: 'http://vient.com/logo.png')
            ]
        when:
            def expected = eventsDTOFactory.factorize([])
        then:
            expected.prizes.size() == 2

            def bookPrize = expected.prizes[num]
            bookPrize.uuid == uuid
            bookPrize.name == name
            bookPrize.producer == producer
            bookPrize.sponsorName == sponsorName
            bookPrize.imageUrl == imageUrl
        where:
            num | uuid  | name              | producer      | sponsorName  | imageUrl
            0   | '123' | 'Sex for Dummies' | 'Bookmann'    | 'JUG Torun'  | 'http://sex4dummies.com/img'
            1   | '666' | 'VI Enterprise'   | 'VI Producer' | 'VI Sponsor' | 'http://vient.com/logo.png'
    }
}
