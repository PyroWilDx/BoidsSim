package Simulation;

import Events.Event;
import Events.EventManager;
import Events.GameEvent;
import gui.GUISimulator;
import gui.Simulable;

import java.util.ArrayList;

/**
 * The game simulator for games.
 */
public class GameSimulator implements Simulable {

    // Attributes
    boolean started;
    private final GUISimulator gui;
    private final Simulator simulator;
    private final EventManager manager;
    private final ArrayList<Event> baseEvents;

    /**
     * Constructs a GameSimulator with a specified GUI, simulator, and event manager.
     *
     * @param gui       The graphical user interface for the simulator.
     * @param simulator The underlying simulator for game logic.
     * @param manager   The event manager to handle events in the simulation.
     */
    public GameSimulator(GUISimulator gui, Simulator simulator,
                         EventManager manager) {
        this.started = false;
        this.gui = gui;
        this.simulator = simulator;
        this.manager = manager;
        this.baseEvents = new ArrayList<>();
        addBaseEvent(new GameEvent(0, simulator, manager));
    }

    /**
     * Constructs a GameSimulator with a specified GUI and simulator, using a default event manager.
     *
     * @param gui       The graphical user interface for the simulator.
     * @param simulator The underlying simulator for game logic.
     */
    public GameSimulator(GUISimulator gui, Simulator simulator) {
        this(gui, simulator, new EventManager());
    }

    /**
     * Adds a base event to the list of base events for initialization.
     *
     * @param baseEvent The base event to be added.
     */
    public void addBaseEvent(Event baseEvent) {
        baseEvents.add(baseEvent);
    }

    /**
     * Advances the simulation to the next step.
     * If the simulator has not been started, a message is printed.
     */
    @Override
    public void next() {
        if (!started) {
            System.out.println("Please click on \"Start\" first !");
            return;
        }

        manager.next();

        draw();
    }

    /**
     * Restarts the simulation, initializing the simulator, event manager, and base events.
     */
    @Override
    public void restart() {
        started = true;

        simulator.reset();
        manager.restart();
        for (Event event : baseEvents) {
            manager.addEvent(event);
        }

        draw();
    }

    /**
     * Clears the graphical user interface and redraws the simulation based on the current state.
     */
    public void draw() {
        gui.reset();

        simulator.drawSelf(gui);
    }

    /**
     * Gets the event manager associated with the simulator.
     *
     * @return The event manager.
     */
    public EventManager getManager() {
        return manager;
    }
}
