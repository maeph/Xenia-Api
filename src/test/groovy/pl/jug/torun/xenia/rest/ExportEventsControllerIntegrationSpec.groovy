package pl.jug.torun.xenia.rest

import org.joda.time.LocalDateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import pl.jug.torun.xenia.IntegrationSpecification
import pl.jug.torun.xenia.dao.EventRepository
import pl.jug.torun.xenia.dao.MeetupMemberRepository
import pl.jug.torun.xenia.dao.PrizeRepository
import pl.jug.torun.xenia.model.Draw
import pl.jug.torun.xenia.model.Event
import pl.jug.torun.xenia.model.GiveAway
import pl.jug.torun.xenia.model.Member
import pl.jug.torun.xenia.model.Prize
import pl.jug.torun.xenia.model.json.DrawDTO
import pl.jug.torun.xenia.model.json.EventDTO
import pl.jug.torun.xenia.model.json.GiveAwayDTO
import pl.jug.torun.xenia.model.json.PrizeDTO
import pl.jug.torun.xenia.model.meetup.MeetupMember

class ExportEventsControllerIntegrationSpec extends IntegrationSpecification {

    @Autowired
    MeetupMemberRepository meetupMemberRepository

    @Autowired
    EventRepository eventRepository

    @Autowired
    PrizeRepository prizeRepository

    @Autowired
    ExportEventsController exportEventsController

    @Transactional
    def 'should export a list of events'() {
        given:
            def now = LocalDateTime.now()
            def yesterday = LocalDateTime.now().minusDays(1)
            def janKowalski = meetupMemberRepository.save(
                    new MeetupMember(
                            id: 1L,
                            member: new Member(
                                    displayName: 'jan.kowalski',
                                    photoUrl: 'http://peekasa.com/image101'
                            )
                    )
            )
            def janNowak = meetupMemberRepository.save(
                    new MeetupMember(
                            id: 2L,
                            member: new Member(
                                    displayName: 'jan.nowak',
                                    photoUrl: 'http://peekasa.com/image102'
                            )
                    )
            )
            def janPolak = meetupMemberRepository.save(
                    new MeetupMember(
                            id: 3L,
                            member: new Member(
                                    displayName: 'jan.polak',
                                    photoUrl: 'http://peekasa.com/image103'
                            )
                    )
            )
            def ebook = prizeRepository.save(
                    new Prize(
                            name: 'Spring in Action',
                            producer: 'O-Relly',
                            imageUrl: 'http://peekasa.com/image301',
                            sponsorName: 'corpo1'
                    )
            )
            def ideLicense = prizeRepository.save(
                    new Prize(
                            name: 'The only right IDE',
                            producer: 'producer1',
                            imageUrl: 'http://peekasa.com/image302',
                            sponsorName: 'corpo2'
                    )
            )
            def analysisToolLicense = prizeRepository.save(
                    new Prize(
                            name: 'JAnalisiTool',
                            producer: 'producer2',
                            imageUrl: 'http://peekasa.com/image303',
                            sponsorName: 'corpo3'
                    )
            )
            eventRepository.save(
                    new Event(
                            title: 'First event',
                            meetupId: 201L,
                            updatedAt: yesterday,
                            giveAways: [
                                    new GiveAway(
                                            amount: 2,
                                            prize: ebook,
                                            draws: [
                                                    new Draw(
                                                            drawDate: yesterday,
                                                            confirmed: true,
                                                            attendee: janKowalski.member
                                                    ),
                                                    new Draw(
                                                            drawDate: yesterday,
                                                            confirmed: false,
                                                            attendee: janNowak.member
                                                    )
                                            ]
                                    )
                            ]
                    )
            )
            eventRepository.save(
                    new Event(
                            title: 'First event',
                            meetupId: 202L,
                            updatedAt: now
                    )
            )
            eventRepository.save(
                    new Event(
                            title: 'First event',
                            meetupId: 203L,
                            updatedAt: now,
                            giveAways: [
                                    new GiveAway(
                                            amount: 1,
                                            prize: ideLicense,
                                            draws: [
                                                    new Draw(
                                                            drawDate: now,
                                                            confirmed: true,
                                                            attendee: janPolak.member
                                                    )
                                            ]
                                    ),
                                    new GiveAway(
                                            amount: 1,
                                            prize: analysisToolLicense,
                                            draws: [
                                                    new Draw(
                                                            drawDate: now,
                                                            confirmed: true,
                                                            attendee: janPolak.member
                                                    )
                                            ]
                                    )
                            ]
                    )
            )

        when:
            def eventsDTO = exportEventsController.exportEvents()

        then:
            eventsDTO.events == [
                    new EventDTO(
                            meetupId: 201L,
                            giveaways: [
                                    new GiveAwayDTO(
                                            amount: 2,
                                            prizeId: ebook.id,
                                            draws: [
                                                    new DrawDTO(
                                                            drawDate: yesterday,
                                                            confirmed: true,
                                                            meetupMemberId: janKowalski.id
                                                    ),
                                                    new DrawDTO(
                                                            drawDate: yesterday,
                                                            confirmed: false,
                                                            meetupMemberId: janNowak.id
                                                    )
                                            ]
                                    )
                            ]
                    ),
                    new EventDTO(
                            meetupId: 202L,
                            giveaways: []
                    ),
                    new EventDTO(
                            meetupId: 203L,
                            giveaways: [
                                    new GiveAwayDTO(
                                            amount: 1,
                                            prizeId: ideLicense.id,
                                            draws: [
                                                    new DrawDTO(
                                                            drawDate: now,
                                                            confirmed: true,
                                                            meetupMemberId: janPolak.id
                                                    )
                                            ]
                                    ),
                                    new GiveAwayDTO(
                                            amount: 1,
                                            prizeId: analysisToolLicense.id,
                                            draws: [
                                                    new DrawDTO(
                                                            drawDate: now,
                                                            confirmed: true,
                                                            meetupMemberId: janPolak.id
                                                    )
                                            ]
                                    )
                            ]
                    )
            ]
            eventsDTO.prizes == [
                    new PrizeDTO(
                            id: ebook.id,
                            name: 'Spring in Action',
                            producer: 'O-Relly',
                            imageUrl: 'http://peekasa.com/image301',
                            sponsorName: 'corpo1'
                    ),
                    new PrizeDTO(
                            id: ideLicense.id,
                            name: 'The only right IDE',
                            producer: 'producer1',
                            imageUrl: 'http://peekasa.com/image302',
                            sponsorName: 'corpo2'
                    ),
                    new PrizeDTO(
                            id: analysisToolLicense.id,
                            name: 'JAnalisiTool',
                            producer: 'producer2',
                            imageUrl: 'http://peekasa.com/image303',
                            sponsorName: 'corpo3'
                    )
            ]

            eventsDTO.events.size() == 1
    }
}
