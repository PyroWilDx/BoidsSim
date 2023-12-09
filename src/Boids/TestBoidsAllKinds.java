package Boids;

import java.awt.Color;

import Simulation.GameSimulator;
import Utils.AppUtils;
import gui.GUISimulator;

public class TestBoidsAllKinds {
    public static void main(String[] args) {
        AppUtils.setGuiSize(1600, 900);
        GUISimulator gui = new GUISimulator(AppUtils.GUI_W, AppUtils.GUI_H, Color.BLACK);
        Boidz boids = new Boidz();
        for (int i = 0; i < 100; i++) {
            int[] info = AppUtils.genRdBoidInfo();
            Boid boid = new BoidWinded(info[0], info[1], info[2], info[3]);
            boids.addNLBoid(boid, 1);
        }
        for (int i = 0; i < 100; i++) {
            int[] info = AppUtils.genRdBoidInfo();
            Boid boid = new BoidGoal(info[0], info[1], info[2], info[3]);
            boids.addNLBoid(boid, 1);
        }
        for (int i = 0; i < 100; i++) {
            int[] info = AppUtils.genRdBoidInfo();
            Boid boid = new Boid(info[0], info[1], info[2], info[3]);
            boids.addNLBoid(boid, 1);
        }
        for (int i = 0; i < 80; i++) {
            int[] info = AppUtils.genRdBoidInfo();
            Boid boid = new Boid(info[0], info[1], info[2], info[3]);
            boids.addNLBoid(boid, 2);
        }
        for (int i = 0; i < 4; i++) {
            int[] info = AppUtils.genRdBoidInfo();
            Boid boid = new BoidGoal(info[0], info[1], info[2], info[3]);
            boids.addNLBoid(boid, 8);
        }
        for (int i = 0; i < 4; i++) {
            int[] info = AppUtils.genRdBoidInfo();
            Boid boid = new Boid(info[0], info[1], info[2], info[3]);
            boids.addNLBoid(boid, 8);
        }
        for (int i = 0; i < 4; i++) {
            int[] info = AppUtils.genRdBoidInfo();
            Boid boid = new BoidWinded(info[0], info[1], info[2], info[3]);
            boids.addNLBoid(boid, 8);
        }
        GameSimulator gameSimulator = new GameSimulator(gui, boids);
        boids.startMakeChildEvent(gameSimulator);
        gui.setSimulable(gameSimulator);
    }
}
