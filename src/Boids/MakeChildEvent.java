package Boids;

import java.util.ArrayList;

import Events.Event;
import Events.EventManager;

/**
 * The MakeChildEvent class represents an event for generating child boids in the simulation.
 */
public class MakeChildEvent extends Event {
    // Constants
    public static final int MAX_BOIDS_SIZE = 160;

    // Attributes
    private final Boidz boids;
    private final EventManager manager;

    /**
     * Constructs a MakeChildEvent with a specified date, boid population, and event manager.
     *
     * @param date    The date of the event.
     * @param boids   The boid population in the simulation.
     * @param manager The event manager responsible for handling events in the simulation.
     */
    public MakeChildEvent(long date, Boidz boids, EventManager manager) {
        super(date);

        this.boids = boids;
        this.manager = manager;
    }

    /**
     * Executes the event, generating child boids from existing boids in the simulation.
     * If the boid population is below the maximum size limit, new boids are created.
     * The event is then scheduled for the next occurrence (10 iterations later).
     */
    @Override
    public void execute() {
        if (boids.getSize() < MAX_BOIDS_SIZE) {
            ArrayList<Boid> nLBoidsClone = new ArrayList<>(boids.getNatureLawBoids());
            for (Boid natureLawBoid : nLBoidsClone) {
                ArrayList<Boid> childs = natureLawBoid.makeChild(nLBoidsClone);
                for (Boid child : childs) {
                    boids.addNLBoid(child, natureLawBoid.getPredatorLevel());
                }
            }
        }

        manager.addEvent(new MakeChildEvent(getDate() + 10, boids, manager));
    }
}
