package Model;

import java.awt.*;

import java.util.ArrayList;
import java.util.Random;

public class Road {

    // Attributs
    private int posX = 0; // le decalage horizontale de notre monde
    private int posY = 0; // l'avancement verticale de notre monde
    public static final int SPEED = 3; //
    private ArrayList<Point> leftSide = new ArrayList<>(); // gauche de la route
    private ArrayList<Point> midLane = new ArrayList<>(); // Ligne milieu de route
    private ArrayList<Point> rightSide = new ArrayList<>(); // droite de la route
    private ArrayList<Point> rightTrees = new ArrayList<>(); // Arbres situés à droite
    private ArrayList<Point> leftTrees = new ArrayList<>(); // Arbres situés à gauche
    Random rand = new Random();
    private int horizon = Game.horizon ; // l'horizon
    private int roadWidth = Game.LARGEUR/ 20; // la largeur de la route
    private int space = Game.HAUTEUR / 4; // l'espace entre chaque point de la route
    private int centreRoad = Game.LARGEUR / 2; // l'emplacement du milieu de la route
    private int checkpointY; // ordonnée du checkpoint


    //Constructors
    public Road() {
        int y = 0;
        int x;
        // Construction de la partie gauche de la route
        leftSide.add(new Point(centreRoad - roadWidth/2, 0));
        leftSide.add(new Point(100+ rand.nextInt(200), Game.HAUTEUR));
        leftSide.add(new Point(100+ rand.nextInt(200), 2*Game.HAUTEUR));
        leftSide.add(new Point(100+ rand.nextInt(200), 3*Game.HAUTEUR ));
        checkpointY = Game.HAUTEUR ;

        // Construction de la partie droite
        for (Point p : leftSide){
            rightSide.add(new Point(p.x + roadWidth, p.y));
            midLane.add(new Point(p.x + roadWidth/2, p.y));
        }
        // Ajout des arbres
        leftTrees.add(new Point(0, Game.HAUTEUR/10));
        leftTrees.add(new Point(0, Game.HAUTEUR/5));
        leftTrees.add(new Point(0, Game.HAUTEUR));
        leftTrees.add(new Point(0, 3*Game.HAUTEUR/2));

        rightTrees.add(new Point((9* Game.LARGEUR/10),Game.HAUTEUR/8));
        rightTrees.add(new Point((9* Game.LARGEUR/10),Game.HAUTEUR/4));
        rightTrees.add(new Point((9* Game.LARGEUR/10),Game.HAUTEUR/2));
        rightTrees.add(new Point((9* Game.LARGEUR/10),4*Game.HAUTEUR/3));

    }

    //Methods

    /**
     * Cette methode retire de les points qu'on a deja dépassé et ajoute des nouveaux
     * @param posY un int qui represente l'avancement de notre monte en veritcal
     */
    public void updateRoad(int posY) {
        if (leftSide.get(2).y - posY <= 0) {
            leftSide.remove(0);// On retire le point qui n'apparait plus
            rightSide.remove(0);
            midLane.remove(0);
        }
        if (leftSide.get(leftSide.size()-2).y - posY <= Game.horizon) {
            int x = 100+ rand.nextInt(200);
            leftSide.add(new Point(x, leftSide.get(leftSide.size()-1).y + horizon/2));

            rightSide.add(new Point(x + roadWidth, rightSide.get(rightSide.size()-1).y + horizon/2));

            midLane.add(new Point(x + roadWidth/2, rightSide.get(midLane.size()-1).y + horizon/2));
        }

    }

    /**
     *
     * @param posY un int qui represente l'avancement de notre monte en veritcal
     * @return un tableau de Point qui represente la partie gauche de notre route
     */
    public Point[] getLeftSide(int posY){
        updateRoad(posY);
        Point[] res = new Point[leftSide.size()];
        for (int i = 0; i < leftSide.size(); i++) {
            res[i] = new Point(leftSide.get(i).x + posX, leftSide.get(i).y - posY);
        }
        return res;
    }

