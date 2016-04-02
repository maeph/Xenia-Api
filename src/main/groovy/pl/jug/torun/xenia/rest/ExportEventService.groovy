package pl.jug.torun.xenia.rest

import org.springframework.beans.factory.annotation.Autowired
import pl.jug.torun.xenia.dao.EventRepository
import pl.jug.torun.xenia.dao.PrizeRepository
import pl.jug.torun.xenia.model.Event
import pl.jug.torun.xenia.model.Prize
import pl.jug.torun.xenia.model.json.EventsDTO
import pl.jug.torun.xenia.model.json.PrizeDTO

/**
 * Created by Lukasz on 2016-04-02.
 */
class ExportEventService {


    final EventRepository eventRepository
    final PrizeRepository prizeRepository

    @Autowired
    ExportEventService(EventRepository eventRepository, PrizeRepository prizeRepository) {
        this.eventRepository = eventRepository
        this.prizeRepository = prizeRepository
    }

    EventsDTO exportAllEvents() {

        List<Event> eventsList = eventRepository.findAll()

        convertToDTO(eventsList)
    }

    private EventsDTO convertToDTO(List<Event> events) {

        List<Prize> prizeList = prizeRepository.findAll()

        def prizeDTO = prizeList.collect{
            new PrizeDTO(
                    id: it.id,
                    name: it.name,
                    producer: it.producer,
                    imageUrl: it.imageUrl,
                    sponsorName: it.sponsorName
            )
        }
        EventsDTO eventsDTO = new EventsDTO(prizeDTO)


        eventsDTO
    }

}
