package sample.Scenes;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import sample.MainStage;

import java.io.File;

public class BaseScene extends Parent implements Runnable {
    public MainStage parent;
    private final int DELAY = 200;
    private boolean isWorking;

    BaseScene(MainStage parent) {
        this.parent = parent;
        setFocusTraversable(true);
    }

    protected BackgroundImage loadBackround() {
        File file = new File("dark-grey-background-texture.jpg");
        Image img = new Image(file.getAbsoluteFile().toURI().toString());
        return new BackgroundImage(img,
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