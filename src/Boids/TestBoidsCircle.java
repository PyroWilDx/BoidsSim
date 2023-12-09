package Boids;

import Simulation.GameSimulator;
import Utils.AppUtils;
import gui.GUISimulator;

import java.awt.*;

public class TestBoidsCircle {
    public static void main(String[] args) {
        int size = 1000;
        int halfSize = size / 2;
        AppUtils.setGuiSize(size, size);
        GUISimulator gui = new GUISimulator(AppUtils.GUI_W, AppUtils.GUI_H, Color.BLACK);
        Boidz boids = new Boidz();
        int radius = halfSize / 2;
        int step = 4;
        for (int angle = 0; angle <= 360; angle += step) {
            double radians = Math.toRadians(angle);

            int x = (int) (radius + 80 + radius * Math.cos(radians));
            int y = (int) (radius + 80 + radius * Math.sin(radians));

            BoidImmovable boid = new BoidImmovable(x, y);
            boids.addNLBoid(boid, 2);
        }
        for (int i = 0; i < 60; i++) {
            int[] info = AppUtils.genRdBoidInfo();
            Boid boid = new Boid(info[0], info[1], info[2], info[3]);
            boids.addNLBoid(boid, 1);
        }
        GameSimulator gameSimulator = new GameSimulator(gui, boids);
        gui.setSimulable(gameSimulator);
    }
}
