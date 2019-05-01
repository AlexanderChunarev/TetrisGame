package sample.Tetris;


import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import sample.MatrixOperations.MatrixOperations;

import java.util.ArrayList;
import java.util.Random;

public class Shape {
    private int x = 120, y = 0;
    private ArrayList<Rectangle> shape = new ArrayList<>();
    private final int BLOCK_SIZE = 30;
    private Image currBlockImage;
    private int[][] currTetromino;
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
        currTetromino = getRandomTetromino();
        initializeShape();
    }

    public int getX() {
        return x / BLOCK_SIZE;
    }

    public int getY() {
        return y / BLOCK_SIZE;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int[][] getCurrTetromino() {
        return currTetromino;
    }

    public void setCurrTetromino(int[][] currTetromino) {
        this.currTetromino = currTetromino;
    }

    ArrayList<Rectangle> getShape() {
        return shape;
    }

    void stepDown() {
        for (Rectangle rectangle : shape) {
            rectangle.setY(rectangle.getY() + BLOCK_SIZE);
        }
    }

    void stepSide(int direction) {
        for (Rectangle shape : shape) {
            shape.setX(shape.getX() + direction * BLOCK_SIZE);
        }
    }

    void initializeShape() {
        for (int x = 0; x < currTetromino.length; x++) {
            for (int y = 0; y < currTetromino[0].length; y++) {
                if (currTetromino[x][y] == 1) {
                    Rectangle block = new Rectangle(this.x + 30 * y, this.y + 30 * x, 28, 28);
                    block.setFill(new ImagePattern(currBlockImage));
                    block.setArcHeight(5);
                    block.setArcWidth(5);
                    shape.add(block);
                }
            }
        }
    }

    void paint(Pane pane) {
        for (Rectangle rectangle : shape) {
            pane.getChildren().add(rectangle);
        }
    }

    private int[][] getRandomTetromino() {
        switch (rand.nextInt(7)) {
            case 0:
                currBlockImage = new Image("file:images/greenBlock.png");
                return TShape;
            case 1:
                currBlockImage = new Image("file:images/blueBlock.png");
                return LShape;
            case 2:
                currBlockImage = new Image("file:images/orangeBlock.png");
                return ZShape;
            case 3:
                currBlockImage = new Image("file:images/redBlock.png");
                return SShape;
            case 4:
                currBlockImage = new Image("file:images/greenBlock.png");
                return LineShape;
            case 5:
                currBlockImage = new Image("file:images/fioletBlock.png");
                return SquareShape;
            case 6:
                currBlockImage = new Image("file:images/blueBlock.png");
                return JShape;
        }
        return null;
    }
}
