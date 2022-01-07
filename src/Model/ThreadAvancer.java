package Model;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadAvancer extends Thread {
    Game state;
    AtomicBoolean running = new AtomicBoolean(false); // Va nous permettre de bien arreter le thread
    private final int delai = 25;

    /* Constructeur */
    public ThreadAvancer(Game state){
        this.state = state;
    }

    /**
     * Arrete le thread
     */
    public void interrupt(){
        running.set(false);
    }

    /**
     *
     * @return true si le thread est entrain de run, false sinon
     */
    boolean isRunning() {
        return running.get();
    }

    /**
     * Cette methode va tourner en boucle le long de cycle de vie du thread
     */

    @Override
    public void run() {
        running.set(true);
        // tant que la partie est en cours
        while (state.isGameOn()) {
            state.avancer();
            state.moveLeft();
            state.moveRight();
            state.checkIfOut();
            try {
                Thread.sleep(delai);
            } catch ( InterruptedException e) {
                e.printStackTrace();
                this.interrupt();
            }
        }
        running.set(false);
    }

}
