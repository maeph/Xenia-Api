package pl.jug.torun.xenia.model

import pl.jug.torun.xenia.model.json.PrizeDTO

/**
 * Created by Wojciech Oczkowski on 2016-06-25.
 */
class PrizeDTOBuilder {
    String uuid = ""
    String name = ""
    String producer = ""
    String sponsorName = ""
    String imageUrl = ""

    def uuid(String uuid) {
        this.uuid = uuid
        this
    }

    def name(String name) {
        this.name = name
        this
    }

    def producer(String producer) {
        this.producer = producer
        this
    }

    def sponsorName(String sponsorName) {
        this.sponsorName = sponsorName
        this
    }

    def imageUrl(String imageUrl) {
        this.imageUrl = imageUrl
        this
    }

    def build() {
        new PrizeDTO(name: name, producer: producer, sponsorName: sponsorName, imageUrl: imageUrl, uuid: uuid)
    }
}
