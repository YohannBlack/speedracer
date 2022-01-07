package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Car {

    //Attributs

    private int x, y; // Coordonnées de la voiture
    public static final int LATERALSPEED = 1; // vitesse de la voiture de gauche à droite
    public static final int WIDTH  = 15; // largeur de la voiture
    public static final int HEIGHT  = 20; // Hauteur de la voiture
    private ArrayList<Opponent> opponents = new ArrayList<>(); // Array des voitures adverses
    private Random rand = new Random();
    private Road road; // route sur laquelle est la voiture


    // Constructeur

    public Car(int x, int y, Road road) {
        this.x = x;
        this.y = y;
        this.road = road;

        // Ajout des opponents
        int last_Y = 0;
        for (int i = 0; i < 5; i++) {
            opponents.add(new Opponent(100 + rand.nextInt(200),
                    last_Y + Game.horizon + rand.nextInt(100) ));
            last_Y = last_Y + Game.horizon;
        }
    }

    // Methods
    /**
     *
     * @return la vitesse lateralle de la voiture
     */
    public int getSPEED() {
        return LATERALSPEED;
    }

    /**
     *
     * @return la largeur d'une voiture
     */
    public int getWIDTH() {
        return WIDTH;
    }

    /**
     *
     * @return la hauteur d'une voiture
     */
    public int getHEIGHT() {
        return HEIGHT;
    }

    /**
     *
     * @return l'abscisse d'une voiture
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return l'ordonnee d'une voiture
     */
    public int getY(){
        return y;
    }

    /**
     *
     * @return tableau de Point contenant les coordonnees de nos adversaires
     */
    public Point[] getOpponents() {
        Point[] res = new Point[opponents.size()];
        for (int i = 0; i < opponents.size(); i++) {
            res[i] = new Point(opponents.get(i).getX() + road.getPosX() , opponents.get(i).getY());

        }
        return res;
    }



    /**
     * Cette methode met à jour opponents, elle retire ceux qu'on a deja depassé et rajoute des nouveaux
     * @param stop
     */
    public void updateOpponents(int stop) {
        for (Opponent p : opponents) {
            p.setY(stop); // On met à jour l'ordonnée de chaque adversaire
        }
        if (opponents.get(1).getY() <=0) { // Si le deuxieme n'est plus visible, on retire le 1er
            opponents.remove(0);
        }
        // Si l'avant dernier est visible, on ajoute un adversaire à la fin
        if (opponents.get(opponents.size() - 1).getY() <= Game.horizon) {
            int x = 100+ rand.nextInt(200);
            int y = opponents.get(opponents.size()-1).getY()  + Game.horizon/2 + rand.nextInt(100);
            opponents.add(new Opponent(x, y));

        }
    }
}
