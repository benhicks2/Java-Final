<<<<<<< HEAD
import javafx.scene.shape.Rectangle;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball {
    private Pong pong;
    private static final double width = 20, height = 20;
    private double x, y, cWidth, cHeight;
    private final double xChangeOriginal = -3, yChangeOriginal = -2; //original x and y velocity
    private double xChange = xChangeOriginal, yChange = yChangeOriginal; //x and y velocity
    private String user = "user", computer = "computer";

    public Ball(Pong pong, double cWidth, double cHeight) {
        this.pong = pong;
        this.cWidth = cWidth;
        this.cHeight = cHeight;
        x = cWidth/2;
        y = cHeight/2;
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
        if (pong.getPlayer(user).getBounds().intersects(getBounds())) {
            changeDirection(user);
        }
        if (pong.getPlayer(computer).getBounds().intersects(getBounds())) {
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
        x = cWidth/2;
        y = cHeight/2;
        xChange = xChangeOriginal;
        yChange = yChangeOriginal;
        pong.increaseScore(paddle);
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
=======
import javafx.scene.shape.Rectangle;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball {
    private Pong pong;
    private static final double width = 20, height = 20;
    private double x, y, cWidth, cHeight, xChange = -2, yChange = -2;
    private String user = "user", computer = "computer";

    public Ball(Pong pong, double cWidth, double cHeight) {
        this.pong = pong;
        this.cWidth = cWidth;
        this.cHeight = cHeight;
        x = cWidth/2;
        y = cHeight/2;
    }
    public void update() {
        x += xChange;
        y += yChange;

        if (x < 0) {
            x = cWidth/2;
            y = cHeight/2;
            pong.increaseScore(computer);
        }
        else if (x > cWidth + width) {
            x = cWidth/2;
            y = cHeight/2;
            pong.increaseScore(user);
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
        if (pong.getPlayer(user).getBounds().intersects(getBounds()) || pong.getPlayer(computer).getBounds().intersects(getBounds())) {
            xChange *= -1;
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
>>>>>>> be031410bcf2b15df5bdcea65c82c9ae2a256fbc
