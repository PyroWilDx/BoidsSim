package Boids;

import Simulation.GameSimulator;
import Utils.AppUtils;
import gui.GUISimulator;

import java.awt.*;

public class TestBoidsNatureLaw2 {
    public static void main(String[] args) {
        AppUtils.setGuiSize(1280, 720);
        GUISimulator gui = new GUISimulator(AppUtils.GUI_W, AppUtils.GUI_H, Color.BLACK);
        Boidz boids = new Boidz();
        int maxPredLevel = 8;
        for (int j = 1; j < maxPredLevel; j++) {
            for (int i = 0; i < Math.pow(2, j); i++) {
                int[] info = AppUtils.genRdBoidInfo();
                Boid boid = new Boid(info[0], info[1], info[2], info[3]);
                boids.addNLBoid(boid, maxPredLevel - j);
            }
        }
        GameSimulator gameSimulator = new GameSimulator(gui, boids);
        boids.startMakeChildEvent(gameSimulator);
        gui.setSimulable(gameSimulator);
    }
}
