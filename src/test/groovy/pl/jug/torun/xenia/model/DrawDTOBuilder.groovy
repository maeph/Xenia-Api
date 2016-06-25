package pl.jug.torun.xenia.model

import pl.jug.torun.xenia.model.json.DrawDTO

import java.time.LocalDateTime

/**
 * Created by Wojciech Oczkowski on 2016-06-25.
 */
class DrawDTOBuilder {
    Long meetupMemberId
    String drawDate
    boolean confirmed

    def meetupMemberId(Long meetupMemberId){
        this.meetupMemberId = meetupMemberId
        this
    }

    def drawDate(String drawDate){
        this.drawDate = drawDate
        this
    }

    def drawDate(LocalDateTime drawDate){
        this.drawDate = drawDate.toString()
        this
    }

    def confirmed(boolean confirmed){
        this.confirmed = confirmed
        this
    }

    def build(){
        if(meetupMemberId == null){
            throw new NullPointerException("meetupMemberId must be set")
        }
        if(drawDate == null){
            throw new NullPointerException("drawDate must be set")
        }
        new DrawDTO(meetupMemberId: meetupMemberId, drawDate: drawDate, confirmed: confirmed)
    }
}
