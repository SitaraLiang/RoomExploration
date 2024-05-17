package Modele;

import java.util.List;
import java.util.Stack;

public class Joueur extends Vivant{
    Stack<Objet> tresors;
    Stack<Objet> epees;
    private int scores;
    public Joueur(Case pos) {
        super(pos);
        tresors = new Stack<>();
        epees = new Stack<>();
    }

    @Override
    public void move(Direction d, EnsDeSalles grid) {
        int new_x = getPos().getX(), new_y = getPos().getY();
        if (isValidMove(d, grid)) {
            switch(d) {
                case TOP:
                    new_y--;
                    break;
                case RIGHT:
                    new_x++;
                    break;
                case DOWN:
                    new_y++;
                    break;
                case LEFT:
                    new_x--;
                    break;
            }
            Case new_pos = new Case(new_x, new_y);
            Jeton jeton = grid.getJeton(pos.getX(), pos.getY());
            Jeton dummy = grid.getJeton(new_pos.getX(), new_pos.getY());
            if (dummy == Jeton.SORTIE) {
                Jeu.termine = true;
                System.out.println("Congratulations! You have successfully reached the end!");
            } else if (dummy == Jeton.PORTE) {
                gothroughdoor(d, new_pos);
            }
            grid.setJeton(pos, Jeton.CHEMIN);
            grid.setJeton(new_pos, jeton);
            setPos(new_pos);
        }
    }

    private void gothroughdoor(Direction d, Case pos) {
        int x = pos.getX(), y = pos.getY();
        switch(d) {
            case TOP:
                y--;
                break;
            case RIGHT:
                x++;
                break;
            case DOWN:
                y++;
                break;
            case LEFT:
                x--;
                break;
        }
        pos.setX(x);
        pos.setY(y);
    }

    @Override
    public boolean isValidMove(Direction d, EnsDeSalles grid) {
        int new_x = getPos().getX(), new_y = getPos().getY();
        switch(d) {
            case TOP:
                new_y--;
                break;
            case RIGHT:
                new_x++;
                break;
            case DOWN:
                new_y++;
                break;
            case LEFT:
                new_x--;
                break;
        }
        if (grid.getJeton(new_x, new_y) != Jeton.CHEMIN && grid.getJeton(new_x, new_y) != Jeton.PORTE && grid.getJeton(new_x, new_y) != Jeton.SORTIE) {
            return false;
        }
        return true;
    }

    public Monstre detectMonster(EnsDeSalles grid) {
        List<Monstre> monstres = grid.getMonstres();
        int x = getPos().getX(), y = getPos().getY();
        for (Monstre m : monstres) {
            Case m_pos = m.getPos();
            if (m_pos.compare(new Case(x-1, y-1)) || m_pos.compare(new Case(x, y-1)) || m_pos.compare(new Case(x+1, y-1))
                    || m_pos.compare(new Case(x-1, y)) || m_pos.compare(new Case(x, y)) || m_pos.compare(new Case(x+1, y))
                    || m_pos.compare(new Case(x-1, y+1)) || m_pos.compare(new Case(x, y+1)) || m_pos.compare(new Case(x+1, y+1))) {
                return m;
            }
        }
        return null;
    }

    public Objet detectObjet(EnsDeSalles grid) {
        List<Objet> objets = grid.getObjets();
        int x = getPos().getX(), y = getPos().getY();
        for (Objet o : objets) {
            Case o_pos = o.getPos();
            if (o_pos.compare(new Case(x-1, y-1)) || o_pos.compare(new Case(x, y-1)) || o_pos.compare(new Case(x+1, y-1))
                    || o_pos.compare(new Case(x-1, y)) || o_pos.compare(new Case(x, y)) || o_pos.compare(new Case(x+1, y))
                    || o_pos.compare(new Case(x-1, y+1)) || o_pos.compare(new Case(x, y+1)) || o_pos.compare(new Case(x+1, y+1))) {
                return o;
            }
        }
        return null;
    }

    public void collect(EnsDeSalles grid) {
        Objet o = detectObjet(grid);
        if (o != null) {
            if (o.getClass() == Tresor.class) {
                tresors.add(o);
                scores++;
                System.out.println("Collect Treasure!");
            } else {
                epees.add(o);
                scores++;
                System.out.println("Collect Sword!");
            }
            Jeton jeton = grid.getJeton(pos.getX(), pos.getY());
            grid.setJeton(pos, Jeton.CHEMIN);
            grid.setJeton(o.getPos(), jeton);
            setPos(o.getPos());
            grid.removeObjet(o);
        }


    }

    public void useEpee(EnsDeSalles grid) {
        if (epees.isEmpty()) return;
        Monstre m = detectMonster(grid);
        if (m != null) {
            Objet epee = epees.pop();
            epee.useObjet(m);
            if (m.isDead()) {
                grid.setJeton(m.getPos(), Jeton.CHEMIN);
                grid.removeMonstre(m);
                scores+=3;
            }
        }
    }

    public void useTresor() {
        if (tresors.isEmpty()) return;
        Objet tresor = tresors.pop();
        tresor.useObjet(this);
    }



    public int getScores() {
        return scores;
    }
}
