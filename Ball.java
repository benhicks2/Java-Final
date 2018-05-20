import javafx.scene.shape.Rectangle;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

public class Ball {
    private Pong pong;
    private static final double width = 20, height = 20;
    private double x, y, cWidth, cHeight;
    private double xChange, yChange; //x and y velocity
    private String user = "user", computer = "computer";

    public Ball(Pong pong, double cWidth, double cHeight) {
        this.pong = pong;
        this.cWidth = cWidth;
        this.cHeight = cHeight;
        x = cWidth/2;
        y = cHeight/2;
        randomDirection();
    }
    public void update() {
        x += xChange;
        y += yChange;

        if (x < 0) {
            resetBall(computer);
        }
        else if (x > cWidth + width) {
            resetBall(user);
        }
        else if (y <= 0) {
            yChange *= -1;
        }
        else if (y + height >= cHeight) {
            yChange *= -1;
        }

        checkCollision();
    }
    public void paint(GraphicsContext pen) {
        pen.setFill(Color.WHITE);
        pen.fillRect(x, y, width, height);
    }

    public void checkCollision() {
        Paddle userPaddle = pong.getPlayer(user);
        Paddle computerPaddle = pong.getPlayer(computer);
        double xPosUser = userPaddle.getXPos();
        double xPosComputer = computerPaddle.getXPos();
        if (userPaddle.getBounds().intersects(getBounds()) && x >= xPosUser + userPaddle.getWidth() - 6) {
            changeDirection(user);
        }
        if (computerPaddle.getBounds().intersects(getBounds()) && x + width <= xPosComputer + 6) {
            changeDirection(computer);
        }
    }
    public void changeDirection(String paddle) {
        xChange *= -1;
        if (pong.getPlayer(paddle).getMovement() < 0 && yChange < 0) {
            yChange -= 0.5;
        }
        else if (pong.getPlayer(paddle).getMovement() > 0 && yChange > 0) {
            yChange += 0.5;
        }
        else if (pong.getPlayer(paddle).getMovement() > 0 && yChange < 0) {
            yChange += 0.5;
        }
        else if (pong.getPlayer(paddle).getMovement() < 0 && yChange > 0) {
            yChange -= 0.5;
        }
    }
    public void resetBall(String paddle) {
        int yMin = -3;
        int yMax = 3;
        int xDirection = 1;
        x = cWidth/2;
        y = cHeight/2;
        if (paddle == computer) {
            xDirection = -1;
        }
        randomDirection();
        pong.increaseScore(paddle);
    }
    public void randomDirection() {
        int yMin = -3;
        int yMax = 3;
        int xDirection = -1;
        Random rand = new Random();
        xChange = 3*xDirection;
        yChange = yMin + (yMax  - yMin)*rand.nextDouble();
        if (Math.abs(yChange) <= 0.3) {
            yChange = 1;
        }
    }
    public Bounds getBounds() {
        Rectangle rect = new Rectangle(x, y, width, height);
        Bounds rectBounds = rect.getBoundsInParent();
        return rectBounds;
    }
    public double getXPos() {
        return x;
    }
    public double getYPos() {
        return y;
    }
}
