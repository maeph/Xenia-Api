package pl.jug.torun.xenia.service

import org.springframework.beans.factory.annotation.Autowired
import pl.jug.torun.xenia.dao.EventRepository
import pl.jug.torun.xenia.dao.PrizeRepository
import pl.jug.torun.xenia.model.Event
import pl.jug.torun.xenia.model.json.EventsDTO
import pl.jug.torun.xenia.model.json.PrizeDTO
import pl.jug.torun.xenia.model.json.factory.EventDTOFactory

/**
 * Created by Lukasz on 2016-04-02.
 */
class ExportEventsService {

    final EventDTOFactory eventDTOFactory

    final EventRepository eventRepository

    final PrizeRepository prizeRepository

    @Autowired
    ExportEventsService(EventRepository eventRepository, PrizeRepository prizeRepository, EventDTOFactory eventDTOFactory) {
        this.eventRepository = eventRepository
        this.prizeRepository = prizeRepository
        this.eventDTOFactory = eventDTOFactory
    }

    EventsDTO exportAllEvents() {
        def eventsList = eventRepository.findAll()
        convertToDTO(eventsList)
    }

    private EventsDTO convertToDTO(List<Event> events) {
        def prizeList = prizeRepository.findAll()
        def prizeDTOs = prizeList.collect {
            new PrizeDTO(
                    id: it.id,
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
