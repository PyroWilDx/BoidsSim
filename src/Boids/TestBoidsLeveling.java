package Boids;

import Simulation.GameSimulator;
import Utils.AppUtils;
import gui.GUISimulator;

import java.awt.*;

public class TestBoidsLeveling {
    public static void main(String[] args) {
        AppUtils.setGuiSize(1280, 720);
        GUISimulator gui = new GUISimulator(AppUtils.GUI_W, AppUtils.GUI_H, Color.BLACK);
        Boidz boids = new Boidz();
        boids.activateLevelingMode();
        for (int i = 0; i < 100; i++) {
            int[] info = AppUtils.genRdBoidInfo();
            Boid boid = new Boid(info[0], info[1], info[2], info[3]);
            boids.addNLBoid(boid, 1);
        }
        int[] info = AppUtils.genRdBoidInfo();
        Boid boid = new Boid(info[0], info[1], info[2], info[3]);
        boids.addNLBoid(boid, 4);

        GameSimulator gameSimulator = new GameSimulator(gui, boids);
        gui.setSimulable(gameSimulator);
    }
}
