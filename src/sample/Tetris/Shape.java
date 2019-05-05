package sample.Tetris;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class Shape {
    private int x, y;
    private ArrayList<Rectangle> shape = new ArrayList<>();
    private final int BLOCK_SIZE = 25;
    private Image currBlockImage;
    private int[][] currShapeMask;
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
        currShapeMask = getRandomShape();
        x = GameField.GLASS_WIDTH / 2 * BLOCK_SIZE - BLOCK_SIZE;
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

    public int[][] getCurrShapeMask() {
        return currShapeMask;
    }

    public void setCurrShapeMask(int[][] currShapeMask) {
        this.currShapeMask = currShapeMask;
    }

    public ArrayList<Rectangle> getShape() {
        return shape;
    }

    void stepDown() {
        for (Rectangle block : shape) {
            block.setY(block.getY() + BLOCK_SIZE);
        }
        setY(getY() + BLOCK_SIZE);
    }

    void stepSide(int direction) {
        for (Rectangle block : shape) {
            block.setX(block.getX() + direction * BLOCK_SIZE);
        }
        setX(getX() + direction * BLOCK_SIZE);
    }

    void initializeShape() {
        for (int x = 0; x < currShapeMask.length; x++) {
            for (int y = 0; y < currShapeMask[0].length; y++) {
                if (currShapeMask[x][y] == 1) {
                    Rectangle block = new Rectangle(this.x + 25 * y, this.y + 25 * x, 24, 24);
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

    private int[][] getRandomShape() {
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
