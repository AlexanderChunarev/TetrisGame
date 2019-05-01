package sample.Tetris;

import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import sample.MatrixOperations.MatrixOperations;

import java.io.*;
import java.util.Properties;

public class GameField extends Pane {
    private static final int GLASS_WIDTH = 13;
    private static final int GLASS_HEIGHT = 10;
    private Shape currShape;
    private Rectangle[][] glass = new Rectangle[GLASS_WIDTH][GLASS_HEIGHT];
    private int score;
    private boolean gameOver;

    public GameField() {
        setFocusTraversable(true);
        currShape = new Shape();
        currShape.paint(this);
        for (Rectangle re : currShape.getShape()) {
            System.out.println(re.getX() + "   " + re.getY());
        }
        currShape.stepSide(-1);
        for (Rectangle re : currShape.getShape()) {
            System.out.println(re.getX() + "   " + re.getY());
        }

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

            //currShape.stepDown();
        } else {
            currShape.stepDown();
        }

    }

    private boolean isTouchWall(String keyName) {
        for (Rectangle rectangle : currShape.getShape()) {
            if (keyName.equals("LEFT") && (rectangle.getX() == 0
                    || glass[(int) (rectangle.getY() / 30) - 1][(int) (rectangle.getX() / 30) - 1] != null)) {
                System.out.println("touch");
                return false;
            }
            if (keyName.equals("RIGHT") && (rectangle.getX() == 270
                    || glass[(int) (rectangle.getY() / 30) - 1][(int) (rectangle.getX() / 30) + 1] != null)) {
                return false;
            }
        }
        return true;
    }

    private boolean isTouchFloor() {
        for (Rectangle rectangle : currShape.getShape()) {
            if (rectangle.getY() / 30 == glass.length
                    || glass[(int) rectangle.getY() / 30][(int) rectangle.getX() / 30] != null) {
                return true;
            }
        }
        return false;
    }

    private boolean isGameOver() {
        for (Rectangle rectangle : currShape.getShape()) {
            if (rectangle.getY() == 0
                    && (glass[(int) rectangle.getY() + 1][(int) rectangle.getX() / 30] != null
                    || glass[(int) rectangle.getY() + 2][(int) rectangle.getX() / 30] != null)) {
                return true;
            }
        }
        return false;
    }


    private void leaveOnTheFloor() {
        for (Rectangle rectangle : currShape.getShape()) {
            glass[(int) rectangle.getY() / 30 - 1][(int) rectangle.getX() / 30] = rectangle;
        }

        for (int i = 0; i < glass.length; i++) {
            for (int j = 0; j < glass[0].length; j++) {
                if (glass[i][j] != null) {
                    System.out.print("1");
                } else {
                    System.out.print("0");
                }
            }
            System.out.println();
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
                    glass[i][j].setY(glass[i][j].getY() + 30);
                }

            }
        }
    }

    private void rotate() {
        int[][] testShape = MatrixOperations.rotate(currShape.getCurrTetromino());
        if (testShape.length == 3) {
            currShape.setX((int) (currShape.getShape().get(0).getX() - 30));
        } else {
            currShape.setX((int) (currShape.getShape().get(0).getX()));
        }
        currShape.setY((int) (currShape.getShape().get(0).getY()));
        if (!isWrongRotate()) {

            currShape.setCurrTetromino(testShape);
            for (int i = 0; i < currShape.getCurrTetromino().length; i++) {
                for (int j = 0; j < currShape.getCurrTetromino()[0].length; j++) {
                    System.out.print(testShape[i][j]);
                }
                System.out.println();
            }
            currShape.getShape().clear();
            currShape.initializeShape();
        }
    }

    private boolean isWrongRotate() {
        for (int x = 0; x < currShape.getCurrTetromino().length; x++) {
            for (int y = 0; y < currShape.getCurrTetromino()[0].length; y++) {
                if (currShape.getCurrTetromino()[x][y] == 1) {
                    if (y + currShape.getY() < 0 || y + currShape.getY() > GLASS_WIDTH - 1) return true;
                    if (x + currShape.getX() < 0 || x + currShape.getX() > GLASS_HEIGHT - 1) return true;
                    if (glass[y + currShape.getY()][x + currShape.getX()] != null) return true;
                }
            }
        }
        return false;
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
                if (isTouchWall("LEFT")) {
                    currShape.stepSide(-1);
                }
            }
            if (event.getCode().equals(KeyCode.valueOf(prop.getProperty("rightKey")))) {
                if (isTouchWall("RIGHT")) {
                    currShape.stepSide(1);
                }
            }
            if (event.getCode().equals(KeyCode.valueOf(prop.getProperty("dropKey")))) {
                while (!isTouchFloor()) {
                    currShape.stepDown();
                }
            }
        });
    }
}
