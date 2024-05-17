package Modele;

public enum Key{
    UP("H"),
    RIGHT("J"),
    DOWN("K"),
    LEFT("L"),
    USE_TREASOR("X"),
    USE_EPEE("V"),
    COLLECT("C");

    private final String symbol;
    Key(String symbol) {
        this.symbol = symbol;
    }

}

