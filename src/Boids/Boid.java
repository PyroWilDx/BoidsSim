package Boids;

import Balls.MovingPoint;
import Utils.AppUtils;
import gui.GraphicalElement;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Boid extends MovingPoint {
    // Constants
    public static final double COHESION_DIVISOR = 100.;
    public static final double COHESION_DISTANCE = 60.;

    public static final double SEPARATION_DIVISOR = 16.;
    public static final double SEPARATION_DISTANCE = COHESION_DISTANCE / 1.4;

    public static final double ALIGNMENT_DIVISOR = 6.;
    public static final double ALIGNMENT_DISTANCE = COHESION_DISTANCE;

    public static final double PREDATOR_DIVISOR = 1.;
    public static final double PREDATOR_DISTANCE = 100.;

    public static final double VICTIM_DIVISOR = 1.;
    public static final double VICTIM_DISTANCE = 80.;

    public static final double KILL_REACH_FACTOR = 4.4;

    public static final double BASE_CHILD_PROBABILITY = 0.2;
    public static final double PREDATOR_FACTOR = 1.;

    public static final int SPEED_RANGE = 6;

    // Attributes
    protected int ddx, ddy;
    private final int maxSpeed;
    private int predatorLevel;
    private int[] triangleX;
    private int[] triangleY;
    
    /**
     * Constructor.
     *
     * @param x  Initial x-coordinate.
     * @param y  Initial y-coordinate.
     * @param dx Initial x velocity.
     * @param dy Initial y velocity.
     */
    public Boid(int x, int y, int dx, int dy) {
        super(x, y, dx, dy);

        this.ddx = 0;
        this.ddy = 0;

        this.maxSpeed = AppUtils.random.nextInt(SPEED_RANGE + 1) + SPEED_RANGE;
        this.predatorLevel = 0;

        this.triangleX = new int[3];
        this.triangleY = new int[3];
    }

    /**
     * Method to scale vectors for each rules.
     *
     * @param p       The vector to be scaled.
     * @param cpt     Counter for nearby elements.
     * @param nX      x-coordinate of the reference point.
     * @param nY      y-coordinate of the reference point.
     * @param divisor The divisor for scaling.
     */
    private void scaleVector(Point2D.Double p, int cpt, int nX, int nY, double divisor) {
        if (cpt > 0) {
            p.x /= cpt;
            p.y /= cpt;
            p.x -= nX;
            p.y -= nY;
        }
        p.x /= divisor;
        p.y /= divisor;
    }

    /**
     * Method to scale the vector with a translation by x and y.
     *
     * @param p       The vector to be scaled.
     * @param cpt     Counter for nearby elements.
     * @param divisor The divisor for scaling.
     */
    private void scaleVectorXY(Point2D.Double p, int cpt, double divisor) {
        scaleVector(p, cpt, x, y, divisor);
    }

    /**
     * Cohesion behavior.
     *
     * @param otherBoids List of other boids.
     * @return Adjusted cohesion vector.
     */
    public Point2D.Double cohesionRule(ArrayList<MovingPoint> otherBoids) {
        Point2D.Double pcj = new Point2D.Double(0, 0);
        int cpt = 0;
        for (MovingPoint boid : otherBoids) {
            if (distance(boid) < COHESION_DISTANCE) {
                pcj.x += boid.x;
                pcj.y += boid.y;
                cpt++;
            }
        }
        scaleVectorXY(pcj, cpt, COHESION_DIVISOR);
        return pcj;
    }

     /**
     * Separation behavior.
     *
     * @param otherBoids List of other boids.
     * @return Adjusted separation vector.
     */
    public Point2D.Double separationRule(ArrayList<MovingPoint> otherBoids) {
        Point2D.Double c = new Point2D.Double(0, 0);
        for (MovingPoint boid : otherBoids) {
            if (distance(boid) < SEPARATION_DISTANCE) {
                c.x -= (boid.x - x);
                c.y -= (boid.y - y);
            }
        }
        scaleVectorXY(c, 0, SEPARATION_DIVISOR);
        return c;
    }

    /**
     * Alignment behavior.
     *
     * @param otherBoids List of other boids.
     * @return Adjusted alignment vector.
     */
    public Point2D.Double aligmentRule(ArrayList<MovingPoint> otherBoids) {
        Point2D.Double pvj = new Point2D.Double(0, 0);
        int cpt = 0;
        for (MovingPoint boid : otherBoids) {
            if (distance(boid) < ALIGNMENT_DISTANCE) {
                pvj.x += boid.getDx();
                pvj.y += boid.getDy();
                cpt++;
            }
        }
        scaleVector(pvj, cpt, getDx(), getDy(), ALIGNMENT_DIVISOR);
        return pvj;
    }

    /**
     * Predator behavior.
     *
     * @param nLOtherBoids List of nearby other boids.
     * @return Adjusted predator vector.
     */
    public Point2D.Double predatorRule(ArrayList<Boid> nLOtherBoids) {
        Point2D.Double pdt = new Point2D.Double(0, 0);
        int cpt = 0;
        for (Boid nLBoid : nLOtherBoids) {
            if (predatorLevel <= nLBoid.predatorLevel) continue;

            int reach = getReach();
            if (distance(nLBoid) < PREDATOR_DISTANCE + reach) {
                pdt.x += nLBoid.x;
                pdt.y += nLBoid.y;
                cpt++;
            }
        }
        scaleVectorXY(pdt, cpt, PREDATOR_DIVISOR);
        return pdt;
    }

    /**
     * Victim behavior.
     *
     * @param nLOtherBoids List of nearby other boids.
     * @return Adjusted victim vector.
     */
    public Point2D.Double victimRule(ArrayList<Boid> nLOtherBoids) {
        Point2D.Double vct = new Point2D.Double(0, 0);
        for (Boid nLBoid : nLOtherBoids) {
            if (predatorLevel >= nLBoid.predatorLevel) continue;

            Point reachCenter = nLBoid.getReachCenter();
            int reach = nLBoid.getReach();
            if (distance(reachCenter) < VICTIM_DISTANCE + reach) {
                vct.x += nLBoid.getDx();
                vct.y += nLBoid.getDy();
            }
        }
        scaleVectorXY(vct, 0, VICTIM_DIVISOR);
        return vct;
    }
    /**
     * Check if the current boid is dead because of a predator behavior depending of the level on the food chain.
     *
     * @param nLOtherBoids List of nearby other boids.
     * @return The dead boid or null if none.
     */
    public Boid checkIfDead(ArrayList<Boid> nLOtherBoids) {
        for (Boid nLBoid : nLOtherBoids) {
            if (predatorLevel >= nLBoid.predatorLevel) continue;

            Point reachCenter = nLBoid.getReachCenter();
            int reach = nLBoid.getReach();
            if (distance(reachCenter) < reach + 1) {
                return nLBoid;
            }
        }
        return null;
    }

     /**
     * Create child boids based for two boids on the same level and close enough.
     *
     * @param nLOtherBoids List of nearby other boids.
     * @return List of child boids.
     */
    public ArrayList<Boid> makeChild(ArrayList<Boid> nLOtherBoids) {
        ArrayList<Boid> childs = new ArrayList<>();
        for (Boid nLBoid : nLOtherBoids) {
            if (predatorLevel != nLBoid.predatorLevel) continue;

            if (distance(nLBoid) < 10) {
                float rd = AppUtils.random.nextFloat();
                if (rd < BASE_CHILD_PROBABILITY / Math.pow(1.6, (nLBoid.predatorLevel + 1) * PREDATOR_FACTOR)) {
                    Boid child = (Boid) this.clone();
                    child.setLocation(x - getDx(), y - getDy());
                    childs.add(child);
                }
            }
        }
        return childs;
    }

    /**
     * Update the position of the boid thanks to the rules.
     */
    @Override
    public void cycle(ArrayList<MovingPoint> boids) {
        ArrayList<MovingPoint> otherBoids = new ArrayList<>(boids);
        otherBoids.remove(this);

        Point2D.Double pcj = cohesionRule(otherBoids);
        Point2D.Double c = separationRule(otherBoids);
        Point2D.Double pvj = aligmentRule(otherBoids);
        ddx += (int) Math.round(pcj.x + c.x + pvj.x);
        ddy += (int) Math.round(pcj.y + c.y + pvj.y);

        setLimitedSpeed(getDx() + ddx, getDy() + ddy);
    }

    /**
     * Apply nature laws related to predator and victim behavior.
     *
     * @param nLBoids List of nearby other boids.
     */
    public void cycleNatureLaw(ArrayList<Boid> nLBoids) {
        ArrayList<Boid> nLOtherBoids = new ArrayList<>(nLBoids);
        nLOtherBoids.remove(this);

        Point2D.Double pdt = predatorRule(nLOtherBoids);
        Point2D.Double vct = victimRule(nLOtherBoids);
        ddx += (int) Math.round(pdt.x + vct.x);
        ddy += (int) Math.round(pdt.y + vct.y);
    }

    /**
     * Set speed with a maximum limit.
     *
     * @param dx  X velocity.
     * @param dy  Y velocity.
     * @param max Maximum speed limit.
     */
    public void setLimitedSpeed(int dx, int dy, int max) {
        // We do limit the speed because with the 3 rules, the velocity can increase a lot.
        int limitedDx = dx;
        int limitedDy = dy;
        double currentSpeed = Math.sqrt(dx * dx + dy * dy);
        if (currentSpeed > max) {
            double scaleFactor = max / currentSpeed;
            limitedDx = (int) Math.round(limitedDx * scaleFactor);
            limitedDy = (int) Math.round(limitedDy * scaleFactor);
        }
        setSpeed(limitedDx, limitedDy);

        ddx = 0;
        ddy = 0;
    }

     /**
     * Set speed with the maximum limit based on predator level.
     *
     * @param dx velocity on the x-axis.
     * @param dy velocity on the y-axis.
     */
    public void setLimitedSpeed(int dx, int dy) {
        int divisor = Math.max(1, predatorLevel);
        double max = maxSpeed + (double) maxSpeed / divisor;
        setLimitedSpeed(dx, dy, (int) max);
    }

    /**
     * Update the orientation of a boid for the gui.
     */
    public void updateTriangle() {
        int tW = (predatorLevel + 1) * 2;
        int tH = (predatorLevel + 1) * 4;

        triangleX[0] = x;
        triangleY[0] = y + tH;

        triangleX[1] = x + tW;
        triangleY[1] = y - tH;

        triangleX[2] = x - tW;
        triangleY[2] = y - tH;

        double angle = Math.atan2(getDy(), getDx()) - Math.PI / 2.;
        AppUtils.rotatePoints(triangleX, triangleY, x, y, angle);
    }

    /**
     * Create the GraphicalElement of a boid (oriented triangle).
     */
    public GraphicalElement getGraphics() {
        updateTriangle();

        return (var1 -> {
            Stroke var2 = var1.getStroke();
            var1.setStroke(new BasicStroke(2.0F));
            Color var3 = var1.getColor();

            var1.setColor(getFillColor());

            var1.fillPolygon(triangleX, triangleY, 3);

            var1.setColor(getBorderColor());
            var1.drawPolygon(triangleX, triangleY, 3);

            // Display Reach
            var1.setColor(Color.WHITE);
            Point reachCenter = getReachCenter();
            int size = getReach() * 2;
            var1.drawOval(reachCenter.x - size / 2,
                    reachCenter.y - size / 2,
                    size, size);

            var1.setColor(var3);
            var1.setStroke(var2);
        });
    }

    /** 
     * Get the fill color based on predator level.
     */ 
    @Override
    public Color getFillColor() {
        if (predatorLevel < 2) return super.getBorderColor();

        int nuance = Math.max(255 - (int) (predatorLevel * 25.5), 0);
        return new Color(255, nuance, 0);
    }

    /**
     * Clone function.
     *
     * @return return a copy of a boid.
     */ 
    @Override
    public Object clone() {
        Boid cloned = (Boid) super.clone();

        cloned.triangleX = new int[3];
        cloned.triangleY = new int[3];

        for (int i = 0; i < 3; i++) {
            cloned.triangleX[i] = triangleX[i];
            cloned.triangleY[i] = triangleY[i];
        }

        return cloned;
    }

    /** 
     * Get the center of the reach.
     */ 
    private Point getReachCenter() {
        return new Point(triangleX[0], triangleY[0]);
    }

    /** 
     * Get the reach.
     */ 
    private int getReach() {
        return (int) Math.round(predatorLevel * KILL_REACH_FACTOR / 2.);
    }

    public void setPredatorLevel(int predatorLevel) {
        this.predatorLevel = predatorLevel;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public int getPredatorLevel() {
        return predatorLevel;
    }

}
