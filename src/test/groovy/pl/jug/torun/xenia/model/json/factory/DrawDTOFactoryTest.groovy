package pl.jug.torun.xenia.model.json.factory

import org.joda.time.LocalDateTime
import pl.jug.torun.xenia.dao.MeetupMemberRepository
import pl.jug.torun.xenia.model.Draw
import pl.jug.torun.xenia.model.Member
import pl.jug.torun.xenia.model.meetup.MeetupMember
import spock.lang.Specification

/**
 * Created by lsaw on 4/14/16.
 */
class DrawDTOFactoryTest extends Specification {

    MeetupMemberRepository meetupMemberRepository = Mock(MeetupMemberRepository)

    def "should create draw DTO"() {
        given:
            def factory = new DrawDTOFactory(meetupMemberRepository)
            def member = new Member()
            def draw = new Draw(attendee: member, confirmed: true, drawDate: LocalDateTime.parse("2016-04-05T13:32:01"))
            meetupMemberRepository.getByMember(member) >> new MeetupMember(id: 1000)
        when:
            def expected = factory.factorize(draw)
        then:
            expected.meetupMemberId == 1000
            expected.drawDate == "2016-04-05T13:32:01.000"
            expected.confirmed
    }
}
