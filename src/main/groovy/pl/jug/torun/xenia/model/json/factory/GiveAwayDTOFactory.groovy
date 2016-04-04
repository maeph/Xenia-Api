package pl.jug.torun.xenia.model.json.factory

import pl.jug.torun.xenia.model.GiveAway
import pl.jug.torun.xenia.model.json.GiveAwayDTO

/**
 * Created by lsaw on 4/4/16.
 */
class GiveAwayDTOFactory {

    GiveAwayDTO factorize(GiveAway giveAway) {
        new GiveAwayDTO(
                prizeId: giveAway.prize.id,
                amount: giveAway.amount,
                draws: null)
    }
}
