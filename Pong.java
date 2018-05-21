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
import javafx.scene.text.Text;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;

public class Pong extends Application {
    private final int cWidth = 700;
    private final int cHeight = 500;

    private int scoreUser = 0;
    private int scoreComputer = 0;
    double textSize = 30;
    String font = "Gotham";
    Text uScore = new Text(cWidth/2 - 50 - 15, 40, "" + scoreUser);
    Text cScore = new Text(cWidth/2 + 50, 40, "" + scoreComputer);

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
            uScore.setText("" + scoreUser);
        }
        else {
            scoreComputer++;
            cScore.setText("" + scoreComputer);
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

        uScore.setFill(Color.WHITE);
        cScore.setFill(Color.WHITE);
        uScore.setFont(Font.font(font, FontWeight.BOLD, textSize));
        cScore.setFont(Font.font(font, FontWeight.BOLD, textSize));

        BorderPane bp = new BorderPane();
        bp.setCenter(c);

        Scene s = new Scene(bp);
        primary.setScene(s);
        primary.setTitle("Pong");

        bp.getChildren().add(uScore);
        bp.getChildren().add(cScore);

        draw(pen);

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

        pen.setFill(Color.WHITE);
        pen.fillRect(cWidth/2 - 1, 0, 2, cHeight);
        //score
    }

}
