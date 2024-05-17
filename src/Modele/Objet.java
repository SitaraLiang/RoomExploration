package Modele;

public abstract class Objet {
    private Case pos;

    public Objet(Case pos) {
        this.pos = pos;
    }
    public Case getPos() {
        return pos;
    }
    public abstract void useObjet(Vivant v);
}
