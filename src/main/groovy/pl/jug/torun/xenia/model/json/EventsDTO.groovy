package pl.jug.torun.xenia.model.json

class EventsDTO {
    List<PrizeDTO> prizes
    List<EventDTO> events

    EventsDTO(List<PrizeDTO> prizes) {
        this.prizes = prizes
    }
}
