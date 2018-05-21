import javafx.scene.shape.Rectangle;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

public class Ball {
    private Pong pong;
    private static final double width = 15, height = 15;
    private double x, y, p2Width, p2Height;
    private double xChange, yChange; //x and y velocity
    private String player1 = "player1", player2 = "player2";

    public Ball(Pong pong, double p2Width, double p2Height) {
        this.pong = pong;
        this.p2Width = p2Width;
        this.p2Height = p2Height;
        x = p2Width/2;
        y = p2Height/2;
        randomDirection(-1);
    }
    public void update() {
        x += xChange;
        y += yChange;

        if (x < 0)
            resetBall(player2);
        else if (x > p2Width + width)
            resetBall(player1);
        else if (y <= 0)
            yChange *= -1;
        else if (y + height >= p2Height)
            yChange *= -1;

        checkCollision();
    }
    public void paint(GraphicsContext pen) {
        pen.setFill(Color.WHITE);
        pen.fillOval(x, y, width, height);
    }

    public void checkCollision() {
        Paddle player1Paddle = pong.getPlayer(player1);
        Paddle player2Paddle = pong.getPlayer(player2);
        double xPosplayer1 = player1Paddle.getXPos();
        double xPosplayer2 = player2Paddle.getXPos();
        if (player1Paddle.getBounds().intersects(getBounds()) && x >= xPosplayer1 + player1Paddle.getWidth() - 6)
            changeDirection(player1);
        if (player2Paddle.getBounds().intersects(getBounds()) && x + width <= xPosplayer2 + 6)
            changeDirection(player2);
    }
    public void changeDirection(String paddle) {
        xChange *= -1;
        int yDiff = 1;
        if (pong.getPlayer(paddle).getMovement() < 0 && yChange < 0)
            yChange -= yDiff;
        else if (pong.getPlayer(paddle).getMovement() > 0 && yChange > 0)
            yChange += yDiff;
        else if (pong.getPlayer(paddle).getMovement() > 0 && yChange < 0)
            yChange += yDiff;
        else if (pong.getPlayer(paddle).getMovement() < 0 && yChange > 0)
            yChange -= yDiff;
    }
    public void resetBall(String paddle) {
        int yMin = -3;
        int yMax = 3;
        int xDirection = 1;
        x = p2Width / 2;
        y = p2Height / 2;
        if (paddle == player2)
            xDirection = -1;
        randomDirection(xDirection);
        pong.increaseScore(paddle);
    }
    public void randomDirection(int xDirection) {
        int yMin = -4;
        int yMax = 4;
        Random rand = new Random();
        xChange = 4 * xDirection;
        yChange = yMin + (yMax  - yMin) * rand.nextDouble();
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
