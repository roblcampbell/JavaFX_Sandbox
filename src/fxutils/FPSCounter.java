package fxutils;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Simple FPS Counter Node to place on screen. Just specify location and text size/color and call its
 *  updateTime() method in the animationTimer's handle() method
 */
public class FPSCounter extends Text {
    //used to store the current time to calculate fps
    private long currentTime = 0;
    //used to store the last time to calculate fps
    private long lastTime = 0;
    //fps counter
    private int fps = 0;
    //acumulated difference between current time and last time
    private double delta = 0;
    //one second in nanoseconds
    private final long ONE_SECOND = 1000000000;

    private double rotationSpeed = 3;

    public FPSCounter(int x, int y, int fontSize, Color textColor) {
        setTranslateX(x);
        setTranslateY(y);
        setFill(textColor);
        setFont(new Font(fontSize));
    }

    public void updateTime(long now) {
        if (lastTime == 0) {
            lastTime = System.nanoTime();
        }
        currentTime = now;
        fps++;
        delta += currentTime-lastTime;

        if(delta > ONE_SECOND){
            setText("FPS : " + fps);
            delta -= ONE_SECOND;
            fps = 0;
        }
        lastTime = currentTime;

        handleAnimation();
    }

    private void handleAnimation() {
        setRotate((getRotate() + rotationSpeed) % 360);
    }
}
