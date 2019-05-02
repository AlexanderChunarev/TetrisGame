package sample.Scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sample.MainStage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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
        vBox.getChildren().addAll(buttons);
        setProperties();
        listener();
        getChildren().add(vBox);
        start();
    }

    @Override
    public void listener() {
        buttons[0].setOnAction(event -> {

            parent.changeScene(new GameScene(parent));
        });
        buttons[1].setOnAction(event -> {
            vBox.getChildren().clear();
            parent.changeScene(new SettingsScene(parent));
        });
        buttons[2].setOnAction(event -> parent.changeScene(new HelpScene(parent)));
        buttons[3].setOnAction(event -> System.exit(0));
    }

    @Override
    public void setProperties() {
        vBox.setPrefSize(parent.WIDTH, parent.HEIGHT);
        vBox.setAlignment(Pos.BASELINE_CENTER);
        vBox.setStyle("-fx-background-color: #383838;");
        vBox.setPadding(new Insets(30, 0, 30, 0));
        vBox.setSpacing(10);
        for (Button button : buttons) {
            button.setFont(loadFont());
            button.setTextFill(Color.LIGHTGRAY);
            button.setStyle("-fx-background-color: transparent;");
        }
    }

    private Font loadFont() {
        Font font = null;

        try {
            font = Font.loadFont(new FileInputStream(new File("prstartk.ttf").getPath()), 28);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return font;
    }
}