    /**
     *
     * @param posY un int qui represente l'avancement de notre monte en veritcal
     * @return un tableau de Point qui represente la partie droite de notre route
     */
    public Point[] getRightSide(int posY){
        updateRoad(posY);
        Point[] res = new Point[rightSide.size()];
        for (int i = 0; i < rightSide.size(); i++) {
            res[i] = new Point(rightSide.get(i).x + posX, rightSide.get(i).y - posY);
        }
        return res;
    }

    /**
     *
     * @param posY un int qui represente l'avancement de notre monte en veritcal
     * @return un tableau de Point qui represente la ligne au milieu de notre route
     */
    public Point[] getMidLane (int posY){
        updateRoad(posY);
        Point[] res = new Point[midLane.size()];
        for (int i = 0; i < midLane.size(); i++) {
            res[i] = new Point(midLane.get(i).x + posX, midLane.get(i).y - posY);
        }
        return res;
    }

    /**
     *
     * @return posY
     */
    public int getPosY(){
        return posY;
    }

    /**
     *
     * @return posx
     */
    public int getPosX(){
        return posX;
    }

    /**
     * Mettre à jour posy afin d'avancer
     */
    public void setPosY(int stop){
        posY = posY + SPEED - stop;

    }

    /**
     * Augementer la valeur de posx
     */
    public void moveLeft() {
        posX = posX + Game.SPEED;
    }

    /**
     * Diminuer la valeur de posx
     */
    public void moveRight() {
        posX = posX - Game.SPEED;
    }

    /**
     *
     * @param p point à coordonnées x, y representant une voiture
     * @return true si la voiture est visible, false sinon
     */
    public boolean isVisible(Point p) {
        return p.y <= horizon && p.y > 0;
    }

    /**
     *
     * @return la position du checkpoint
     */
    public int getCheckpointY() {
        updateCheckPointY();
        return checkpointY - posY;
    }

    /**
     * permet de mettre à jour le checkpoint quand il est dépassé
     */
    public void updateCheckPointY() {
        if (checkpointY - posY <=0) {
            checkpointY = posY + 3*Game.HAUTEUR ;
        }
    }

    /**
     *
     * @return la largeur de la route
     */
    public int getRoadWidth() {
        return roadWidth;
    }

    /**
     *
     * @return la vitesse max de la voiture
     */
    public int getSpeed() {
        return SPEED;
    }

    /**
     *
     * @return un tableau de Point representant les arbres situés à droite
     */
    public Point[] getRightTrees() {
        updateRightTrees(posY);
        Point[] res = new Point[rightTrees.size()];
        for (int i = 0; i < rightTrees.size(); i++) {
            res[i] = new Point(rightTrees.get(i).x + posX, rightTrees.get(i).y - posY);
        }
        return res;
    }

    /**
     *
     * @param posY int representant l'avancement de notre monde
     */
    private void updateRightTrees(int posY) {
        if (rightTrees.get(2).y - posY <= 0) {
            rightTrees.remove(0);// On retire le point qui n'apparait plus
        }
        // Si l'avant avant dernier point est visible on ajoute un nouveau point
        if (rightTrees.get(rightTrees.size()-2).y - posY <= Game.horizon) {
            rightTrees.add(new Point(rightTrees.get(rightTrees.size()-2).x, rightTrees.get(rightTrees.size()-1).y + horizon/3));
        }
    }

    /**
     *
     * @return un tableau de Point representant les arbres situés à gauche
     */
    public Point[] getLeftTrees() {
        updateLeftTrees(posY);
        Point[] res = new Point[leftTrees.size()];
        for (int i = 0; i < leftTrees.size(); i++) {
            res[i] = new Point(leftTrees.get(i).x + posX, leftTrees.get(i).y - posY);
        }
        return res;
    }

    /**
     *
     * @param posY int representant l'avancement de notre monde
     */
    private void updateLeftTrees(int posY) {
        if (leftTrees.get(2).y - posY <= 0) {
            leftTrees.remove(0);// On retire le point qui n'apparait plus
        }
        // Si l'avant avant dernier point est visible on ajoute un nouveau point
        if (leftTrees.get(leftTrees.size()-2).y - posY <= Game.horizon) {
            leftTrees.add(new Point(leftTrees.get(leftTrees.size()-2).x, leftTrees.get(leftTrees.size()-1).y + horizon/3));
        }
    }
}



































