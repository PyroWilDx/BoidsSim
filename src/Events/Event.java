package Events;

public abstract class Event {
    private final long date;

    public Event(long date) {
        this.date = date;
    }

    public abstract void execute();

    public long getDate() {
        return date;
    }
}
