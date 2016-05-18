package pl.jug.torun.xenia.model.json.factory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pl.jug.torun.xenia.dao.PrizeRepository
import pl.jug.torun.xenia.model.Event
import pl.jug.torun.xenia.model.json.EventsDTO
import pl.jug.torun.xenia.model.json.PrizeDTO

/**
 * Created by lsaw on 4/12/16.
 */
@Component
class EventsDTOFactory {

    final EventDTOFactory eventDTOFactory

    final PrizeRepository prizeRepository

    @Autowired
    EventsDTOFactory(EventDTOFactory eventDTOFactory, PrizeRepository prizeRepository) {
        this.eventDTOFactory = eventDTOFactory
        this.prizeRepository = prizeRepository
    }

    EventsDTO factorize(List<Event> events) {
        def prizeList = prizeRepository.findAll()
        def prizeDTOs = prizeList.collect {
            new PrizeDTO(
                    uuid: it.uuid,
                    name: it.name,
                    producer: it.producer,
                    imageUrl: it.imageUrl,
                    sponsorName: it.sponsorName
            )
        }

        def eventDTOs = events.collect {
            eventDTOFactory.factorize(it)
        }

        new EventsDTO(prizes: prizeDTOs, events: eventDTOs)
    }
}
