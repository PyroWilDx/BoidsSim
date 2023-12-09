package Boids;

import Simulation.GameSimulator;
import Utils.AppUtils;
import gui.GUISimulator;

import java.awt.*;

public class TestBoidsObstacle {
    public static void main(String[] args) {
        AppUtils.setGuiSize(1280, 720);
        GUISimulator gui = new GUISimulator(AppUtils.GUI_W, AppUtils.GUI_H, Color.BLACK);
        Boidz boids = new Boidz();
        boids.addNLBoid(new BoidImmovable(100, 100), 2);
        boids.addNLBoid(new BoidImmovable(200, 200), 2);
        boids.addNLBoid(new BoidImmovable(600, 160), 2);
        boids.addNLBoid(new BoidImmovable(1000, 600), 2);
        boids.addNLBoid(new BoidImmovable(640, 360), 2);
        boids.addNLBoid(new BoidImmovable(700, 400), 2);
        boids.addNLBoid(new BoidImmovable(600, 340), 2);
        boids.addNLBoid(new BoidImmovable(700, 320), 2);
        boids.addNLBoid(new BoidImmovable(590, 410), 2);
        boids.addNLBoid(new BoidImmovable(600, 300), 2);
        boids.addNLBoid(new BoidImmovable(540, 360), 2);
        boids.addNLBoid(new BoidImmovable(640, 480), 2);

        for (int i = 0; i < 100; i++) {
            int[] info = AppUtils.genRdBoidInfo();
            float rd = AppUtils.random.nextFloat();
            Boid boid;
            if (rd < 0.4) boid = new Boid(info[0], info[1], info[2], info[3]);
            else if (rd < 0.6) boid = new BoidWinded(info[0], info[1], info[2], info[3]);
            else boid = new BoidGoal(info[0], info[1], info[2], info[3]);
            boids.addNLBoid(boid, 1);
        }
        GameSimulator gameSimulator = new GameSimulator(gui, boids);
        gui.setSimulable(gameSimulator);
    }
}
