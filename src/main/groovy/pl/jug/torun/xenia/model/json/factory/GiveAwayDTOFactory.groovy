package pl.jug.torun.xenia.model.json.factory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pl.jug.torun.xenia.model.GiveAway
import pl.jug.torun.xenia.model.json.GiveAwayDTO

@Component
class GiveAwayDTOFactory {

    DrawDTOFactory drawDTOFactory

    @Autowired
    GiveAwayDTOFactory(DrawDTOFactory drawDTOFactory) {
        this.drawDTOFactory = drawDTOFactory
    }

    GiveAwayDTO factorize(GiveAway giveAway) {
        def drawDTOs = giveAway.draws.collect {
            drawDTOFactory.factorize(it)
        }
        new GiveAwayDTO(
                prizeUuid: giveAway.prize.uuid,
                amount: giveAway.amount,
                draws: drawDTOs)
    }
}
