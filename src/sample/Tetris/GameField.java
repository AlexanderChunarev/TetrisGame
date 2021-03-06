package sample.Tetris;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.util.Properties;

public class GameField extends Pane {
    static final int GLASS_HEIGHT = 20;
    static final int GLASS_WIDTH = 10;
    private Group group = new Group();
    private Shape currShape;
    private Shape nextShape;
    private Rectangle[][] glass;
    private float score;
    private int removedLines;
    private boolean gameOver;
    private Properties prop;

    public GameField() {
        setFocusTraversable(true);
        prop = new Properties();
        try {
            InputStream input = new FileInputStream("src/UserSettings.properties");
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        glass = new Rectangle[GLASS_HEIGHT][GLASS_WIDTH];
        currShape = new Shape();
        nextShape = new Shape();
        currShape.paint(this);
        drawNext();
    }

    public int getRemovedLines() {
        return removedLines;
    }

    public void setRemovedLines(int removedLines) {
        this.removedLines = removedLines;
    }

    public Group getGroup() {
        return group;
    }

    public float getScore() {
        return score;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void update() {
        tryMoveDown();
        removeFilledRow();

    }

    private void drawNext() {
        group.getChildren().clear();
        for (Rectangle tetromino : nextShape.getCurrTetromino()) {
            tetromino.setX(tetromino.getX() - 100);
            group.getChildren().add(tetromino);
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
            currShape = nextShape;
            nextShape = new Shape();
            drawNext();
            for (Rectangle tetromino : currShape.getCurrTetromino()) {
                tetromino.setX(tetromino.getX() + 100);
            }
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
        for (Rectangle block : currShape.getCurrTetromino()) {
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
        for (Rectangle block : currShape.getCurrTetromino()) {
            if (block.getY() / 25 - 1 < 0) {
                return true;
            }
        }
        return false;
    }

    private boolean isTouchFloor() {
        for (Rectangle block : currShape.getCurrTetromino()) {
            if (block.getY() / 25 == GLASS_HEIGHT
                    || glass[(int) block.getY() / 25][(int) block.getX() / 25] != null) {
                return true;
            }
        }
        return false;
    }

    private boolean isWrongRotate() {
        for (int x = 0; x < currShape.getCurrTetrominoMask().length; x++) {
            for (int y = 0; y < currShape.getCurrTetrominoMask()[0].length; y++) {
                if (currShape.getCurrTetrominoMask()[x][y] == 1) {
                    if (y + currShape.getY() / 25 < 0 || y + currShape.getY() / 25 > GLASS_HEIGHT - 1) return true;
                    if (x + currShape.getX() / 25 < 0 || x + currShape.getX() / 25 > GLASS_WIDTH - 1) return true;
                    if (glass[y + currShape.getY() / 25][x + currShape.getX() / 25] != null) return true;
                }
            }
        }
        return false;
    }

    private void leaveOnTheFloor() {
        for (Rectangle block : currShape.getCurrTetromino()) {
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

    private void removeFilledRow() {
        float count, currScore = 0;
        for (int i = 0; i < glass.length; i++) {
            count = 0;
            for (int j = 0; j < glass[0].length; j++) {
                if (glass[i][j] != null) {
                    count++;
                }
            }
            if (count == glass[0].length) {
                moveGlassDown(i);
                currScore += 100;
                removedLines++;
                repaint();
            }
        }
        setScore(currScore);
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

    private void setScore(float currScore) {
        if (currScore == 400) {
            score += currScore * 1.75;
        } else if (currScore == 300) {
            score += currScore * 1.50;
        } else if (currScore == 200) {
            score += currScore * 1.25;
        } else {
            score += currScore;
        }

    }

    private void rotate() {
        currShape.setY(currShape.getY());
        if (!isWrongRotate()) {
            currShape.rotate();
            currShape.getCurrTetromino().clear();
            currShape.initializeShape();
        }
    }

    public void gameController(Parent root) {
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