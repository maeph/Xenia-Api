package pl.jug.torun.xenia.model.json.factory

import pl.jug.torun.xenia.model.GiveAway
import pl.jug.torun.xenia.model.Prize
import spock.lang.Specification

/**
 * Created by lsaw on 4/4/16.
 */
class GiveAwayDTOFactoryTest extends Specification {

    GiveAwayDTOFactory giveAwayDTOFactory = new GiveAwayDTOFactory()

    def "should create giveaway DTO"() {
        given:
            def giveAway = new GiveAway(prize: new Prize(id: "12345"), amount: 3)
        when:
            def expected = giveAwayDTOFactory.factorize(giveAway)
        then:
            expected.prizeId == "12345"
            expected.amount == 3
    }
}
