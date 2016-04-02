package pl.jug.torun.xenia.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.jug.torun.xenia.dao.PrizeRepository
import pl.jug.torun.xenia.model.json.EventDTO
import pl.jug.torun.xenia.model.json.EventsDTO
import pl.jug.torun.xenia.model.json.PrizeDTO

/**
 * Created by piotr on 02.04.16.
 */
@Service
class ImportEventsService {

    @Autowired
    PrizeRepository prizeRepository

    void importEvents(EventsDTO eventsDTO) {

    }


    void updatePrizes(List<PrizeDTO> prizes) {

    }


    void updateEvents(List<EventDTO> events) {

    }


}
