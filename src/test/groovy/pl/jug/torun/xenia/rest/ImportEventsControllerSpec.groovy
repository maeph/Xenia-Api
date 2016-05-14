package pl.jug.torun.xenia.rest

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import pl.jug.torun.xenia.Application
import pl.jug.torun.xenia.dao.PrizeRepository
import pl.jug.torun.xenia.model.json.EventsDTO
import spock.lang.Specification

/**
 * Created by piotr on 14.05.16.
 */
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = Application)
@IntegrationTest
class ImportEventsControllerSpec extends Specification {

    @Autowired
    ImportEventsController importEventsController

    @Autowired
    PrizeRepository prizeRepository

    def "should import an empty list" () {
        given:
            def events = convertJSONtoDTO('{"prizes":[], "events":[]}')

        expect:
            importEventsController.importEvents(events)
    }

    def "should import prizes"() {
        given:
            def events = convertJSONtoDTO('''{
                "prizes":[
                    {"id": "666-777-888", "name": "Prize1", "producer": "JUG", "sponsorName": "JUG", "imageUrl": "http://example.com/img1.png"},
                    {"id": "888-777-666", "name": "Prize2", "producer": "JUG", "sponsorName": "JUG", "imageUrl": "http://example.com/img2.png"}
                ],
                "events":[]}'''
            )
        when:
            importEventsController.importEvents(events)
        then:
            prizeRepository.findOne('666-777-888').every {
                it.name == 'Prize1'
                it.producer == 'JUG'
                it.sponsorName == 'JUG'
                it.imageUrl == 'http://example.com/img1.png'
            }
            prizeRepository.findOne('888-777-666').every {
                it.name == 'Prize2'
                it.producer == 'JUG'
                it.sponsorName == 'JUG'
                it.imageUrl == 'http://example.com/img2.png'
            }
    }

    private EventsDTO convertJSONtoDTO(String json) {
        ObjectMapper mapper = new ObjectMapper()
        return mapper.readValue(json, EventsDTO.class)

    }

}
