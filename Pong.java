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
    private final int p2Width = 700;
    private final int p2Height = 500;

    private int scoreplayer1 = 0;
    private int scoreplayer2 = 0;
    double textSize = 30;
    String font = "Gotham";
    Text p1Score = new Text(p2Width/2 - 50 - 15, 40, "" + scoreplayer1);
    Text p2Score = new Text(p2Width/2 + 50, 40, "" + scoreplayer2);

    static boolean p1Down = false;
    static boolean p2Down = false;

    Canvas c = new Canvas(p2Width, p2Height);
    private Paddle p1Paddle; //player1 paddle
    private Paddle p2Paddle; //player2 paddle
    private Ball ball;

    public Pong() {
        p1Paddle = new Paddle(this, 20, p2Height/2, p2Width, p2Height, false);
        p2Paddle = new Paddle(this, p2Width - 20 - 30, p2Height/2, p2Width, p2Height, true);
        ball = new Ball(this, p2Width, p2Height);
    }

    public void increaseScore(String paddle) {
        if (paddle == "player1") {
            scoreplayer1++;
            p1Score.setText("" + scoreplayer1);
        }
        else {
            scoreplayer2++;
            p2Score.setText("" + scoreplayer2);
        }
    }
    public Paddle getPlayer(String paddle) {
        if (paddle == "player1") {
            return p1Paddle;
        }
        else {
            return p2Paddle;
        }
    }
    public Ball getBall() {
        return ball;
    }

    @Override
    public void start(Stage primary) {
        GraphicsContext pen = c.getGraphicsContext2D();

        p1Score.setFill(Color.WHITE);
        p2Score.setFill(Color.WHITE);
        p1Score.setFont(Font.font(font, FontWeight.BOLD, textSize));
        p2Score.setFont(Font.font(font, FontWeight.BOLD, textSize));

        BorderPane bp = new BorderPane();
        bp.setCenter(c);

        Scene s = new Scene(bp);
        primary.setScene(s);
        primary.setTitle("Pong");

        bp.getChildren().add(p1Score);
        bp.getChildren().add(p2Score);

        draw(pen);

//detect keypress
        s.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.W) {
                p1Down = true;
                p1Paddle.move(-1, true);
            }
            if (e.getCode() == KeyCode.S) {
                p1Down = true;
                p1Paddle.move(1, true);
            }
        });
        s.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.W || e.getCode() == KeyCode.S) {
                p1Paddle.move(1, false);
            }
        });

        s.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.O) {
                p2Down = true;
                p2Paddle.move(-1, true);
            }
            if (e.getCode() == KeyCode.L) {
                p2Down = true;
                p2Paddle.move(1, true);
            }
        });
        s.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.O || e.getCode() == KeyCode.L) {
                p2Paddle.move(1, false);
            }
        });
//animation
        final long startNanoTime = System.nanoTime();
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                draw(pen);

                ball.update();
                p1Paddle.update();
                p2Paddle.update();

                ball.paint(pen);
                p1Paddle.paint(pen);
                p2Paddle.paint(pen);

            }
        }.start();


        primary.show();
    }
    public void draw(GraphicsContext pen) {
        //background
        pen.setFill(Color.BLACK);
        pen.fillRect(0, 0, p2Width, p2Height);

        pen.setFill(Color.WHITE);
        pen.fillRect(p2Width/2 - 1, 0, 2, p2Height);
        //score
    }

}
