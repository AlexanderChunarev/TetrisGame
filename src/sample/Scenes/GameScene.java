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
import javafx.scene.text.Font;
import sample.Tetris.GameField;
import sample.MainStage;

import java.io.*;
import java.util.Properties;

public class GameScene extends BaseScene implements InitializeScene {
    private BorderPane rootPane;
    private Pane menuPanel;
    private GameField gameField;
    private Button[] buttons;
    private Label score = new Label();
    private Label bestScore = new Label();
    private int bestScoreVal;
    private Properties properties;

    GameScene(MainStage parent) {
        super(parent);
        properties = new Properties();
        rootPane = new BorderPane();
        menuPanel = new Pane();
        gameField = new GameField();
        buttons = new Button[]{
                new Button("New game", loadButtonImage("newGame.png")),
                new Button("Pause", loadButtonImage("pause.png")),
                new Button("Exit", loadButtonImage("ex.png"))};
        try {
            properties.load(new FileInputStream("score.properties"));
            bestScoreVal = Integer.parseInt(properties.getProperty("bestScore"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        score.setText("Score: " + 0);
        bestScore.setText("Best: " + bestScoreVal);
        getChildren().add(rootPane);
        listener();
        setProperties();
        start();
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
            if (gameField.getScore() > bestScoreVal) {
                bestScore.setText("Best: " + gameField.getScore());
            }
            if (gameField.getGameOver()) {
                stop();
                rootPane.setOnKeyPressed(event -> {
                });
                saveScore();
            }
        });
    }

    @Override
    public void listener() {
        buttons[0].setOnMouseClicked(event -> {
            stop();
            saveScore();
            parent.changeScene(new GameScene(parent));
        });
        buttons[1].setOnMouseClicked(event -> {
            if (buttons[1].getText().equals("Pause")) {
                stop();
                rootPane.setOnKeyPressed(event1 -> {
                });
                buttons[1].setText("Continue");
                buttons[1].setGraphic(loadButtonImage("play.png"));
            } else {
                start();
                gameField.gameController(rootPane);
                buttons[1].setText("Pause");
                buttons[1].setGraphic(loadButtonImage("pause.png"));
            }
        });
        buttons[2].setOnMouseClicked(event -> {
            stop();
            saveScore();
            parent.changeScene(new MenuScene(parent));
        });
        gameField.gameController(rootPane);
    }

    private void saveScore() {
        if (gameField.getScore() > bestScoreVal) {
            properties.setProperty("bestScore", String.valueOf(gameField.getScore()));
            try {
                properties.store(new FileOutputStream(new File("score.properties")), "Score");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setProperties() {
        rootPane.setPrefSize(MainStage.WIDTH, MainStage.HEIGHT);
        rootPane.setBackground(new Background(loadBackround()));
        rootPane.setPadding(new Insets(10));
        gameField.setPrefSize(250, 525);
        gameField.setStyle("-fx-background-color: rgba(0, 100, 100, 0.2); -fx-background-radius: 5;");
        score.setTranslateY(MainStage.HEIGHT - 65);
        bestScore.setTranslateY(MainStage.HEIGHT - 45);
        score.setTextFill(Color.WHITE);
        bestScore.setTextFill(Color.WHITE);
        score.setFont(new Font(14));
        bestScore.setFont(new Font(14));
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setTranslateY(i * 45);
            buttons[i].setPrefSize(105, 40);
            buttons[i].setPadding(new Insets(0));
            buttons[i].setContentDisplay(ContentDisplay.LEFT);
            buttons[i].setStyle("-fx-base: #2f3033;" +
                    "-fx-background-radius: 5;" +
                    "-fx-focus-color: transparent;" +
                    "-fx-background-insets: -1.4, 0, 1, 2;");

        }
        menuPanel.getChildren().addAll(buttons);
        menuPanel.getChildren().addAll(score, bestScore);
        rootPane.setLeft(gameField);
        rootPane.setRight(menuPanel);
    }
}
