package sample;

import javafx.scene.*;
import javafx.stage.Stage;
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
        scene.setRoot(parent);
    }
}
