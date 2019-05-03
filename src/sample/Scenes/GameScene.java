package sample.Scenes;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import sample.Tetris.GameField;
import sample.MainStage;

public class GameScene extends BaseScene implements InitializeScene {
    private BorderPane rootPane;
    private Pane menuPanel;
    private GameField gameField;
    private Button[] buttons;
    private Label score = new Label("Score: 0");

    GameScene(MainStage parent) {
        super(parent);
        rootPane = new BorderPane();
        menuPanel = new Pane();
        gameField = new GameField();
        buttons = new Button[]{
                new Button("New game", loadButtonImage("play.png")),
                new Button("Pause", loadButtonImage("pause.png")),
                new Button("Exit")};
        getChildren().add(rootPane);
        listener();
        setProperties();
        start();
        gameField.gameController(this);
    }

    private ImageView loadButtonImage(String imageName) {
        return new ImageView(
                new Image("file:images/" + imageName));
    }

    @Override
    public void update() {
        Platform.runLater(() -> {
            gameField.setFocusTraversable(true);
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
            stop();
            parent.changeScene(new GameScene(parent));
        });
        buttons[1].setOnAction(event -> {
            if (buttons[1].getText().equals("Pause")) {
                stop();
                buttons[1].setText("Continue");
                buttons[1].setGraphic(loadButtonImage("play.png"));
            } else {
                start();
                buttons[1].setText("Pause");
                buttons[1].setGraphic(loadButtonImage("pause.png"));
            }
        });
        buttons[2].setOnAction(event -> {
            parent.changeScene(new MenuScene(parent));
            stop();
        });
    }

    @Override
    public void setProperties() {
        rootPane.setPrefSize(MainStage.WIDTH, MainStage.HEIGHT);
        rootPane.setBackground(new Background(loadBackround()));
        rootPane.setPadding(new Insets(10));
        gameField.setPrefSize(250, 525);
        gameField.setStyle("-fx-background-color: rgba(0, 100, 100, 0.2); -fx-background-radius: 5;");
        score.setTranslateY(300);
        score.setTextFill(Color.WHITE);
        buttons[0].setTranslateY(0);
        buttons[1].setTranslateY(40);
        buttons[2].setTranslateY(80);
        for (Button button:buttons) {
            button.setPrefSize(105, 40);
            button.setPadding(new Insets(0));
            button.setContentDisplay(ContentDisplay.LEFT);
            button.setStyle("-fx-base: #2f3033;" +
                    "-fx-background-radius: 5;" +
                    "-fx-focus-color: transparent;" +
                    "-fx-background-insets: -1.4, 0, 1, 2;");
        }
        menuPanel.getChildren().addAll(buttons);
        menuPanel.getChildren().add(score);
        rootPane.setLeft(gameField);
        rootPane.setRight(menuPanel);
    }
}
