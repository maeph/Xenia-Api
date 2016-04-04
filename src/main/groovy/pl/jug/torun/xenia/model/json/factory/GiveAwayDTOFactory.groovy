package pl.jug.torun.xenia.model.json.factory

import org.springframework.stereotype.Component
import pl.jug.torun.xenia.model.GiveAway
import pl.jug.torun.xenia.model.json.GiveAwayDTO

@Component
class GiveAwayDTOFactory {

    GiveAwayDTO factorize(GiveAway giveAway) {
        new GiveAwayDTO(
                prizeId: giveAway.prize.id,
                amount: giveAway.amount,
                draws: null)
    }
}
