package pl.jug.torun.xenia

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import pl.jug.torun.xenia.dao.DrawRepository
import pl.jug.torun.xenia.dao.EventRepository
import pl.jug.torun.xenia.dao.MeetupMemberRepository
import pl.jug.torun.xenia.dao.MemberRepository
import pl.jug.torun.xenia.dao.PrizeRepository
import spock.lang.Specification

import javax.transaction.Transactional

@ContextConfiguration(loader = SpringApplicationContextLoader, classes = Application)
@IntegrationTest
@Transactional
class IntegrationSpecification extends Specification {

    @Autowired
    MemberRepository memberRepository

    @Autowired
    MeetupMemberRepository meetupMemberRepository

    @Autowired
    DrawRepository drawRepository

    @Autowired
    PrizeRepository prizeRepository

    @Autowired
    EventRepository eventRepository

    def setup() {
        eventRepository.deleteAll()
        prizeRepository.deleteAll()
        drawRepository.deleteAll()
        meetupMemberRepository.deleteAll()
        memberRepository.deleteAll()
    }
}
