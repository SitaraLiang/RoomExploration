package Vue;

public enum MusicSamples {
    BackgroundMusic("assets/seven.wav"),
    DuringTheGame("assets/3D.wav"),
    ClickButton("assets/choose.wav"),
    OperatedButton("assets/click.wav"),
    WOLF_BITES("assets/wolf_roar.wav"),
    VAMPIRE_ATTACK("assets/vampire.wav"),
    USE_TREASURE("assets/collect.wav"),
    COLLECT_ITEM("assets/collect.wav"),
    USE_SWORD("assets/killmonster.wav"),
    ENDGAME("assets/goodgame.wav");
    private final String filepath;
    MusicSamples(String filepath) {
        this.filepath = filepath;
    }

    public String getFilepath() {
        return filepath;
    }

}
