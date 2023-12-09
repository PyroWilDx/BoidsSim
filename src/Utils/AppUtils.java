package Utils;

import java.util.Random;

/**
 * The AppUtils class provides utility methods for the application.
 */
public class AppUtils {
    public static int GUI_W = 600;
    public static int GUI_H = 600;
    public static final Random random = new Random();

    /**
     * Rotates an array of points around a specified center point by a given angle.
     *
     * @param xPoints The x-coordinates of the points.
     * @param yPoints The y-coordinates of the points.
     * @param cX      The x-coordinate of the center point.
     * @param cY      The y-coordinate of the center point.
     * @param angle   The angle of rotation in radians.
     */
    public static void rotatePoints(int[] xPoints, int[] yPoints,
                                    int cX, int cY, double angle) {
        double cosTheta = Math.cos(angle);
        double sinTheta = Math.sin(angle);

        for (int i = 0; i < xPoints.length; i++) {
            double xT = xPoints[i] - cX;
            double yT = yPoints[i] - cY;

            double xRotated = xT * cosTheta - yT * sinTheta;
            double yRotated = xT * sinTheta + yT * cosTheta;

            xPoints[i] = (int) (xRotated + cX);
            yPoints[i] = (int) (yRotated + cY);
        }
    }

    /**
     * Sets the size of the graphical user interface.
     *
     * @param guiW The width of the GUI.
     * @param guiH The height of the GUI.
     */
    public static void setGuiSize(int guiW, int guiH) {
        AppUtils.GUI_W = guiW;
        AppUtils.GUI_H = guiH;
    }

    /**
     * Generates random information for a simulated boid.
     *
     * @return An array of integers representing the boid's information.
     *         Index 0 and 1: initial position, Index 2 and 3: initial velocity.
     */
    public static int[] genRdBoidInfo() {
        int[] info = new int[4];
        info[0] = AppUtils.random.nextInt(AppUtils.GUI_W);
        info[1] = AppUtils.random.nextInt(AppUtils.GUI_H);
        info[2] = AppUtils.random.nextInt(10 * 2) - 10;
        info[3] = AppUtils.random.nextInt(10 * 2) - 10;
        return info;
    }

    /**
     * Private constructor because we should not make an instance of this class.
     */
    private AppUtils() {

    }

}
