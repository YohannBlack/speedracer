package Controler;

import Model.*;
import View.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controler implements KeyListener {

    private Game game; // Notre partie
    private View view; // Notre vue


    public Controler(Game game, View view){
        this.game = game;
        this.view = view;
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                game.setTrueLeft();
                break;

            case KeyEvent.VK_RIGHT :
                game.setTrueRight();
                break;
        }
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                game.setFalseLeft();
                break;

            case KeyEvent.VK_RIGHT :
                game.setFalseRight();
                break;
        }
    }
}
