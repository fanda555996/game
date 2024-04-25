package org.example;

import org.example.logic.*;

import java.awt.*;
import java.util.ArrayList;

public class GameLogic {
    private Ball ball;
    private ArrayList<Enemy> enemies;
    private ArrayList<Wall> walls;
    private final int ENEMY_STEPS = 2;
    private final int BALL_STEPS = 20;

    public GameLogic() {
        this.ball = null;
        this.enemies = new ArrayList<>();
        this.walls = new ArrayList<>();
    }

    public void initialize() {

        ball = new Ball(20, 20, "hodnej.png");

        Enemy enemy1 = new Enemy(350,350, "enemy.png",100);
        Enemy enemy2 = new Enemy(150,250, "enemy.png",100);
        enemies.add(enemy1);
        enemies.add(enemy2);

        Wall wall1 = new Wall(250, 30, 250, 130, Color.BLACK);
        Wall wall2 = new Wall(100, 50, 150, 50, Color.BLACK);
        walls.add(wall1);
        walls.add(wall2);
    }

    public void update() {
        //ball.move(2, Direction.RIGHT);
        for (Enemy enemy: enemies) {
            int differenceX = Math.abs(ball.getCoord().x - enemy.getCoord().x);
            int differenceY = Math.abs(ball.getCoord().y - enemy.getCoord().y);

            if (differenceX > differenceY) {
                // Direction LEFT, RIGHT
                if (ball.getCoord().x - enemy.getCoord().x > 0) {
                    // Direction RIGHT
                    moveEnemy(Direction.RIGHT, enemy);
                } else {
                    // Direction LEFT
                    moveEnemy(Direction.LEFT, enemy);
                }
            } else {
                // Direction UP, DOWN
                if (ball.getCoord().y - enemy.getCoord().y > 0) {
                    // Direction DOWN
                    moveEnemy(Direction.DOWN, enemy);
                } else {
                    // Direction UP
                    moveEnemy(Direction.UP, enemy);
                }
            }
        }
        for (Wall wall: walls) {
            if (ball.isCollided(wall.getRectangle())){
                wall.inactivate();
            }
        }
    }
    public boolean predictBallCollision(Direction direction){
        return predictCollision(direction, ball, BALL_STEPS);
    }
    private boolean predictCollision(Direction direction, Entity entity, int steps) {
        Rectangle moveRectangle = new Rectangle();
        switch (direction) {
            case RIGHT -> {
                moveRectangle = new Rectangle(entity.getX() + steps, entity.getY(), entity.getWidth(), entity.getHeight());
            }
            case LEFT -> {
                moveRectangle = new Rectangle(entity.getX() - steps, entity.getY(), entity.getWidth(), entity.getHeight());
            }
            case UP -> {
                moveRectangle = new Rectangle(entity.getX(), entity.getY() - steps, entity.getWidth(), entity.getHeight());
            }
            case DOWN -> {
                moveRectangle = new Rectangle(entity.getX(), entity.getY() + steps, entity.getWidth(), entity.getHeight());
            }

        }
        for (Wall wall:walls) {
            if (moveRectangle.intersects(wall.getRectangle())){
                return true;
            }
        }
        return false;


    }

    public ArrayList<Enemy> getEnemy() {
        return enemies;
    }

    public Ball getBall() {
        return ball;
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    private void moveEnemy(Direction direction, Enemy enemy){
        if (!predictCollision(direction, enemy, ENEMY_STEPS)) {
            enemy.move(ENEMY_STEPS, direction);
        } else {
            ArrayList<Direction> directions = new ArrayList<>();
            switch (direction) {
                case UP -> {
                    directions.add(Direction.RIGHT);
                    directions.add(Direction.DOWN);
                    directions.add(Direction.LEFT);
                }
                case RIGHT -> {
                    directions.add(Direction.DOWN);
                    directions.add(Direction.LEFT);
                    directions.add(Direction.UP);

                }
                case DOWN -> {
                    directions.add(Direction.LEFT);
                    directions.add(Direction.UP);
                    directions.add(Direction.RIGHT);

                }
                case LEFT -> {
                    directions.add(Direction.UP);
                    directions.add(Direction.RIGHT);
                    directions.add(Direction.DOWN);

                }

            }
            for (Direction testedDirection: directions){
                if (!predictCollision(testedDirection, enemy, ENEMY_STEPS)) {
                    enemy.move(ENEMY_STEPS, testedDirection);
                    break;
                }
            }
        }
    }

    public void movePlayer(Direction direction) {
        ball.move(BALL_STEPS, direction);

    }
}
