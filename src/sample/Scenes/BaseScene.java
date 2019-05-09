package sample.Scenes;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import sample.MainStage;

public class BaseScene extends Parent implements Runnable {
    public MainStage parent;
    private int DELAY = 500;
    private boolean isWorking;

    BaseScene(MainStage parent) {
        this.parent = parent;
        setFocusTraversable(true);
    }

    public int getDELAY() {
        return DELAY;
    }

    public void setDELAY(int DELAY) {
        this.DELAY = DELAY;
    }

    protected BackgroundImage loadBackround() {
        Image image = new Image(getClass().getResourceAsStream("/resources/dark-grey-background-texture.png"));
        return new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
    }

    protected void start() {
        isWorking = true;
        new Thread(this).start();

    }

    protected void stop() {
        isWorking = false;
    }

    protected void update() {

    }

    @Override
    public void run() {
        while (isWorking) {
            try {
                update();
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}