package sample.Scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sample.MainStage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MenuScene extends BaseScene implements InitializeScene {
    private VBox vBox;
    private Button[] buttons;

    public MenuScene(MainStage parent) {
        super(parent);
        vBox = new VBox();
        buttons = new Button[]{
                new Button("Play"),
                new Button("Settings"),
                new Button("Help"),
                new Button("Exit")};
        setProperties();
        listener();
        getChildren().add(vBox);
        start();
    }

    @Override
    public void listener() {
        buttons[0].setOnAction(event -> {
            stop();
            vBox.getChildren().clear();
            parent.changeScene(new GameScene(parent));
        });
        buttons[1].setOnAction(event -> {
            stop();
            vBox.getChildren().clear();
            parent.changeScene(new SettingsScene(parent));
        });
        buttons[2].setOnAction(event -> {
            stop();
            vBox.getChildren().clear();
            parent.changeScene(new HelpScene(parent));
        });
        buttons[3].setOnAction(event -> System.exit(0));
    }

    @Override
    public void setProperties() {
        vBox.setBackground(new Background(loadBackround()));
        vBox.setPrefSize(parent.WIDTH, parent.HEIGHT);
        vBox.setAlignment(Pos.BASELINE_CENTER);
        vBox.setPadding(new Insets(30, 0, 30, 0));
        vBox.setSpacing(10);
        for (Button button : buttons) {
            button.setFont(loadFont());
            button.setTextFill(Color.LIGHTGRAY);
            button.setStyle("-fx-background-color: transparent;");
        }
        vBox.getChildren().addAll(buttons);
    }

    private Font loadFont() {
        Font font = null;
        try {
            font = Font.loadFont(new FileInputStream(new File("prstartk.ttf").getPath()), 32);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return font;
    }
}