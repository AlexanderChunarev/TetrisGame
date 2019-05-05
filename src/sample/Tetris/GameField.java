package sample.Tetris;

import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import sample.MatrixOperations.MatrixOperations;

import java.io.*;
import java.util.Properties;

public class GameField extends Pane {
    static final int GLASS_HEIGHT = 20;
    static final int GLASS_WIDTH = 10;
    private Shape currShape;
    private Rectangle[][] glass;
    private int score;
    private boolean gameOver;

    public GameField() {
        setFocusTraversable(true);
        glass = new Rectangle[GLASS_HEIGHT][GLASS_WIDTH];
        currShape = new Shape();
        currShape.paint(this);
    }

    public int getScore() {
        return score;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void update() {
        tryMoveDown();
        if (isFilled()) {
            removeFilledRow();
            repaint();
        }
    }

    private void tryMoveDown() {
        if (isTouchFloor()) {
            if (!isGameOver()) {
                leaveOnTheFloor();
            } else {
                gameOver = true;
                return;
            }
            currShape = new Shape();
            currShape.paint(this);
        } else {
            currShape.stepDown();
        }
    }

    private void tryMoveSide(String keyName, int direction) {
        if (isTouchWall(keyName)) {
            currShape.stepSide(direction);
        }
    }

    private boolean isTouchWall(String keyName) {
        for (Rectangle block : currShape.getShape()) {
            int x = (int) (block.getY() / 25 - 1);
            int y = (int) (block.getX() / 25);
            if (x < 0) {
                x = (int) (block.getY() / 25);
            }
            if (keyName.equals("LEFT") && (block.getX() == 0
                    || glass[x][y - 1] != null)) {
                return false;
            }
            if (keyName.equals("RIGHT") && (block.getX() / 25 == GLASS_WIDTH - 1
                    || glass[x][y + 1] != null)) {
                return false;
            }
        }
        return true;
    }

    private boolean isGameOver() {
        for (Rectangle block : currShape.getShape()) {
            if (block.getY() / 25 - 1 < 0) {
                return true;
            }
        }
        return false;
    }

    private boolean isTouchFloor() {
        for (Rectangle block : currShape.getShape()) {
            if (block.getY() / 25 == GLASS_HEIGHT
                    || glass[(int) block.getY() / 25][(int) block.getX() / 25] != null) {
                return true;
            }
        }
        return false;
    }

    private boolean isWrongRotate() {
        for (int x = 0; x < currShape.getCurrShapeMask().length; x++) {
            for (int y = 0; y < currShape.getCurrShapeMask()[0].length; y++) {
                if (currShape.getCurrShapeMask()[x][y] == 1) {
                    if (y + currShape.getY() / 25 < 0 || y + currShape.getY() / 25 > GLASS_HEIGHT - 1) return true;
                    if (x + currShape.getX() / 25 < 0 || x + currShape.getX() / 25 > GLASS_WIDTH - 1) return true;
                    if (glass[y + currShape.getY() / 25][x + currShape.getX() / 25] != null) return true;
                }
            }
        }
        return false;
    }

    private void leaveOnTheFloor() {
        for (Rectangle block : currShape.getShape()) {
            glass[(int) block.getY() / 25 - 1][(int) block.getX() / 25] = block;
        }
    }

    private void repaint() {
        getChildren().clear();
        paintComponent();
    }

    private void paintComponent() {
        for (int x = 0; x < glass.length; x++) {
            for (int y = 0; y < glass[0].length; y++) {
                if (glass[x][y] != null) {
                    getChildren().add(glass[x][y]);
                }
            }
        }
        currShape.paint(this);
    }

    private boolean isFilled() {
        for (int i = 0; i < glass.length; i++) {
            int count = MatrixOperations.getCount(glass, i);
            if (count == glass[0].length) {
                return true;
            }
        }
        return false;
    }

    private void removeFilledRow() {
        for (int i = 0; i < glass.length; i++) {
            int count = MatrixOperations.getCount(glass, i);
            if (count == glass[0].length) {
                moveGlassDown(i);
                score += 100;
            }
        }
    }

    private void moveGlassDown(int pos) {
        for (int i = pos; i > 0; i--) {
            for (int j = 0; j < glass[0].length; j++) {
                glass[i][j] = glass[i - 1][j];
                if (glass[i][j] != null) {
                    glass[i][j].setY(glass[i][j].getY() + 25);
                }

            }
        }
    }

    private void rotate() {
        int[][] testShape = MatrixOperations.rotate(currShape.getCurrShapeMask());
        for (Rectangle block : currShape.getShape()) {
            if (block.getX() == currShape.getX()) {
                currShape.setX((int) block.getX());
                break;
            }
        }
        currShape.setY(currShape.getY());
        if (!isWrongRotate()) {
            currShape.setCurrShapeMask(testShape);
            currShape.getShape().clear();
            currShape.initializeShape();
        }
    }


    public void gameController(Parent root) {
        Properties prop = new Properties();
        try {
            InputStream input = new FileInputStream("UserSettings");
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        root.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.valueOf(prop.getProperty("rotateKey")))) {
                rotate();
                repaint();
            }
            if (event.getCode().equals(KeyCode.valueOf(prop.getProperty("leftKey")))) {
                tryMoveSide("LEFT", -1);
            }
            if (event.getCode().equals(KeyCode.valueOf(prop.getProperty("rightKey")))) {
                tryMoveSide("RIGHT", 1);
            }
            if (event.getCode().equals(KeyCode.valueOf(prop.getProperty("dropKey")))) {
                if (!isTouchFloor()) {
                    currShape.stepDown();
                }

            }
        });
    }
}
