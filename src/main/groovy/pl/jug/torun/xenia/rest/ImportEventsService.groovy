package pl.jug.torun.xenia.rest

import org.joda.time.LocalDateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.jug.torun.xenia.dao.EventRepository
import pl.jug.torun.xenia.dao.GiveAwayRepository
import pl.jug.torun.xenia.dao.MeetupMemberRepository
import pl.jug.torun.xenia.dao.PrizeRepository
import pl.jug.torun.xenia.model.Draw
import pl.jug.torun.xenia.model.GiveAway
import pl.jug.torun.xenia.model.Prize
import pl.jug.torun.xenia.model.json.EventsDTO
import pl.jug.torun.xenia.service.EventsService

@Service
class ImportEventsService {

    @Autowired
    PrizeRepository prizeRepository

    @Autowired
    EventRepository eventRepository

    @Autowired
    GiveAwayRepository giveAwayRepository

    @Autowired
    MeetupMemberRepository meetupMemberRepository

    @Autowired
    EventsService eventsService

    void importEvents(EventsDTO eventsDTO) {

        prizeRepository.save(
                eventsDTO.prizes.findAll {
                    !prizeRepository.findByUuid(it.uuid)
                } .collect {
            new Prize(uuid: it.uuid, name: it.name, producer: it.producer, sponsorName: it.sponsorName, imageUrl: it.imageUrl)
        })

        eventsService.refreshEvents()

        eventsDTO.events?.forEach {

            def event = eventRepository.findByMeetupId(it.meetupId)
            if (event != null) {
                event.giveAways = (it.giveaways.collect {
                    new GiveAway(
                            prize: prizeRepository.findByUuid(it.prizeUuid),
                            amount: it.amount,
                            draws: it.draws.collect {
                                new Draw(
                                        attendee: meetupMemberRepository.findOne(it.meetupMemberId).member,
                                        drawDate: LocalDateTime.parse(it.drawDate),
                                        confirmed: it.confirmed
                                )
                            })
                })
                giveAwayRepository.save(event.giveAways)
                eventRepository.save(event)
            }
        }

    }

}
