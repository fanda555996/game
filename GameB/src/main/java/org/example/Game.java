package org.example;

import com.sun.source.tree.UsesTree;
import jdk.jshell.execution.DirectExecutionControl;
import org.example.logic.Coordinates;
import org.example.logic.Direction;
import org.example.logic.Enemy;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.event.*;
import java.security.Key;

public class Game {
    GameLogic logic;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run()  {
                new Game();
            }
        });
    }

    public Game() {
        logic = new GameLogic();
        GameGraphics graphic = new GameGraphics(logic);
        logic.initialize();
        graphic.render(logic);
        boolean isGameOver = false;

        graphic.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    logic.getBall().move(20, Direction.UP);
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    logic.getBall().move(20, Direction.DOWN);
                } else if (e.getKeyCode() == KeyEvent.VK_A) {
                    logic.getBall().move(20, Direction.LEFT);
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    logic.getBall().move(20, Direction.RIGHT);
                    graphic.render(logic);
                }

            }



            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        graphic.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int differenceX = e.getX() - logic.getBall().getX();
                int differenceY = e.getY() - logic.getBall().getY() - graphic.getInsets().top;
                if (differenceX > 0 && differenceX < logic.getBall().getWidth() && differenceY > 0 && differenceY < logic.getBall().getHeight()){
                    logic.movePlayer(Direction.RIGHT);
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        Timer timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logic.update();
                graphic.render(logic);
            }
        });

        timer.start();
        /*
        ;
        while (!isGameOver){
            logic.update();
            graphic.render();
        }
         */
    }
    private void controlledMove(Direction direction) {
        if (!logic.predictBallCollision(direction)){
            logic.movePlayer(direction);

        }
    }

    public GameLogic getLogic() {
        return logic;
    }
}
