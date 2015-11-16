package examples;
import java.awt.*;
import java.util.Random;

import fxutils.FPSCounter;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Simple starfield which spits out rectangles from center of screen and rotates them as they travel.
 */
public class SimpleStarfieldExample extends Application {
    private static final int STAR_COUNT = 400;
    // how many degrees to rotate per update
    private int rotationSpeed = 5;
    private int rectSize = 30;
    private int screenWidth = 1024;
    private int screenHeight = 600;
    private double centerX, centerY;

    private final Rectangle[] nodes = new Rectangle[STAR_COUNT];
    private final double[] angles = new double[STAR_COUNT];
    private final long[] start = new long[STAR_COUNT];
    private final Color[] colors = new Color[] {Color.WHITE, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PINK};
    private FPSCounter fpsCounter;

    private final Random random = new Random();

    @Override
    public void start(final Stage primaryStage) {
        for (int i=0; i<STAR_COUNT; i++) {
            Color color = colors[new Random().nextInt(colors.length - 1)];
            nodes[i] = new Rectangle(rectSize, rectSize, color);
            angles[i] = 2.0 * Math.PI * random.nextDouble();
            start[i] = random.nextInt(2000000000);
        }

        Group rootGroup = new Group(nodes);
        final Scene scene = new Scene(rootGroup, screenWidth, screenHeight, Color.BLACK);
        fpsCounter = new FPSCounter(screenWidth - 90, 30, 20, Color.AQUA);
        rootGroup.getChildren().add(fpsCounter);
        primaryStage.setScene(scene);
        primaryStage.show();

        TranslateTransition tranny1 = new TranslateTransition(Duration.millis(10000), fpsCounter);
        tranny1.setToX(100);
        tranny1.setToY(400);
        tranny1.setAutoReverse(true);
        tranny1.setCycleCount(Timeline.INDEFINITE);
        tranny1.play();
        final double width = 0.5 * primaryStage.getWidth();
        final double height = 0.5 * primaryStage.getHeight();
        final double radius = Math.sqrt(2) * Math.max(width, height);
        centerX = width;
        centerY = height;

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                fpsCounter.updateTime(now);

                for (int i=0; i<STAR_COUNT; i++) {
                    final Node node = nodes[i];
                    final double angle = angles[i];
                    final long t = (now - start[i]) % 2000000000;
                    final double d = t * radius / 2000000000.0;
                    double sizeMult = 1 - (d / 500);
                    node.setTranslateX(Math.cos(angle) * d + centerX);
                    node.setTranslateY(Math.sin(angle) * d + centerY);
                    node.setScaleX(sizeMult);
                    node.setScaleY(sizeMult);
                    node.setRotate((node.getRotate() + rotationSpeed) % 360);
                }
            }
        }.start();
    }

    public static void main(String[] args) {
        System.out.println(java.awt.Toolkit.getDefaultToolkit().getScreenSize());
        System.out.println(Toolkit.getDefaultToolkit().getScreenResolution());
        launch(args);
    }

}