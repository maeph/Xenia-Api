package pl.jug.torun.xenia.rest

import org.mockito.Mock
import pl.jug.torun.xenia.dao.PrizeRepository
import pl.jug.torun.xenia.model.Prize
import pl.jug.torun.xenia.model.json.EventsDTO
import pl.jug.torun.xenia.model.json.PrizeDTO
import spock.lang.Specification

import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when
import static org.mockito.MockitoAnnotations.initMocks


/**
 * Created by piotr on 02.04.16.
 */
class ImportEventsServiceTest extends Specification {

    @Mock
    PrizeRepository prizeRepository

    def "should write prizes to empty database"() {
        given:

        initMocks(this)
        when(prizeRepository.findAll()).thenReturn([])

        def importService = new ImportEventsService(prizeRepository: prizeRepository)

        def examplePrizes = [new PrizeDTO(id: "01", name: "Prize1", producer: "Producer1", sponsorName: "Sponsor1", imageUrl: "http://example.com/image1.png"),
                             new PrizeDTO(id: "02", name: "Prize2", producer: "Producer2", sponsorName: "Sponsor2", imageUrl: "http://example.com/image2.png")]
        when:

        importService.importEvents(new EventsDTO(prizes: examplePrizes))

        then:

        1 * prizeRepository.save(*_) >> {arguments ->
            def prizes = arguments[0] as Set
            def expectedPrizes = [new Prize(id: "01", name: "Prize1", producer: "Producer1", sponsorName: "Sponsor1", imageUrl: "http://example.com/image1.png"),
                                  new Prize(id: "02", name: "Prize2", producer: "Producer2", sponsorName: "Sponsor2", imageUrl: "http://example.com/image2.png")] as Set

            assert expectedPrizes == prizes
        }


    }
}
