package pl.jug.torun.xenia.rest
import com.fasterxml.jackson.databind.ObjectMapper
import jdk.nashorn.internal.ir.annotations.Ignore
import org.joda.time.LocalDateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import pl.jug.torun.xenia.Application
import pl.jug.torun.xenia.dao.DrawRepository
import pl.jug.torun.xenia.dao.EventRepository
import pl.jug.torun.xenia.dao.GiveAwayRepository
import pl.jug.torun.xenia.dao.MeetupMemberRepository
import pl.jug.torun.xenia.dao.PrizeRepository
import pl.jug.torun.xenia.model.Event
import pl.jug.torun.xenia.model.Member
import pl.jug.torun.xenia.model.json.EventsDTO
import pl.jug.torun.xenia.model.meetup.MeetupMember
import pl.jug.torun.xenia.service.EventsService
import spock.lang.Specification

@ContextConfiguration(loader = SpringApplicationContextLoader, classes = Application)
@IntegrationTest
class ImportEventsControllerSpec extends Specification {


    @Autowired
    MeetupMemberRepository meetupMemberRepository

    @Autowired
    DrawRepository drawRepository

    @Autowired
    PrizeRepository prizeRepository

    @Autowired
    EventRepository eventRepository

    @Autowired
    GiveAwayRepository giveAwayRepository

    @Autowired
    ImportEventsController importEventsController

    @Autowired
    EventsService eventsService

    static final Event EVENT1 = new Event(title: "Hackaton #1", startDate: LocalDateTime.now(),
            endDate: LocalDateTime.now().plusDays(1), updatedAt: LocalDateTime.now(), meetupId: 11111L)
    static final Event EVENT2 = new Event(title: "Hackaton #2", startDate: LocalDateTime.now(),
            endDate: LocalDateTime.now().plusDays(1), updatedAt: LocalDateTime.now(), meetupId: 22222L)
    static final Event EVENT3 = new Event(title: "Hackaton #2", startDate: LocalDateTime.now(),
            endDate: LocalDateTime.now().plusDays(1), updatedAt: LocalDateTime.now(), meetupId: 33333L)

    static final MEETUP_MEMBER1 =
            new MeetupMember(
                    id: 1L,
                    member: new Member(
                            displayName: 'member.1',
                            photoUrl: 'http://example.com/image.jpg'
                    )
            )

    static final MEETUP_MEMBER2 =
            new MeetupMember(
                    id: 2L,
                    member: new Member(
                            displayName: 'member.2',
                            photoUrl: 'http://example.com/image.jpg'
                    )
            )



    def setup() {
        eventsService = Stub(EventsService)
        eventRepository.save([EVENT1, EVENT2, EVENT3])
        meetupMemberRepository.save([MEETUP_MEMBER1, MEETUP_MEMBER2])
        importEventsController.importService.eventsService = eventsService //Wiremock?

        giveAwayRepository.deleteAll()

    }

    def "should import an empty list" () {
        given:
            def events = convertJSONtoDTO('{"prizes":[], "events":[]}')

        expect:
            importEventsController.importEvents(events)
    }

    def "should import prizes"() {
        given:
            def events = convertJSONtoDTO('''{
                "prizes":[
                    {"uuid": "666-777-888", "name": "Prize1", "producer": "JUG", "sponsorName": "JUG", "imageUrl": "http://example.com/img1.png"},
                    {"uuid": "888-777-666", "name": "Prize2", "producer": "JUG", "sponsorName": "JUG", "imageUrl": "http://example.com/img2.png"}
                ],
                "events":[]
                }'''
            )
        when:
            importEventsController.importEvents(events)
        then:
            prizeRepository.findByUuid('666-777-888').every {
                it.name == 'Prize1'
                it.producer == 'JUG'
                it.sponsorName == 'JUG'
                it.imageUrl == 'http://example.com/img1.png'
            }
            prizeRepository.findByUuid('888-777-666').every {
                it.name == 'Prize2'
                it.producer == 'JUG'
                it.sponsorName == 'JUG'
                it.imageUrl == 'http://example.com/img2.png'
            }
    }


    def "should import events"() {
        given:
            def eventsDTO = convertJSONtoDTO("""{
                "prizes": [],
                "events": [
                    {"meetupId": 11111, "giveaways": []},
                    {"meetupId": 22222, "giveaways": []},
                    {"meetupId": 33333, "giveaways": []}
                ]
            }""")
        when:
            importEventsController.importEvents(eventsDTO)

        then:
            eventRepository.findAll().size() == 3
    }

    @Ignore //JPA Session not initialized
    def "should import events and prizes with give-aways"() {
        given:
            def eventsAndPrizes = convertJSONtoDTO('''{
                "prizes":[
                    {"uuid": "666-777-888", "name": "Prize1", "producer": "JUG", "sponsorName": "JUG", "imageUrl": "http://example.com/img1.png"},
                    {"uuid": "888-777-666", "name": "Prize2", "producer": "JUG", "sponsorName": "JUG", "imageUrl": "http://example.com/img2.png"}
                ],
                "events":[
                    {"meetupId": 11111, "giveaways": [{"prizeUuid": "666-777-888", "amount": 1 ,
                        "draws": [
                        {"meetupMemberId": 1, "drawDate":"2016-06-20T19:55:53.251", "confirmed":true}
                        ]}]},
                    {"meetupId": 22222, "giveaways": []},
                    {"meetupId": 33333, "giveaways": []}
                ]
                }'''
            )
        when:
            importEventsController.importEvents(eventsAndPrizes)

        def giveAway = giveAwayRepository.findByPrize(prizeRepository.findByUuid('666-777-888')).first()
        then:
            giveAway.id == 1L
            giveAway.draws.size() == 1
            giveAway.draws.first().drawDate == LocalDateTime.parse("2016-06-20T19:55:53.251")
            giveAway.draws.first().confirmed


        def one = meetupMemberRepository.getOne(1L)
        def id = one.member.id
        drawRepository.getOne(giveAway.draws.first().id).attendee.id == id
    }


    private static EventsDTO convertJSONtoDTO(String json) {
        ObjectMapper mapper = new ObjectMapper()
        return mapper.readValue(json, EventsDTO.class)
    }
}
