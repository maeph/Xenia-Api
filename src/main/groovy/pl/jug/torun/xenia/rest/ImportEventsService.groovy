package pl.jug.torun.xenia.rest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.jug.torun.xenia.dao.PrizeRepository
import pl.jug.torun.xenia.model.Prize
import pl.jug.torun.xenia.model.json.EventsDTO
/**
 * Created by piotr on 02.04.16.
 */
@Service
class ImportEventsService {

    @Autowired
    PrizeRepository prizeRepository

    void importEvents(EventsDTO eventsDTO) {
        prizeRepository.save(eventsDTO.prizes.collect {
            new Prize(id: it.id, name: it.name, producer: it.producer, sponsorName: it.sponsorName, imageUrl: it.imageUrl)
        })
    }

}
