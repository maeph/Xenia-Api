package pl.jug.torun.xenia.model.json

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
class EventsDTO {
    List<PrizeDTO> prizes
    List<EventDTO> events

}
