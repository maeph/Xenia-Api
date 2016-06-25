package pl.jug.torun.xenia.model

import pl.jug.torun.xenia.model.json.EventDTO
import pl.jug.torun.xenia.model.json.GiveAwayDTO

/**
 * Created by Wojciech Oczkowski on 2016-06-25.
 */
class EventDTOBuilder {
    Long meetupId
    List<GiveAwayDTO> giveaways = []

    def meetupId(Long meetupId){
        this.meetupId = meetupId
        this
    }

    def giveaways(List<GiveAwayDTO> giveaways){
        this.giveaways.addAll(giveaways)
        this
    }

    def build(){
        if(meetupId == null){
            throw new NullPointerException("meetupId must be set")
        }
        new EventDTO(meetupId: meetupId, giveaways: giveaways);
    }
}
