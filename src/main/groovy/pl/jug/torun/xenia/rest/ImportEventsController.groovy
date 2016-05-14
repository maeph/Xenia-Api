package pl.jug.torun.xenia.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import pl.jug.torun.xenia.model.json.EventDTO
import pl.jug.torun.xenia.model.json.EventsDTO
import pl.jug.torun.xenia.model.json.PrizeDTO

/**
 * Created by piotr on 02.04.16.
 */
@RestController
@RequestMapping("/event/import")
class ImportEventsController {

    @Autowired
    ImportEventsService importService

    @RequestMapping(method = RequestMethod.POST, consumes = ["application/json"])
    void importEvents(@RequestBody EventsDTO request) {
        importService.importEvents(request)
    }
}
