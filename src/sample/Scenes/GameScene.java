package sample.Scenes;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import sample.Tetris.GameField;
import sample.MainStage;

import java.io.File;

public class GameScene extends BaseScene implements InitializeScene {
    private BorderPane rootPane = new BorderPane();
    private VBox menuPanel = new VBox();
    private GameField gameField = new GameField();
    private Button[] buttons = new Button[]{
            new Button("Play"),
            new Button("Pause"),
            new Button("Back")};

    GameScene(MainStage parent) {
        super(parent);
        getChildren().add(rootPane);
        setProperties();
        listener();
        start();
    }

    private BackgroundImage loadBackround() {
        File file = new File("dark-grey-background-texture.jpg");
        Image img = new Image(file.getAbsoluteFile().toURI().toString());
        return new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
    }

    @Override
    public void update(long tick) {
        Platform.runLater(() -> gameField.update());
    }

    @Override
    public void listener() {
        buttons[0].setOnAction(event -> {});
        buttons[1].setOnAction(event -> {});
        buttons[2].setOnAction(event -> {
            parent.changeScene(new MenuScene(parent));
            stop();
        });
    }

    @Override
    public void setProperties() {
        rootPane.setPrefSize(parent.WIDTH, parent.HEIGHT);
        rootPane.setBackground(new Background(loadBackround()));
        rootPane.setPadding(new Insets(15));
        gameField.setPrefSize(298, 420);
        gameField.setStyle("-fx-background-color: rgba(0, 100, 100, 0.2); -fx-background-radius: 5;");
        menuPanel.setPadding(new Insets(0));
        menuPanel.getChildren().addAll(buttons);
        rootPane.setLeft(gameField);
        rootPane.setRight(menuPanel);
    }
}
