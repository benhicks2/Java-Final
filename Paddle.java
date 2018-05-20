<<<<<<< HEAD
import javafx.scene.shape.Rectangle;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle {
    private Pong pong;
    private static final double width = 30, height = 100;
    private double x, y, cWidth, cHeight;
    private double speed = 5;
    private double yChange = 0;

    public Paddle(Pong pong, double x, double y, double cWidth, double cHeight, boolean ai) {
        this.pong = pong;
        this.cWidth = cWidth;
        this.cHeight = cHeight;
        this.x = x;
        this.y = y;
    }
    public void move(double direction, boolean down) {
        if (down == true) {
            yChange = speed * direction;
        }
        else {
            yChange = 0;
        }
    }
    public void update() {
        y += yChange;
        if (y <= 0) {
            y = 0;
        }
        else if (y >= cHeight - height) {
            y = cHeight - height;
        }
    }
    public void paint(GraphicsContext pen) {
        pen.setFill(Color.WHITE);
        pen.fillRect(x, y, width, height);
    }

    public double getMovement() {
        return yChange;
    }

    public Bounds getBounds() {
        Rectangle rect = new Rectangle(x, y, width, height);
        Bounds rectBounds = rect.getBoundsInParent();
        return rectBounds;
    }
}
=======
import javafx.scene.shape.Rectangle;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle {
    private Pong pong;
    private static final double width = 30, height = 100;
    private double x, y, cWidth, cHeight;
    private double speed = 5;
    private double yChange = 0;
    private boolean ai;

    public Paddle(Pong pong, double x, double y, double cWidth, double cHeight, boolean ai) {
        this.pong = pong;
        this.cWidth = cWidth;
        this.cHeight = cHeight;
        this.x = x;
        this.y = y;
        this.ai = ai;
    }
    public void move(double direction, boolean down) {
        if (down == true) {
            yChange = speed * direction;
        }
        else {
            yChange = 0;
        }
    }
    public void update() {
        if (ai) {
            int direction;
            Ball ball = pong.getBall();
            if (ball.getXPos() > cWidth/2) {
                if (ball.getYPos() < y) {
                    direction = -1;
                }
                else if (ball.getYPos() > y + height) {
                    direction = 1;
                }
                else {
                    direction = 0;
                }
                yChange = speed * direction;
            }
        }
        y += yChange;
        if (y <= 0) {
            y = 0;
        }
        else if (y >= cHeight - height) {
            y = cHeight - height;
        }
    }
    public void paint(GraphicsContext pen) {
        pen.setFill(Color.WHITE);
        pen.fillRect(x, y, width, height);
    }

    public Bounds getBounds() {
        Rectangle rect = new Rectangle(x, y, width, height);
        Bounds rectBounds = rect.getBoundsInParent();
        return rectBounds;
    }
}
>>>>>>> be031410bcf2b15df5bdcea65c82c9ae2a256fbc
