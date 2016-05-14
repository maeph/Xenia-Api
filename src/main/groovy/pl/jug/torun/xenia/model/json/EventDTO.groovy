package pl.jug.torun.xenia.model.json

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * Created by zbyszko on 02.04.16.
 */
@ToString
@EqualsAndHashCode
class EventDTO {
    String meetupId
    List<GiveAwayDTO> giveaways
}
