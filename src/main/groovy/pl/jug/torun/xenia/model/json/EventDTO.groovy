package pl.jug.torun.xenia.model.json

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString


@ToString
@EqualsAndHashCode
class EventDTO {
    Long meetupId
    List<GiveAwayDTO> giveaways
}
