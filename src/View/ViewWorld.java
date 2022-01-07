package View;

import Model.*;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;


public class ViewWorld extends JPanel {
    // Attributs
    private Game game; // Notre monde
    private ThreadAffichage threadAffichage; // Thread qui permet d'afficher le monde
    private int WindowHeight = 600; // Hauteur de notre fenetre
    private int WindowWidth =1000; // Largeur de notre fenetre
    private ImageIcon imageTrees, imageCar, imageOpponent;//Icones d'images pour get les imagesrepresenter plusieurs elements
    private Image trees, carImage, opponent; // Les images qui representent plusieurs elements
    private Graphics2D g2; // Nous allons draw sur g2
    double heightRatio =  WindowHeight/ Game.HAUTEUR; // Ratio hauteur fenetre/monde
    double widthRatio =  WindowWidth / Game.LARGEUR; // Ratio largeur


    //Constructor
    public ViewWorld(Game game) throws AWTException {
        this.game = game;
        Dimension dim = new Dimension(WindowWidth, WindowHeight);
        this.setPreferredSize(dim);
        this.setBackground(new Color(34,139,34));
        this.threadAffichage =(new ThreadAffichage(this));
        threadAffichage.start();

        // Recuperation des images
        imageTrees = new ImageIcon("src/resources/trees.png");
        imageCar = new ImageIcon("src/resources/car.png");
        imageOpponent = new ImageIcon("src/resources/opponent.png");
        trees = imageTrees.getImage();
        carImage = imageCar.getImage();
        opponent = imageOpponent.getImage();
    }

    //Methods
    /**
     *
     * @param p le point à transformer
     * @param W_ratio le ratio entre largeur du monde et de la fenetre
     * @param H_ratio le ration entre la hauteur du monde et de la fenetre
     * @return un point graphique, qui represente p dans la fenetre graphique
     */
    public Point transforme( Point p,double W_ratio, double H_ratio) {
        Point pg = new Point();
        pg.x = (int) (p.x * W_ratio);
        pg.y = (int) (WindowHeight - (p.y * H_ratio));
        return pg;
    }

    /**
     *
     * @param g la fenetre graphique sur laquelle on dessine
     */
    public void paint(Graphics g) {
        g2 = (Graphics2D)g ;
        super.paint(g2);

        /********************    DESSIN DE NOTRE ROUTE       *****************************/

        Point[] pointsLeft = this.game.getLeftSide();
        Point[] pointsRight = this.game.getRightSide();
        Point[] pointsMid = this.game.getMidLane();

        Point ppgl = transforme(pointsLeft[0], widthRatio, heightRatio);
        Point pgl = transforme(pointsLeft[1], widthRatio, heightRatio);
        Point ppgr = transforme(pointsRight[0], widthRatio, heightRatio);
        Point pgr = transforme(pointsRight[1], widthRatio, heightRatio);
        Point ppgm = transforme(pointsMid[0], widthRatio, heightRatio);
        Point pgm = transforme(pointsMid[1], widthRatio, heightRatio);

        g.setColor(Color.darkGray);
        Point midl = new Point((pgl.x+ppgl.x)/2, (pgl.y+ppgl.y)/2);
        Point midr = new Point((pgr.x+ppgr.x)/2, (pgr.y+ppgr.y)/2);
        Point midm = new Point((pgm.x+ppgm.x)/2, (pgm.y+ppgm.y)/2);
        g2.drawLine(ppgl.x, ppgl.y, midl.x, midl.y);
        g2.drawLine(ppgr.x, ppgr.y, midr.x, midr.y);
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(ppgm.x, ppgm.y, midm.x, midm.y);

        for(int i =0; i < pointsLeft.length-1; i++) {
            g2.setColor(Color.darkGray);
            Point firstPointLeft = transforme(pointsLeft[i], widthRatio, heightRatio);
            Point secondPointLeft = transforme(pointsLeft[i + 1], widthRatio, heightRatio);

            Point firstPointRight = transforme(pointsRight[i], widthRatio, heightRatio);
            Point secondPointRight = transforme(pointsRight[i + 1], widthRatio, heightRatio);

            Point firstPointMid = transforme(pointsMid[i], widthRatio, heightRatio);
            Point secondPointMid = transforme(pointsMid[i + 1], widthRatio, heightRatio);

            Point midl2 = new Point((secondPointLeft.x + firstPointLeft.x) / 2,
                    (secondPointLeft.y + firstPointLeft.y) / 2);
            Point midr2 = new Point((secondPointRight.x + firstPointRight.x) / 2,
                    (secondPointRight.y + firstPointRight.y) / 2);

            Point midm2 = new Point((secondPointMid.x + firstPointMid.x) / 2,
                    (secondPointMid.y + firstPointMid.y) / 2);

            QuadCurve2D courbel = new QuadCurve2D.Double();
            QuadCurve2D courber = new QuadCurve2D.Double();
            QuadCurve2D courbem = new QuadCurve2D.Double();
            courbel.setCurve(
                    new Point2D.Double(midl.x, midl.y),
                    new Point2D.Double(transforme(pointsLeft[i], widthRatio, heightRatio).x,
                            transforme(pointsLeft[i], widthRatio, heightRatio).y),
                    new Point2D.Double(midl2.x, midl2.y)
                );

            courber.setCurve(
                    new Point2D.Double(midr.x, midr.y),
                    new Point2D.Double(transforme(pointsRight[i], widthRatio, heightRatio).x,
                            transforme(pointsRight[i], widthRatio, heightRatio).y),
                    new Point2D.Double(midr2.x, midr2.y)
            );

            courbem.setCurve(
                    new Point2D.Double(midm.x, midm.y),
                    new Point2D.Double(transforme(pointsMid[i], widthRatio, heightRatio).x,
                            transforme(pointsMid[i], widthRatio, heightRatio).y),
                    new Point2D.Double(midm2.x, midm2.y)
            );

            g2.setStroke(new BasicStroke(2*game.getRoad().getRoadWidth()));
            g2.setColor(Color.darkGray);
            g2.draw(courbel);
            g2.draw(courber);
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.white);
            g2.draw(courbem);
            midl = midl2;
            midr = midr2;
            midm = midm2;

        }
        g2.setStroke(new BasicStroke(2));

