package Events;

public class TestEventManager {
    public static void main(String[] args) throws InterruptedException {
        EventManager manager = new EventManager();

        // On poste un événement [ PING ] tous les deux pas de temps
        for (int i = 2; i <= 10; i += 2) {
            manager.addEvent(new MessageEvent(i, " [ PING ]"));
        }
        // On poste un événement [ PONG ] tous les trois pas de temps
        for (int i = 3; i <= 9; i += 3) {
            manager.addEvent(new MessageEvent(i, " [ PONG ]"));
        }

        while (!manager.isFinished()) {
            manager.next();
            Thread.sleep(1000);
        }
    }
}


class MessageEvent extends Event {
    private final String message;

    public MessageEvent(int date, String message) {
        super(date);
        this.message = message;
    }

    public void execute() {
        System.out.println(this.getDate() + this.message);
    }
}