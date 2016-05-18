package pl.jug.torun.xenia.model.json

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
class PrizeDTO {
    String uuid
    String name
    String producer
    String sponsorName
    String imageUrl
}
