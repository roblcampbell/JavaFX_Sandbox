package examples;
import java.util.Random;

import fxutils.FPSCounter;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Simple starfield which spits out rectangles from center of screen and rotates them as they travel.
 */
public class SimpleStarfieldExample extends Application {
    private static final int STAR_COUNT = 500;
    // how many degrees to rotate per update
    private int rotationSpeed = 5;
    private int screenWidth = 640;
    private int screenHeight = 480;

    private final Rectangle[] nodes = new Rectangle[STAR_COUNT];
    private final double[] angles = new double[STAR_COUNT];
    private final long[] start = new long[STAR_COUNT];
    private FPSCounter fpsCounter;

    private final Random random = new Random();

    @Override
    public void start(final Stage primaryStage) {
        for (int i=0; i<STAR_COUNT; i++) {
            nodes[i] = new Rectangle(15, 15, Color.WHITE);
            angles[i] = 2.0 * Math.PI * random.nextDouble();
            start[i] = random.nextInt(2000000000);
        }

        Group rootGroup = new Group(nodes);
        final Scene scene = new Scene(rootGroup, screenWidth, screenHeight, Color.BLACK);
        fpsCounter = new FPSCounter(screenWidth - 90, 30, 20, Color.AQUA);
        rootGroup.getChildren().add(fpsCounter);
        primaryStage.setScene(scene);
        primaryStage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                fpsCounter.updateTime(now);
                final double width = 0.5 * primaryStage.getWidth();
                final double height = 0.5 * primaryStage.getHeight();
                final double radius = Math.sqrt(2) * Math.max(width, height);
                for (int i=0; i<STAR_COUNT; i++) {
                    final Node node = nodes[i];
                    final double angle = angles[i];
                    final long t = (now - start[i]) % 2000000000;
                    final double d = t * radius / 2000000000.0;
                    node.setTranslateX(Math.cos(angle) * d + width);
                    node.setTranslateY(Math.sin(angle) * d + height);
                    node.setRotate((node.getRotate() + rotationSpeed) % 360);
                }
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}