package pl.jug.torun.xenia.model

import pl.jug.torun.xenia.model.json.DrawDTO
import pl.jug.torun.xenia.model.json.GiveAwayDTO

/**
 * Created by Wojciech Oczkowski on 2016-06-25.
 */
class GiveAwayDTOBuilder {
    String prizeUuid
    int amount
    List<DrawDTO> draws = []

    def prizeUuid(String prizeUuid){
        this.prizeUuid = prizeUuid
        this
    }

    def amount(int amount){
        this.amount = amount
        this
    }

    def draws(List<DrawDTO> draws){
        this.draws.addAll(draws)
        this
    }

    def draws(DrawDTO ... draws){
        this.draws.addAll(draws)
        this
    }

    def buid(){
        if(prizeUuid == null){
            throw new NullPointerException("prizeUuid must be set")
        }
        new GiveAwayDTO(prizeUuid: prizeUuid, amount: amount, draws: draws)
    }
}
