package pl.jug.torun.xenia.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pl.jug.torun.xenia.dao.EventRepository
import pl.jug.torun.xenia.model.json.EventsDTO
import pl.jug.torun.xenia.model.json.factory.EventsDTOFactory

/**
 * Created by Lukasz on 2016-04-02.
 */
@Component
class ExportEventsService {

    final EventsDTOFactory eventsDTOFactory

    final EventRepository eventRepository

    @Autowired
    ExportEventsService(EventRepository eventRepository, EventsDTOFactory eventsDTOFactory) {
        this.eventRepository = eventRepository
        this.eventsDTOFactory = eventsDTOFactory
    }

    EventsDTO exportAllEvents() {
        def eventsList = eventRepository.findAll()
        eventsDTOFactory.factorize(eventsList)
    }

}