        /*****************       DESSIN DU CHECKPOINT ******************************************/
        g2.setColor(Color.YELLOW);
        g2.drawLine(0, (int)((Game.HAUTEUR - game.getCheckPointY())*heightRatio),
                (int)(Game.LARGEUR * widthRatio), (int)((Game.HAUTEUR - game.getCheckPointY()) * heightRatio));


        /**************         DESSIN DE LA VOITURE *********************************************/
        int x = game.getCAR_X() - game.getCarWidth()/2;
        int y = game.getCAR_Y() + game.getCarHeight() + 5 ;
        Point p = transforme(new Point(x, y), widthRatio, heightRatio);
        g2.drawImage(carImage,(int)p.x , (int) p.y,(int)(game.getCarWidth()* widthRatio),
                (int)(game.getCarHeight()* heightRatio), this  );


        /********************        DESSIN DES ADVERSAIRES        *******************************/
        Point[] opponents = this.game.getOpponents();
        Point pg;
        for (Point point : opponents) {
            pg = transforme(point, widthRatio, heightRatio);

            g2.drawImage(opponent,(int)pg.x , (int) pg.y,
                    (int)(game.getCarWidth()* widthRatio), (int)(game.getCarHeight()* heightRatio), this );
        }

        /**********       DESSIN DU BACKGROUND         *******************************************************/
        Point[] leftTrees = game.getRoad().getLeftTrees();
        Point[] rightTrees = game.getRoad().getRightTrees();
        Point tg;

        for (Point t : leftTrees) {
            tg = transforme(t, widthRatio, heightRatio);
            g2.drawImage(trees,(int)tg.x , (int) tg.y,
                    200,200, this );
            g2.drawImage(trees,(int)tg.x  - 200, (int) tg.y,
                    200,200, this );
            g2.drawImage(trees,(int)tg.x  - 400, (int) tg.y,
                    200,200, this );

        }

        for (Point t : rightTrees) {
            tg = transforme(t, widthRatio, heightRatio);
            g2.drawImage(trees,(int)tg.x , (int) tg.y,
                    200,200, this );
            g2.drawImage(trees,(int)tg.x + 200, (int) tg.y,
                    200,200, this );
            g2.drawImage(trees,(int)tg.x + 400, (int) tg.y,
                    200,200, this );
        }

        /******************   DISPLAY LE SCORE , LE TEMPS ET LA VITESSE ************************************/
        g2.setColor(Color.WHITE);
        Font font = new Font("SANS_SERIF", Font.BOLD, 20);;
        g2.setFont(font);
        g2.drawString("Score: " + game.getposY(),this.WindowWidth - 180,this.WindowHeight -10 );
        g2.drawString("Vitesse: " + ((game.SPEED - game.getStop() - 1) * 10) + " Km/h",this.WindowWidth - 180,this.WindowHeight -30 );
        g2.drawString("seconds left  " + game.getTimeLeft() ,this.WindowWidth - 950,this.WindowHeight -30 );



    }

    /**
     * methode qui affiche une fentre de fin de partie
     */
    public void endScreen() {
        threadAffichage.interrupt();
        JOptionPane.showMessageDialog(View.frame, "Votre score : " + game.getposY(), "Game Over", JOptionPane.PLAIN_MESSAGE);
        System.exit(0);
    }

    /**
     *
     * @return notre partie
     */
    public Game getGame() {
        return game;
    }

}
