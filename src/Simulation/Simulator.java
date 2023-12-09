package Simulation;

import gui.GUISimulator;

/**
 * The Simulator interface.
 */
public interface Simulator {

    /**
     * Advance the simulation by one cycle.
     */
    void cycle();

    /**
     * Reset the simulation to its initial state.
     */
    void reset();

    /**
     * Draw the current state of the simulation on the provided graphical user interface.
     * @param gui The graphical user interface where the simulation will be drawn.
     */
    void drawSelf(GUISimulator gui);
}
