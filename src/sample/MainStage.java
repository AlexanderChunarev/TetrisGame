package sample;

import javafx.animation.FadeTransition;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Scenes.MenuScene;

public class MainStage extends Stage {
    private Scene scene;
    public final int WIDTH = 420;
    public final int HEIGHT = 450;

    MainStage() {
        scene = new Scene(new MenuScene(this), WIDTH, HEIGHT);
        setResizable(false);
        sizeToScene();
        setScene(scene);
        setOnCloseRequest(t -> System.exit(0));
        show();
    }

    public void changeScene(Parent parent) {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), parent);
        ft.setFromValue(0.9);
        ft.setToValue(1.0);
        ft.play();
        scene.setRoot(parent);
    }
}
