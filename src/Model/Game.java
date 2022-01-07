package Model;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;


public class Game {

    //Attributs
    private Car car; // notre voiture
    public static final int HAUTEUR = 300; // Hauteur de notre monde
    public static final int LARGEUR = 500; // Largeur de notre monde
    public static final int CAR_X = LARGEUR / 2, CAR_Y = 0; // les coordonnees de la voiture
    public static final int horizon = HAUTEUR ; // ordonnee de notre horizon
    public static final int SPEED = 4; // vitesse de deplacement
    private boolean left = false; // booleen qui permet de tourner à gauche
    private boolean right = false; // boolean qui permet de tourner à droite
    private final ThreadAvancer avance; // thread qui permet de faire avancer l'ensemble de l'univers
    private boolean gameOn; // boolean qui informe de l'etat de la partie (finie ou pas)
    private Road road; // notre route
    private int passedCheckPoints; // Nombre de checkpoints dépassés
    int stop; // int representant le ralebtissement de vitesse
    public static final int  maxTime = 10; // temps maximum du compteur au debut
    private int timeLeft; // int representant les secondes restantes
    private Timer timer = new Timer(); // timer qui va permettre d'executer une tache regulierement
    private TimerTask task = new TimerTask() {
        // Tache à executer par timer
        @Override
        public void run() {
            timeLeft--;
            if (checkIfOut()) { // Si la voiture sort de la route
                // Le ralentissement ne doit pas être superieur à la vitesse sinon on fait marche arriere
                if(stop < road.getSpeed()) {
                    stop++;
                }
            }
            else {
                // ralentissement negatif  --> acceleration
                if (stop > 0) {
                    stop --;
                }
            }
        }
    };




    // Constructor
    public Game() {
        this.road = new Road();
        this.car = new Car(CAR_X, CAR_Y, road);
        this.avance = new ThreadAvancer(this);
        gameOn = true;
        avance.start();
        // Execution de la tache chaque seconde ----> compte à rebours
        timer.scheduleAtFixedRate(task,1000,1000);
        passedCheckPoints = 0;
        timeLeft = maxTime;
        stop = 0;
    }

    // Methods
    /**
     * Si le boolean left est true, alors tourner à gauche sinon ne rien faire
     */
    public void moveLeft() {
        if (left)
            road.moveLeft();
    }

    /**
     * Si le boolean right est true, alors tourner à droite sinon ne rien faire
     */
    public void moveRight() {
        if (right)
            road.moveRight();
    }

    /**
     * mettre left à true, utilisé pour faire tourner la voiture à gauche
     */
    public void setTrueLeft() {
        left = true;
    }

    /**
     * mettre left à false, utilisé pour faire tourner la voiture à gauche
     */
    public void setFalseLeft() {
        left = false;
    }

    /**
     * mettre right à true, utilisé pour faire tourner la voiture à droite
     */
    public void setTrueRight() {
        right = true;
    }
    /**
     * mettre right à true, utilisé pour faire tourner la voiture à droite
     */
    public void setFalseRight() {
        right = false;
    }

    /**
     * @return la largeur de la voiture
     */
    public int getCarWidth() {
        return car.getWIDTH();
    }

    /**
     * @return la hauteur de la voiture
     */
    public int getCarHeight() {
        return car.getHEIGHT();
    }

    /**
     * @return l'abscisse de notre voiture
     */
    public int getCAR_X() {
        return car.getX();
    }

    /**
     * @return l'ordonnee de notre voiture
     */
    public int getCAR_Y() {
        return car.getY();
    }

    /**
     *
     * @return un tableau de Point qui represente le côté gauche de la route
     */
    public Point[] getLeftSide() {
        return road.getLeftSide(road.getPosY());
    }

    /**
     *
     * @return un tableau de Point qui represente le côté droit de la route
     */
    public Point[] getRightSide() {
        return road.getRightSide(road.getPosY());
    }

    /**
     *
     * @return un tableau de Point qui represente le milieu de la route
     */
    public Point[] getMidLane() {
        return road.getMidLane(road.getPosY());
    }


    /**
     *
     * @return la hauteur de notre monde
     */
    public int getHAUTEUR() {
        return HAUTEUR;
    }

    /**
     *
     * @return la largeur de notre monde
     */
    public int getLARGEUR() {
        return LARGEUR;
    }

    /**
     *
     * @return posX
     */
    public int getposX() {
        return road.getPosX();
    }

    /**
     *
     * @return posY
     */
    public int getposY() {
        return road.getPosY();
    }

