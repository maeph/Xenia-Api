package pl.jug.torun.xenia.model.json.factory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pl.jug.torun.xenia.dao.MeetupMemberRepository
import pl.jug.torun.xenia.model.Draw
import pl.jug.torun.xenia.model.json.DrawDTO

/**
 * Created by lsaw on 4/14/16.
 */
@Component
class DrawDTOFactory {

    MeetupMemberRepository meetupMemberRepository

    @Autowired
    DrawDTOFactory(MeetupMemberRepository meetupMemberRepository) {
        this.meetupMemberRepository = meetupMemberRepository
    }

    DrawDTO factorize(Draw draw) {
        def meetupMember = meetupMemberRepository.getByMember(draw.attendee)
        new DrawDTO(meetupMemberId: meetupMember.id, drawDate: draw.drawDate.toString(), confirmed: draw.confirmed)
    }
}
