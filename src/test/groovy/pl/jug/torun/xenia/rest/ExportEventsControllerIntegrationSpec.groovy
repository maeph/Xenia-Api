package pl.jug.torun.xenia.rest

import org.joda.time.LocalDateTime
import org.springframework.beans.factory.annotation.Autowired
import pl.jug.torun.xenia.IntegrationSpecification
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
    ExportEventsController exportEventsController

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
            def ebookDraws = [
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
            drawRepository.save(ebookDraws)
            def ebookGiveAways = [
                    new GiveAway(
                            amount: 2,
                            prize: ebook,
                            draws: ebookDraws
                    )
            ]
            giveAwayRepository.save(ebookGiveAways)
            eventRepository.save(
                    new Event(
                            title: 'First event',
                            meetupId: 201L,
                            updatedAt: yesterday,
                            giveAways: ebookGiveAways
                    )
            )
            eventRepository.save(
                    new Event(
                            title: 'First event',
                            meetupId: 202L,
                            updatedAt: now
                    )
            )

            def ideDraw = new Draw(
                    drawDate: now,
                    confirmed: true,
                    attendee: janPolak.member
            )
            def analysisToolDraw = new Draw(
                    drawDate: now,
                    confirmed: true,
                    attendee: janKowalski.member
            )
            drawRepository.save([ideDraw, analysisToolDraw])
            def firstEventGiveAways = [
                    new GiveAway(
                            amount: 1,
                            prize: ideLicense,
                            draws: [ideDraw]
                    ),
                    new GiveAway(
                            amount: 1,
                            prize: analysisToolLicense,
                            draws: [analysisToolDraw]
                    )
            ]
            giveAwayRepository.save(firstEventGiveAways)
            eventRepository.save(
                    new Event(
                            title: 'First event',
                            meetupId: 203L,
                            updatedAt: now,
                            giveAways: firstEventGiveAways
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
                                            prizeUuid: ebook.uuid,
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
                                            prizeUuid: ideLicense.uuid,
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
                                            prizeUuid: analysisToolLicense.uuid,
                                            draws: [
                                                    new DrawDTO(
                                                            drawDate: now,
                                                            confirmed: true,
                                                            meetupMemberId: janKowalski.id
                                                    )
                                            ]
                                    )
                            ]
                    )
            ]
            eventsDTO.prizes == [
                    new PrizeDTO(
                            uuid: ebook.uuid,
                            name: 'Spring in Action',
                            producer: 'O-Relly',
                            imageUrl: 'http://peekasa.com/image301',
                            sponsorName: 'corpo1'
                    ),
                    new PrizeDTO(
                            uuid: ideLicense.uuid,
                            name: 'The only right IDE',
                            producer: 'producer1',
                            imageUrl: 'http://peekasa.com/image302',
                            sponsorName: 'corpo2'
                    ),
                    new PrizeDTO(
                            uuid: analysisToolLicense.uuid,
                            name: 'JAnalisiTool',
                            producer: 'producer2',
                            imageUrl: 'http://peekasa.com/image303',
                            sponsorName: 'corpo3'
                    )
            ]
    }
}
