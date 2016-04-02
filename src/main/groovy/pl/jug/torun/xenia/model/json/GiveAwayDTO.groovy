package pl.jug.torun.xenia.model.json

/**
 * Created by zbyszko on 02.04.16.
 */
class GiveAwayDTO {
    String prizeId
    int amount
    List<DrawDTO> draws
    /**
     *   {
     "meetupId":"dkasldkf",
     "giveaways": [
     {
     "prizeId": "ldifjsdf-fsdw4r",
     "amount": 2,
     "draws": [
     {
     "meetupMemberId": "meetupMemberId1",
     "drawDate": "data",
     "confirmed": true
     },
     {
     "meetupMemberId": "meetupMemberId2",
     "drawDate": "data",
     "confirmed": true
     }
     ]
     }
     ]
     }
     */
}
