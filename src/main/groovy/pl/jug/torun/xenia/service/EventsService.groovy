package pl.jug.torun.xenia.service

import pl.jug.torun.xenia.model.Event

/**
 * Manages events
 *
 * @author Marcin Świerczyński
 */
interface EventsService {

    /**
     * Returns all events
     *
     * @return List of Events
     */
    public List<Event> findAll()


    /**
     * Refreshes single event from, possible, external source
     */
    public void refreshEvent(long id)


    /**
     * Refreshes events from, possible, external source
     */
    public void refreshEvents()

}