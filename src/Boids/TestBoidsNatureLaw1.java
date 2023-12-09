package Boids;

import Simulation.GameSimulator;
import Utils.AppUtils;
import gui.GUISimulator;

import java.awt.*;

public class TestBoidsNatureLaw1 {
    public static void main(String[] args) {
        AppUtils.setGuiSize(1280, 720);
        GUISimulator gui = new GUISimulator(AppUtils.GUI_W, AppUtils.GUI_H, Color.BLACK);
        Boidz boids = new Boidz();
        for (int i = 0; i < 120; i++) {
            int[] info = AppUtils.genRdBoidInfo();
            Boid boid = new Boid(info[0], info[1], info[2], info[3]);
            boids.addNLBoid(boid, 1);
        }
        for (int i = 0; i < 70; i++) {
            int[] info = AppUtils.genRdBoidInfo();
            Boid boid = new Boid(info[0], info[1], info[2], info[3]);
            boids.addNLBoid(boid, 2);
        }
        for (int i = 0; i < 10; i++) {
            int[] info = AppUtils.genRdBoidInfo();
            Boid boid = new Boid(info[0], info[1], info[2], info[3]);
            boids.addNLBoid(boid, 8);
        }
        GameSimulator gameSimulator = new GameSimulator(gui, boids);
        boids.startMakeChildEvent(gameSimulator);
        gui.setSimulable(gameSimulator);
    }
}
