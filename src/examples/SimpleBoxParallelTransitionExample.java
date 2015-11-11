package examples;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This example shows a simple box being translated in parallel to travel, rotate, change color, and scale
 *   using the built in FX animation. It will reverse back to its starting point automatically.
 */
public class SimpleBoxParallelTransitionExample extends Application {
    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 500, 500, Color.BLACK);
        Rectangle r = new Rectangle(0, 0, 250, 250);
        r.setFill(Color.BLUE);
        root.getChildren().add(r);

        int duration = 3000;

        TranslateTransition translate =
                new TranslateTransition(Duration.millis(duration));
        translate.setToX(390);
        translate.setToY(390);

        FillTransition fill = new FillTransition(Duration.millis(duration));
        fill.setToValue(Color.RED);

        RotateTransition rotate = new RotateTransition(Duration.millis(duration));
        rotate.setToAngle(360);

        ScaleTransition scale = new ScaleTransition(Duration.millis(duration));
        scale.setToX(0.1);
        scale.setToY(0.1);

        ParallelTransition transition = new ParallelTransition(r,
                translate, fill, rotate, scale);
        transition.setCycleCount(Timeline.INDEFINITE);
        transition.setRate(1);
        transition.setAutoReverse(true);
        transition.play();

        stage.setTitle("JavaFX Scene Graph Demo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
