package Modele;

public enum Configuration {
    GAME_TEMPLATE("assets/template.txt"),
    MONSTER_SPEED(200),
    WOLF_POWER(1),
    LIFES(5),
    TREASURE_ENERGY(3),
    SWORD_POWER(5),
    MAX_LEVEL(3);

    private final Object value;
    Configuration(Object o) {
        this.value = o;
    }

    public Object getValue() {
        return value;
    }


}