    /**
     * method executé pour faire avancer le monde
     */
    public void avancer() {
        updateTimer(); // Mis à jour du compte à rebours
        road.setPosY(stop); // Mis à jour de posY de la route
        car.updateOpponents(stop); // Mis à jour de la position des adversaires
        testPerdu(); // test si la partie est finie
    }

    /**
     *
     * @return le temps restant
     */
    public int getTimeLeft() {
        return timeLeft;
    }

    /**
     * mets à jour le timer s'il est dépassé
     */
    public void updateTimer() {
        // 5 represente l'avancement de notre voiture par rapport au bas de la fenetre graphique
        if (road.getCheckpointY() < CAR_Y+5) { // Si le checkpoint a été dépassé
            setpassedCheckPoints();
            // On essaie d'ajouter au minimum 5 secondes
            if (passedCheckPoints + 5 < maxTime/2 )
                timeLeft = timeLeft + ((maxTime / 2) - passedCheckPoints); // on ajoute de moins en moins de temps
            else
                timeLeft = timeLeft + 5;
        }
    }

    /**
     * incremente le nombre de checkpoints
     */
    public void setpassedCheckPoints() {
        passedCheckPoints++;
    }

    /**
     *
     * @return l'ordonnée du checkpoint
     */
    public int getCheckPointY(){
        return road.getCheckpointY();
    }

    /**
     *
     * @return road de type Road representant notre route
     */
    public Road getRoad() {
        return road;
    }

    /**
     *
     * @return un tableau de Point representant la position des adversaires
     */
    public Point[] getOpponents() {
        return car.getOpponents();
    }

    /**
     * Methode qui test si la partie est finie
     * Si le timer est à 0 ou s'il y a eu une collision
     */
    public void testPerdu() {
        Point [] opponents = getOpponents();
        for (int i = 0; i< opponents.length; i++) {
            // si (ya collision || compteur à 0 || vitesse à 0)
            if ((CAR_X< opponents[i].x + Car.WIDTH/2 &&
                    CAR_X + Car.WIDTH/2 > opponents[i].x &&
                    CAR_Y < opponents[i].y + Car.HEIGHT &&
                    Car.HEIGHT + CAR_Y > opponents[i].y)
                    ||  timeLeft <= 0 || ( road.getSpeed() - stop == 0)){
                avance.interrupt(); // On n'avance plus
                timer.cancel(); // On arrete le timer
                gameOn = false; // La partie est finie
                break;
            }
        }
    }

    /**
     *
     * @return true si la voiture sort de la route, false sinon
     */
    public boolean checkIfOut() {
        // Le but est de créer des droites qui se rapproche de la courbe afin de tester la position relative entre
        // la voiture et la droite
        Point[] midLane = getMidLane();
        Point pt1 = midLane[0];
        Point pt2 = midLane[1];
        Point midl = new Point((pt1.x + pt2.x) /2,(pt1.y + pt2.y) /2 ); // point "d'encrage"

        for (int i = 1; i< midLane.length -1; i++) {
            if (CAR_Y < midl.y && CAR_Y >= pt1.y) {
                if(pt1.x < midl.x) {
                    if (CAR_X <= midl.x + road.getRoadWidth()/2  && CAR_X >= pt1.x - road.getRoadWidth()/2 ) {
                        //System.out.println("IN 1");
                        return false;
                    }
                }
                else {
                    if (CAR_X >= midl.x - road.getRoadWidth()/2 && CAR_X <= pt1.x + road.getRoadWidth()/2) {
                        //System.out.println("IN 2");
                        return false;
                    }
                }
            }
            else if (CAR_Y >= midl.y && CAR_Y < pt2.y) {
                if(midl.x < pt2.x) {
                    if (CAR_X <= pt2.x + road.getRoadWidth()/2  && CAR_X >= midl.x - road.getRoadWidth()/2) {
                        //System.out.println("IN 3");
                        return false;
                    }
                }
                else {
                    if (CAR_X >= pt2.x - road.getRoadWidth()/2 && CAR_X <= midl.x + road.getRoadWidth()/2 ) {
                        //System.out.println("IN 4");
                        return false;
                    }
                }
            }
            pt1 = midLane[i];
            pt2 = midLane[i+1];
            midl = new Point((pt1.x + pt2.x) /2,(pt1.y + pt2.y) /2 );

        }
        //System.out.println("OUT ");
        return true;
    }

    /**
     *
     * @return un int representant le ralentissement
     */
    public int getStop() { return stop; }

    /**
     *
     * @return false si la partie est finie, true sinon
     */
    public boolean isGameOn() {
        return gameOn;
    }
}





