import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class Pong extends Application {
    private final int cWidth = 700;
    private final int cHeight = 500;

    private int scoreUser = 0;
    private int scoreComputer = 0;

    static boolean down = false;

    Canvas c = new Canvas(cWidth, cHeight);
    private Paddle uPaddle; //user paddle
    private Paddle cPaddle; //computer paddle
    private Ball ball;

    public Pong() {
        uPaddle = new Paddle(this, 20, cHeight/2, cWidth, cHeight, false);
        cPaddle = new Paddle(this, cWidth - 20 - 30, cHeight/2, cWidth, cHeight, true);
        ball = new Ball(this, cWidth, cHeight);
    }

    public void increaseScore(String paddle) {
        if (paddle == "user") {
            scoreUser++;
        }
        else {
            scoreComputer++;
        }
    }
    public Paddle getPlayer(String paddle) {
        if (paddle == "user") {
            return uPaddle;
        }
        else {
            return cPaddle;
        }
    }
    public Ball getBall() {
        return ball;
    }

    @Override
    public void start(Stage primary) {
        GraphicsContext pen = c.getGraphicsContext2D();

        draw(pen);

        BorderPane bp = new BorderPane();
        bp.setCenter(c);

        Scene s = new Scene(bp);
        primary.setScene(s);
        primary.setTitle("Pong");

//detect keypress
        s.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP) {
                down = true;
                uPaddle.move(-1, true);
            }
            if (e.getCode() == KeyCode.DOWN) {
                down = true;
                uPaddle.move(1, true);
            }
        });
        s.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN) {
                uPaddle.move(1, false);
            }
        });

//animation
        final long startNanoTime = System.nanoTime();
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                draw(pen);

                ball.update();
                uPaddle.update();
                cPaddle.update();

                ball.paint(pen);
                uPaddle.paint(pen);
                cPaddle.paint(pen);

            }
        }.start();


        primary.show();
    }
    public void draw(GraphicsContext pen) {
        //background
        pen.setFill(Color.BLACK);
        pen.fillRect(0, 0, cWidth, cHeight);
        //score
        pen.setFill(Color.WHITE);
        pen.fillText("" + scoreUser, cWidth/2 - 50, 20);
        pen.fillText("" + scoreComputer, cWidth/2 + 50, 20);
    }

}
