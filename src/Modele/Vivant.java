package Modele;

import java.util.List;

public abstract class Vivant {
    protected int vies = (int) Configuration.LIFES.getValue();
    protected Case pos;

    public Vivant(Case pos) {
        this.pos = pos;
    }

    public int getVies() {
        return vies;
    }
    public Case getPos() { return pos; }
    public void setPos(Case pos) {
        this.pos = pos;
    }
    public void setVies(int vie) {
        this.vies = vie;
    }

    public boolean isDead() {
        return vies == 0;
    }

    public abstract void move(Direction d, EnsDeSalles grid);

    public abstract boolean isValidMove(Direction d, EnsDeSalles grid);


}
