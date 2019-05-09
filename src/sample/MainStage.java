package sample;

import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.Scenes.MenuScene;

public class MainStage extends Stage {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 545;

    MainStage() {
        setResizable(false);
        sizeToScene();
        setTitle("Tetris");
        getIcons().add(new Image("file:images/video-game.png"));
        setScene(new Scene(new MenuScene(this), WIDTH, HEIGHT));
        setOnCloseRequest(t -> System.exit(0));
        show();
    }

    public void changeScene(Parent parent) {
        setScene(new Scene(parent, WIDTH, HEIGHT));
    }
}
