package View;


import java.util.concurrent.atomic.AtomicBoolean;

class ThreadAffichage extends Thread {
    private ViewWorld window;
    private AtomicBoolean running = new AtomicBoolean(false); // Va nous permettre de bien arreter le thread
    private final int delai =25;

    /* Constructeur */
    public ThreadAffichage(ViewWorld window) {
        this.window = window;
    }

    /**
     * Arrete le thread a la fin de la partie
     */
    public void interrupt(){
        running.set(false);
    }

    /**
     *
     * @return vrai si le thread run, false sinon
     */

    boolean isRunning() {
        return running.get();
    }

    /**
     * methode qui tourne en boucle lors du cycle de vie du thread
     */
    public void run() {
        running.set(true);

        // Tant que la partie est en cours
        while (window.getGame().isGameOn()) {
            window.repaint(); // Cela permet de mettre a jour la fenetre graphique
            try {
                Thread.sleep(delai);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        window.endScreen();
        running.set(false);
    }
}