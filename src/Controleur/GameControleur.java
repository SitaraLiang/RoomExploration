package Controleur;

import Modele.*;
import Vue.GameUI;
import java.io.IOException;
import Exception.LevelOutOfBoundException;
import Vue.VueGame;


public class GameControleur {
    private final String TEMPLATE_FILE = (String) Configuration.GAME_TEMPLATE.getValue();
    private Jeu jeu;
    private GameUI window = null;

    public GameControleur() throws IOException {
        window = new GameUI(this);
        jeu = new Jeu(TEMPLATE_FILE);
    }
    public void AfficheVues() {
        window.setVisible(true);
    }

    public void startGame(VueGame panneau) throws InterruptedException {
        jeu.addListenerPositionChanged(panneau);
        jeu.addMonsterAttackListener(panneau);
        jeu.startGame();
    }

    public void setLevel(int level) throws LevelOutOfBoundException, IOException {
        jeu.updateLevel(level);
    }

    public int getNbLignes() {
        return jeu.getNbLignes();
    }

    public int getNbColonnes() {
        return jeu.getNbColonnes();
    }

    public int getLevel() {
        return jeu.getLevel();
    }

    public EnsDeSalles getGrid() {
        return jeu.getGrid();
    }

    public int getScore() {
        return jeu.getScore();
    }

    public int getPlayerLife() { return jeu.getPlayerLife();}

    public String getMessage() {
        return jeu.terminate();
    }

    public boolean getTerminateStatus() {
        return jeu.termine;
    }

    // tell the class modele to change
    public void notifyPositionChanged(Key key) {
        if (key != null) {
            jeu.movePlayer(key);
        }
    }
}
