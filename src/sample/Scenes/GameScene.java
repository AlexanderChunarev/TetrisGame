package sample.Scenes;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import sample.Tetris.GameField;
import sample.MainStage;

import java.io.File;

public class GameScene extends BaseScene implements InitializeScene {
    private BorderPane rootPane = new BorderPane();
    private VBox menuPanel = new VBox();
    private GameField gameField = new GameField();
    private Button[] buttons = new Button[]{
            new Button("New game"),
            new Button("Pause"),
            new Button("Back")};
    private Label score = new Label("Score: 0");

    GameScene(MainStage parent) {
        super(parent);
        getChildren().add(rootPane);
        listener();
        setProperties();
        start();
        gameField.gameController(this);
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
    public void update() {
        Platform.runLater(() -> {
            gameField.update();
            score.setText("Score: " + gameField.getScore());
            if (gameField.getGameOver()) {
                stop();
            }
        });
    }

    @Override
    public void listener() {
        buttons[0].setOnAction(event -> {
            parent.changeScene(new GameScene(parent));
            stop();
        });
        buttons[1].setOnAction(event -> {
            if (buttons[1].getText().equals("Pause")) {
                stop();
                buttons[1].setText("Continue");
            } else {
                start();
                buttons[1].setText("Pause");
            }

        });
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
        gameField.setStyle("-fx-background-color: rgba(0, 100, 100, 0.3); -fx-background-radius: 5;");
        score.setTranslateY(300);
        menuPanel.getChildren().addAll(buttons);
        score.setTextFill(Color.WHITE);
        menuPanel.getChildren().add(score);
        rootPane.setLeft(gameField);
        rootPane.setRight(menuPanel);
    }
}
