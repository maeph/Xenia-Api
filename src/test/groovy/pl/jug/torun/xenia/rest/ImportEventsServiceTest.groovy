package pl.jug.torun.xenia.rest

import pl.jug.torun.xenia.dao.PrizeRepository
import pl.jug.torun.xenia.model.Prize
import pl.jug.torun.xenia.model.json.EventsDTO
import pl.jug.torun.xenia.model.json.PrizeDTO
import spock.lang.Specification

/**
 * Created by piotr on 02.04.16.
 */
class ImportEventsServiceTest extends Specification {

    PrizeRepository prizeRepository = Mock(PrizeRepository)
    ImportEventsService importService = new ImportEventsService(prizeRepository: prizeRepository)

    def "should write prizes to empty database"() {
        given:
            def examplePrizes = [new PrizeDTO(id: "01", name: "Prize1", producer: "Producer1", sponsorName: "Sponsor1", imageUrl: "http://example.com/image1.png"),
                                 new PrizeDTO(id: "02", name: "Prize2", producer: "Producer2", sponsorName: "Sponsor2", imageUrl: "http://example.com/image2.png")]
        when:
            importService.importEvents(new EventsDTO(prizes: examplePrizes))

        then:
            1 * prizeRepository.save(_) >> { arguments ->
                def prizes = arguments[0] as List
                assert prizes[0].properties == new Prize(id: "01", name: "Prize1", producer: "Producer1", sponsorName: "Sponsor1", imageUrl: "http://example.com/image1.png").properties
                assert prizes[1].properties == new Prize(id: "02", name: "Prize2", producer: "Producer2", sponsorName: "Sponsor2", imageUrl: "http://example.com/image2.png").properties
            }
    }

}
