package pl.jug.torun.xenia.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.jug.torun.xenia.model.json.EventsDTO
import pl.jug.torun.xenia.service.ExportEventsService

import static org.springframework.web.bind.annotation.RequestMethod.GET

/**
 * Created by Lukasz on 2016-04-02.
 */
@RestController
@RequestMapping("/export")
class ExportEventsController {

    final ExportEventsService exportEventsService

    @Autowired
    ExportEventsController(ExportEventsService exportEventsService) {
        this.exportEventsService = exportEventsService
    }

    @RequestMapping(method = GET)
    EventsDTO exportEvents() {
        exportEventsService.exportAllEvents()
    }
}
