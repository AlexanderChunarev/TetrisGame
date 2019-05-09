package sample.Tetris;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class Shape {
    private int x, y;
    private ArrayList<Rectangle> currShape = new ArrayList<>();
    private final int TETROMINO_SIZE = 25;
    private Image currTetrominoImage;
    private int[][] currTetrominoMask;
    private int[][] TShape = {{0, 1, 0},
            {1, 1, 1}};
    private int[][] JShape = {{1, 0, 0},
            {1, 1, 1}};
    private int[][] LShape = {{1, 1, 1},
            {1, 0, 0}};
    private int[][] SShape = {{0, 1, 1},
            {1, 1, 0}};
    private int[][] ZShape = {{1, 1, 0},
            {0, 1, 1}};
    private int[][] SquareShape = {{1, 1},
            {1, 1}};
    private int[][] LineShape = {{1, 1, 1, 1}};
    private Random rand = new Random();

    public Shape() {
        currTetrominoMask = getRandomShape();
        x = GameField.GLASS_WIDTH / 2 * TETROMINO_SIZE - TETROMINO_SIZE;
        y = 0;
        initializeShape();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int[][] getCurrTetrominoMask() {
        return currTetrominoMask;
    }

    public void setCurrTetrominoMask(int[][] currTetrominoMask) {
        this.currTetrominoMask = currTetrominoMask;
    }

    public ArrayList<Rectangle> getCurrTetromino() {
        return currShape;
    }

    void stepDown() {
        for (Rectangle tetromino : currShape) {
            tetromino.setY(tetromino.getY() + TETROMINO_SIZE);
        }
        setY(getY() + TETROMINO_SIZE);
    }

    void stepSide(int direction) {
        for (Rectangle tetromino : currShape) {
            tetromino.setX(tetromino.getX() + direction * TETROMINO_SIZE);
        }
        setX(getX() + direction * TETROMINO_SIZE);
    }

    void initializeShape() {
        for (int x = 0; x < currTetrominoMask.length; x++) {
            for (int y = 0; y < currTetrominoMask[0].length; y++) {
                if (currTetrominoMask[x][y] == 1) {
                    Rectangle tetromino = new Rectangle(this.x + 25 * y, this.y + 25 * x, 24, 24);
                    tetromino.setFill(new ImagePattern(currTetrominoImage));
                    tetromino.setArcHeight(5);
                    tetromino.setArcWidth(5);
                    currShape.add(tetromino);
                }
            }
        }
    }

    void paint(Pane pane) {
        for (Rectangle rectangle : currShape) {
            pane.getChildren().add(rectangle);
        }
    }

    private int[][] getRandomShape() {
        switch (rand.nextInt(7)) {
            case 0:
                currTetrominoImage = new Image(getClass().getResourceAsStream("/resources/green.png"));
                return TShape;
            case 1:
                currTetrominoImage = new Image(getClass().getResourceAsStream("/resources/blue.png"));
                return LShape;
            case 2:
                currTetrominoImage = new Image(getClass().getResourceAsStream("/resources/orange.png"));
                return ZShape;
            case 3:
                currTetrominoImage = new Image(getClass().getResourceAsStream("/resources/red.png"));
                return SShape;
            case 4:
                currTetrominoImage = new Image(getClass().getResourceAsStream("/resources/green.png"));
                return LineShape;
            case 5:
                currTetrominoImage = new Image(getClass().getResourceAsStream("/resources/fiolet.png"));
                return SquareShape;
            case 6:
                currTetrominoImage = new Image(getClass().getResourceAsStream("/resources/orange.png"));
                return JShape;
        }
        return null;
    }
}
