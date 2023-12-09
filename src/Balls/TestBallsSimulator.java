package Balls;

import Simulation.GameSimulator;
import Utils.AppUtils;
import gui.GUISimulator;

import java.awt.Color;

/**
 * Testing the simulation of multiple balls moving on the graphic interface.
 */
public class TestBallsSimulator {
    public static void main(String[] args) {
        AppUtils.setGuiSize(600, 600);
        GUISimulator gui = new GUISimulator(AppUtils.GUI_W, AppUtils.GUI_H, Color.BLACK);
        Balls balls = new Balls();
        balls.setBounce(true);
        balls.addBall(new MovingPoint(1, 40, 10, 10));
        balls.addBall(new MovingPoint(10, 10, 10, 10));
        balls.addBall(new MovingPoint(20, 10, 20, 0));
        balls.addBall(new MovingPoint(30, 0, 0, 62));
        balls.addBall(new MovingPoint(360, 400, -4, 10));
        balls.addBall(new MovingPoint(42, 42, -1, -1));
        GameSimulator gameSimulator = new GameSimulator(gui, balls);
        gui.setSimulable(gameSimulator);
    }
}