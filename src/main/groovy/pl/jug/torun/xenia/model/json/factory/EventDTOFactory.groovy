package pl.jug.torun.xenia.model.json.factory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pl.jug.torun.xenia.model.Event
import pl.jug.torun.xenia.model.json.EventDTO

/**
 * Created by lsaw on 4/4/16.
 */
@Component
class EventDTOFactory {

    final GiveAwayDTOFactory giveAwayDTOFactory

    @Autowired
    EventDTOFactory(GiveAwayDTOFactory giveAwayDTOFactory) {
        this.giveAwayDTOFactory = giveAwayDTOFactory
    }

    EventDTO factorize(Event event) {
        def giveAwayDTOs = event.giveAways.collect {
            giveAwayDTOFactory.factorize(it)
        }
        new EventDTO(
                meetupId: event.meetupId,
                giveaways: giveAwayDTOs)
    }
}
