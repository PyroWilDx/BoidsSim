package Events;

import java.util.LinkedList;
/**
 * Class handling event.
 */
public class EventManager {
    private final LinkedList<Event> events;
    private long currentDate;

    /**
     * Constructor.
     */
    public EventManager() {
        this.events = new LinkedList<>();
        this.currentDate = 0;
    }

    /**
     * Add the event at the good position in all events depending their date.
     * @param event
     */
    public void addEvent(Event event) {
        int i = 0;
        for (Event currEvent : events) {
            if (currEvent.getDate() > event.getDate()) {
                events.add(i, event);
                return;
            }
            i++;
        }
        events.add(event);
    }

    /**
     * Execute the event and check if the current event must be changed.
     */
    public void next() {
        currentDate++;

        int cptDone = 0;
        LinkedList<Event> eventsCopy = new LinkedList<>(events);
        for (Event currEvent : eventsCopy) {
            if (currEvent.getDate() <= currentDate) {
                currEvent.execute();
                cptDone++;
            } else break;
        }
        for (int i = 0; i < cptDone; i++) {
            events.remove();
        }
    }

    public boolean isFinished() {
        return events.isEmpty();
    }

    public void restart() {
        events.clear();
        currentDate = 0;
    }

}
