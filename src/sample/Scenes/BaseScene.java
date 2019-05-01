package sample.Scenes;

import javafx.scene.Parent;
import sample.MainStage;

public class BaseScene extends Parent implements Runnable{
    public MainStage parent;
    private final int DELAY = 3000;
    private boolean isWorking;

    BaseScene(MainStage parent) {
        this.parent = parent;
        setFocusTraversable(true);
    }

    protected void start() {
        isWorking = true;
        new Thread(this).start();

    }

    protected void stop() {
        isWorking = false;
    }

    public void update() {

    }

    @Override
    public void run() {
        while (isWorking) {
            try {
                Thread.sleep(DELAY);
                update();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
