package pl.jug.torun.xenia.rest

import org.junit.Test
import org.mockito.Mock
import pl.jug.torun.xenia.dao.EventRepository
import pl.jug.torun.xenia.dao.PrizeRepository
import pl.jug.torun.xenia.model.Event
import pl.jug.torun.xenia.model.Prize
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

/**
 * Created by Lukasz on 2016-04-02.
 */
class ExportEventServiceTest extends Specification{

    EventRepository eventRepository = mock(EventRepository)
    PrizeRepository prizeRepository = mock(PrizeRepository)


    def "should return events"() {

        given:
        def exportEventService = new ExportEventService(eventRepository, prizeRepository)
        when(eventRepository.findAll()).thenReturn([
                new Event(meetupId: 1234, title: "JUG Meeting"),
                new Event(meetupId: 2343, title: "JUG Get Source")
        ])
        when(prizeRepository.findAll()).thenReturn([
                new Prize(name: "Ja", producer: "Cos", id: 123, sponsorName: "Sponsor", imageUrl: "url")
        ])


        when:
        def expected = exportEventService.exportAllEvents()

        then:
            expected
        expected.prizes.size()==1
    }
}
