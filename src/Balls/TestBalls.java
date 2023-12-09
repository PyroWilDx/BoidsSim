package Balls;

/**
 * Testing the class Balls.Balls
 */
public class TestBalls {
    public static void main(String[] args) {
        // Init all balls.
        Balls balls = new Balls();
        balls.addBall(new MovingPoint(1, 20));
        balls.addBall(new MovingPoint(2, 2));
        balls.addBall(new MovingPoint(30, 2));
        balls.addBall(new MovingPoint(4, 2));
        balls.addBall(new MovingPoint(60, 10));
        balls.addBall(new MovingPoint(6, 2));

        System.out.println("===Initial Positions===");
        System.out.println(balls);

        // Testing translating all balls.
        System.out.println("===Translating all balls by (10, 10)===");
        balls.translate(10, 10);
        System.out.println(balls);

        // Testing the method reset.
        System.out.println("===Reseting all balls===");
        balls.reset();
        System.out.println(balls);
    }
}
