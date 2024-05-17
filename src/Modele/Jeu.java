package Modele;

import java.io.IOException;
import java.util.List;
import Exception.LevelOutOfBoundException;
import javax.swing.event.EventListenerList;
import static java.lang.Thread.sleep;

public class Jeu {
    private final int MAX_LEVEL = (int) Configuration.MAX_LEVEL.getValue();
    private final int SPEED_MONSTRE = (int) Configuration.MONSTER_SPEED.getValue();
    public static Case START;
    private Case sortie;
    private int current_level;
    private EnsDeSalles grid;
    public static boolean termine = false;
    private String filepath;
    private Joueur joueur;
    private static EventListenerList listeners;

    public Jeu(int current_level, String path) throws IOException {
        this.current_level = current_level;
        this.listeners = new EventListenerList();
        this.filepath = path;
        this.grid = new EnsDeSalles();
        grid.intialiseGrid(current_level, filepath);
        this.joueur = grid.getJoueur();
        this.START = joueur.getPos();
        this.sortie = grid.getSortie();
    }
    public Jeu(String path) throws IOException { this(0, path); }

    public void updateLevel(int currentLevel) throws LevelOutOfBoundException, IOException {
        if (currentLevel < 0 || currentLevel > MAX_LEVEL) {
            throw new LevelOutOfBoundException("Level Out of Bound.");
        }
        current_level = currentLevel;
        grid.intialiseGrid(current_level, filepath);
        joueur = grid.getJoueur();
        sortie = grid.getSortie();
        START = joueur.getPos();
    }
    public void movePlayer(Key key) {
        switch(key) {
            case UP:
                joueur.move(Direction.TOP, grid);
                break;
            case RIGHT:
                joueur.move(Direction.RIGHT, grid);
                break;
            case DOWN:
                joueur.move(Direction.DOWN, grid);
                break;
            case LEFT:
                joueur.move(Direction.LEFT, grid);
                break;
            case COLLECT:
                joueur.collect(grid);
                break;
            case USE_EPEE:
                joueur.useEpee(grid);
                break;
            case USE_TREASOR:
                joueur.useTresor();
                break;
        }
        PositionChanged();
    }


    public void moveMonstre() {
        List<Monstre> lst = grid.getMonstres();
        for (Monstre m : lst) {
            m.move(null, grid);
        }
        PositionChanged();
    }

    public void startGame(){
        termine = false;

        Thread thread = new Thread(() -> {
            try {
                while(!termine) {
                    moveMonstre();
                    sleep(SPEED_MONSTRE);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();

        while(!termine) {
            //afficheJeu();
            if (joueur.isDead()) {
                termine = true;
                PositionChanged();
            }
        }
    }


    public void addListenerPositionChanged(ChangementPositionListener listener) {
        listeners.add(ChangementPositionListener.class, listener);
    }

    private void PositionChanged() {
        ChangementPositionListener[] lst =
                (ChangementPositionListener[])
                        listeners.getListeners(ChangementPositionListener.class);
        for (ChangementPositionListener listener : lst) {
            listener.PositionChangee(new ChangementPositionEvent(this, grid));
        }
    }

    public void addMonsterAttackListener(MonsterAttackListener listener) {
        listeners.add(MonsterAttackListener.class, listener);
    }

    public static void MonsterAttack(Monstre m) {
        MonsterAttackListener[] lst =
                (MonsterAttackListener[])
                        listeners.getListeners(MonsterAttackListener.class);
        for (MonsterAttackListener listener : lst) {
            listener.MonsterAttackEffect(new MonsterAttackEvent(m));
        }
    }

    public void afficheJeu() {
        grid.affiche();
    }
    public String terminate() {
        if (joueur.isDead())
            return "Unfortunately, you were killed by the monster...";
        else
            return "Good game! You won!";
    }
    public int getNbLignes() {
        return grid.getNb_lignes();
    }
    public int getNbColonnes() {
        return grid.getNb_colonnes();
    }
    public int getLevel() {
        return current_level;
    }
    public EnsDeSalles getGrid() {
        return grid;
    }
    public int getScore() {
        return joueur.getScores();
    }

    public int getPlayerLife() {
        return joueur.getVies();
    }
}
