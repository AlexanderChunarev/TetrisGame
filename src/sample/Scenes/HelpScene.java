package sample.Scenes;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sample.MainStage;

public class HelpScene extends BaseScene implements InitializeScene{
    private Label label;
    private Button back;
    Pane pane;

    HelpScene(MainStage parent) {
        super(parent);
        pane = new Pane();
        label = new Label();
        back = new Button("Back");
        setProperties();
        getChildren().add(pane);
        setDescription();

    }

    private void setDescription() {
        label.setWrapText(true);
        label.setPrefSize(300,200);
        label.setText("Random tetramino figures fall from" +
                " the top into a rectangular glass" +
                " with a width of 10 and a height of" +
                " 20 cells. In the flight, the player" +
                " can rotate the figure and move it" +
                " horizontally. The figure flies until" +
                " it stumbles on another figurine or" +
                " bottom of the glass. If this is filled" +
                " with a horizontal row of 10 cages, it" +
                " is removed that it is above it, falls" +
                " down to one cage");
    }

    @Override
    public void listener() {

    }

    @Override
    public void setProperties() {
        pane.setBackground(new Background(loadBackround()));
        pane.setPrefSize(MainStage.WIDTH,MainStage.HEIGHT);
        label.setFont(Font.font("Verdana", 14));
        label.setTextFill(Color.WHITE);
        label.setTranslateX(50);
        label.setTranslateY(20);
        back.setTranslateX(180);
        back.setTranslateY(220);
        pane.getChildren().addAll(label, back);
    }
}