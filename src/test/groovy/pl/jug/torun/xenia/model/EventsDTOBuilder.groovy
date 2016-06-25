package pl.jug.torun.xenia.model

import pl.jug.torun.xenia.model.json.EventDTO
import pl.jug.torun.xenia.model.json.EventsDTO
import pl.jug.torun.xenia.model.json.PrizeDTO

/**
 * Created by Wojciech Oczkowski on 2016-06-25.
 */
class EventsDTOBuilder {

    List<PrizeDTO> prizes = []
    List<EventDTO> events = []

    def prizes(List<PrizeDTO> prizes) {
        this.prizes.addAll(prizes)
        return this
    }

    def events(List<EventDTO> events) {
        this.events.addAll(events)
        return this
    }

    def build() {
        return new EventsDTO(prizes: prizes, events: events)
    }
}
