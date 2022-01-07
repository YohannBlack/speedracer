package View;

import Controler.Controler;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class View {

    public static JFrame frame; // Notre frame
    private ViewWorld vWorld; // Nous allons draw sur vWorld
    private Game game; // Notre partie

    public View(Game game) throws AWTException {
        this.game = game;
        frame = new JFrame("Speed Racer");
        vWorld = new ViewWorld(this.game);

        frame.setLocation(200, 100);
        frame.addKeyListener(new Controler(game, this));

        JPanel subPanel = new JPanel();
        subPanel.add(vWorld);

        frame.add(subPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }


    /**
     *
     * @return un ViewWorld, representation graohique de notre partie
     */
    public ViewWorld getViewWorld() {
        return vWorld;
    }

}

