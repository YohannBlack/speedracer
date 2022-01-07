package Model;

import java.util.Random;

public class Opponent {
    //Attributs

    private int x; //abscisse
    private int y; // ordonnée
    public static final int MAXSPEED = 2; // vitesse max de l'opponent
    private int speed; // vitesse relative à celle de notre voiture
    private Random rand = new Random();
    // Constructors

    public Opponent(int x, int y) {
        this.x = x;
        this.y = y;
        speed = 1 + rand.nextInt(MAXSPEED);
    }


    // Methods

    /**
     *
     * @return la vitesse de l'adversaire
     */
    public int getSpeed() {
        return speed;
    }

    /**
     *
     * @return l'abscisse de l'adversaire
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return l'ordonnée de l'adversaire
     */
    public int getY() { return y ; }

    /**
     * permet d'update l'ordonnée de l'adversaire
     */
    public void setY(int stop) {
        this.y = y - speed + stop;
    }



}
