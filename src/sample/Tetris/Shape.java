package sample.Tetris;


import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Shape {
    private int x = 90, y = 0;
    private ArrayList<Rectangle> figure = new ArrayList<>();
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

    void stepDown() {
        for (Rectangle rectangle : figure) {
            System.out.println(rectangle.getY());
            rectangle.setY(rectangle.getY() + BLOCK_SIZE);
        }
    }

    void stepLeft() {
        for (Rectangle rectangle : figure) {
            rectangle.setX(rectangle.getX() - BLOCK_SIZE);
        }
    }

    void stepRight() {
        for (Rectangle rectangle : figure) {
            rectangle.setX(rectangle.getX() + BLOCK_SIZE);
        }
    }

    void drop() {
        stepDown();
    }

    void getRotatedTetromino(int[][] currentTetromino) {
        if (currentTetromino.length == 3) {
            this.x = (int) figure.get(0).getX() - BLOCK_SIZE;
        } else {
            this.x = (int) figure.get(0).getX();
        }
        this.y = (int) figure.get(0).getY();
        figure.clear();
        initializeShape();
    }

    void initializeShape() {
        for (int x = 0; x < currTetromino.length; x++) {
            for (int y = 0; y < currTetromino[0].length; y++) {
                if (currTetromino[x][y] == 1) {
                    Rectangle block = new Rectangle(this.x + 30 * y, this.y + 30 * x, 28, 28);
                    block.setFill(new ImagePattern(currBlockImage));
                    block.setArcHeight(5);
                    block.setArcWidth(5);
                    figure.add(block);
                }
            }
        }
    }

    boolean isTouchWall(Rectangle[][] glass, String keyName) {
        for (Rectangle rectangle : figure) {
            if (keyName.equals("LEFT") && (rectangle.getX() == 0
                    || glass[(int) (rectangle.getY() / BLOCK_SIZE)][(int) (rectangle.getX() / BLOCK_SIZE) - 1] != null)) {
                return true;
            }
            if (keyName.equals("RIGHT") && (rectangle.getX() == 270
                    || glass[(int) (rectangle.getY() / BLOCK_SIZE)][(int) (rectangle.getX() / BLOCK_SIZE) + 1] != null)) {
                return true;
            }

        }
        return false;
    }

    boolean isTouchFloor(Rectangle[][] glass) {
        for (Rectangle rectangle : figure) {
            if (rectangle.getY() / BLOCK_SIZE == glass.length - 1
                    || glass[(int) rectangle.getY() / BLOCK_SIZE][(int) rectangle.getX() / BLOCK_SIZE] != null) {
                return true;
            }
        }
        return false;
    }

    void leaveOnTheFloor(Rectangle[][] glass) {
        for (Rectangle rectangle : figure) {
            glass[(int) rectangle.getY() / BLOCK_SIZE - 1][(int) rectangle.getX() / BLOCK_SIZE] = rectangle;
        }
    }

    void paint(Pane pane) {
        for (Rectangle rectangle : figure) {
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
