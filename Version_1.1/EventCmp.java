import java.util.Comparator;

class EventCmp implements Comparator<Event> {
    public int compare(Event e1, Event e2) {
        if (e1.getTime().compareTo(e2.getTime()) == 0) {
            return e1.getID() - e2.getID();
        }
        return e1.getTime().compareTo(e2.getTime());
    }
}

