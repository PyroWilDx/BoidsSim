package Game.ImmigrationGame;

import Simulation.GameSimulator;
import Game.Grid;
import Utils.AppUtils;
import gui.GUISimulator;

import java.awt.*;


public class TestImmigrationGame {
    public static void main(String[] args) {
        AppUtils.setGuiSize(600, 600);
        GUISimulator gui = new GUISimulator(AppUtils.GUI_W, AppUtils.GUI_H, Color.WHITE);
        int squareSize = Grid.SQUARE_DISPLAY_SIZE;
        GameSimulator gameSimulator = new GameSimulator(
                gui,
                new ImmigrationGrid(AppUtils.GUI_W / squareSize,
                        AppUtils.GUI_H / squareSize,
                        4)
        );
        gui.setSimulable(gameSimulator);
    }
}
