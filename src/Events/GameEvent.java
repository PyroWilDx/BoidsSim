package Events;

import Simulation.Simulator;

/**
 * Class handling the simulator with events.
 */
public class GameEvent extends Event {
    private final Simulator simulator;
    private final EventManager manager;

    /**
     * Constructor
     * @param date current date.
     * @param simulator the simulator.
     * @param manager the event handler.
     */
    public GameEvent(long date, Simulator simulator, EventManager manager) {
        super(date);

        this.simulator = simulator;
        this.manager = manager;
    }

    @Override
    public void execute() {
        simulator.cycle();
        manager.addEvent(new GameEvent(getDate() + 1, simulator, manager));
    }
}
