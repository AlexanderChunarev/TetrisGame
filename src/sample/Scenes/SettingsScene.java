package sample.Scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sample.MainStage;

import java.io.*;
import java.util.Properties;

public class SettingsScene extends BaseScene implements InitializeScene{
    private GridPane gridPane = new GridPane();
    private Button backToMEnu = new Button("Back");
    private Label[] labels = new Label[]{
            new Label("Left:"),
            new Label("Right:"),
            new Label("Rotate:"),
            new Label(("Drop:"))};
    private TextField[] textFields = new TextField[]{
            new TextField(),
            new TextField(),
            new TextField(),
            new TextField()};

    SettingsScene(MainStage parent) {
        super(parent);
        for (int i = 0; i < 4; i++) {
            gridPane.add(labels[i], 0, i + 1);
            gridPane.add(textFields[i], 1, i + 1);
        }
        gridPane.add(backToMEnu, 1, 5);
        getChildren().add(gridPane);
        setProperties();
        listener();
        loadSettings();
    }

    @Override
    public void listener() {
        backToMEnu.setOnAction(event -> {
            setUserSettings();
            parent.changeScene(new MenuScene(parent));
        });
        for (int i = 0; i < textFields.length; i++) {
            int index = i;
            textFields[i].setOnKeyReleased(event -> {
                textFields[index].setText(String.valueOf(event.getCode()));
            });
        }
    }

    @Override
    public void setProperties() {
        gridPane.setPrefSize(parent.WIDTH, parent.HEIGHT);
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setStyle("-fx-background-color: #383838;");
        gridPane.setVgap(20);
        gridPane.setHgap(20);
        for (Label label : labels) {
            label.setFont(Font.font("Verdana", 14));
            label.setTextFill(Color.WHITE);
        }
        for (TextField textField : textFields) {
            textField.setAlignment(Pos.CENTER);
            textField.setStyle("-fx-display-caret: false;");
        }
    }

    private void setUserSettings() {
        try {
            Properties props = new Properties();
            OutputStream out = new FileOutputStream(new File("UserSettings"));
            if (textFields[0].getText() != null) {
                props.setProperty("leftKey", textFields[0].getText());
            }
            if (textFields[1].getText() != null) {
                props.setProperty("rightKey", textFields[1].getText());
            }
            if (textFields[2].getText() != null) {
                props.setProperty("rotateKey", textFields[2].getText());
            }
            if (textFields[3].getText() != null) {
                props.setProperty("dropKey", textFields[3].getText());
            }
            props.store(out, "User settings");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSettings() {
        Properties prop = new Properties();
        InputStream input;
        try {
            input = new FileInputStream("UserSettings");
            prop.load(input);
            textFields[0].setText(prop.getProperty("leftKey"));
            textFields[1].setText(prop.getProperty("rightKey"));
            textFields[2].setText(prop.getProperty("rotateKey"));
            textFields[3].setText(prop.getProperty("dropKey"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
