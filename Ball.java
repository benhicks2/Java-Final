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
