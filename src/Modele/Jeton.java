package Modele;

public enum Jeton {
    WALL("#"),
    PLAYER("J"),
    VAMPIRE("V"),
    WEREWOLF("W"),
    TRESOR("T"),
    EPEE("E"),
    PORTE("P"),
    SORTIE("S"),
    CHEMIN(" ");

    private final String symbol;

    Jeton(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static Jeton getJeton(String symbol) {
        for (Jeton jeton : Jeton.values()) {
            if (jeton.symbol.equals(symbol)) {
                return jeton;
            }
        }
        return null;
    }
}
