package Modele;

public class Epee extends Objet{
    private final int POWER = (int) Configuration.SWORD_POWER.getValue();
    public Epee(Case pos) {
        super(pos);
    }

    public void useObjet(Vivant v) {
        if (((Monstre)v).getVies() < POWER) {
            ((Monstre)v).setVies(0);
        } else {
            ((Monstre)v).setVies(((Monstre)v).getVies() - POWER);
        }
        System.out.println("Killed Monster!");
    }
}
