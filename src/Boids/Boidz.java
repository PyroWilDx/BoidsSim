package Boids;

import Balls.Balls;
import Simulation.GameSimulator;
import Utils.AppUtils;

import java.util.ArrayList;

/**
 * Class corresponding to multiple boids.
 */
public class Boidz extends Balls {
    // Attributes
    final ArrayList<Boid> natureLawBoids;
    double infectionP;
    boolean levelingMode;

    /**
     * Constructor.
     */
    public Boidz() {
        super();

        natureLawBoids = new ArrayList<>();
        this.infectionP = 0;
        this.levelingMode = false;
    }

    /**
     * Add a boid with its predator level.
     *
     * @param boid          a MovingPoint corresponding to a boid.
     * @param predatorLevel the predator level of the boid.
     */
    public void addNLBoid(Boid boid, int predatorLevel) {
        super.addBall(boid);

        boid.setPredatorLevel(predatorLevel);
        natureLawBoids.add(boid);
    }

    /**
     * Update the gui according to the rules applied for all boids.
     */
    @Override
    public void cycle() {
        for (Boid natureLawBoid : natureLawBoids) {
            natureLawBoid.cycleNatureLaw(natureLawBoids);
        }

        super.cycle();

        ArrayList<Boid> nLBoidsClone = new ArrayList<>(natureLawBoids);
        for (Boid natureLawBoid : nLBoidsClone) {
            Boid eater = natureLawBoid.checkIfDead(nLBoidsClone);
            if (eater == null) continue;

            double rd = AppUtils.random.nextFloat();
            if (rd < infectionP / eater.getPredatorLevel()) {
                natureLawBoid.setPredatorLevel(eater.getPredatorLevel());
            } else {
                getBalls().remove(natureLawBoid);
                natureLawBoids.remove(natureLawBoid);
                if (levelingMode) {
                    eater.setPredatorLevel(eater.getPredatorLevel() + 1);
                }
            }
        }
    }

    /**
     * Starting makeChildEvent.
     *
     * @param gameSimulator the simulator.
     */
    public void startMakeChildEvent(GameSimulator gameSimulator) {
        gameSimulator.addBaseEvent(new MakeChildEvent(0, this,
                gameSimulator.getManager()));
    }

    /**
     * Activate infection mode.
     *
     * @param infectionProbability the probability for one boid to infect another one.
     */
    public void activateInfectionMode(double infectionProbability) {
        this.infectionP = infectionProbability;
    }

    /**
     * Activate the infection mode with a probability of 1.
     */
    public void activateInfectionMode() {
        activateInfectionMode(1.);
    }

    /**
     * Activate level mode for predators.
     */
    public void activateLevelingMode() {
        this.levelingMode = true;
    }

    public ArrayList<Boid> getNatureLawBoids() {
        return natureLawBoids;
    }

}
