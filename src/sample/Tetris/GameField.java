package sample.Tetris;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class GameField extends Pane {
    private static final int AMOUNT_OF_BLOCKS = 14;
    private Shape shape = new Shape();
    private Rectangle[][] glass = new Rectangle[AMOUNT_OF_BLOCKS][10];

    public GameField() {
        shape.paint(this);
    }

    public void update() {
        if (shape.isTouchFloor(glass)) {
            shape.leaveOnTheFloor(glass);
            shape = new Shape();
            shape.paint(this);
        }
        shape.stepDown();
    }

    public void paint() {
        shape.paint(this);
    }

}
