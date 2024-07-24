package model.game.enums;

public enum Chips {
    YELLOW(5),
    GREEN(10),
    RED(25),
    BLACK(50),
    BLUE(100),
    PINK(500),
    PURPLE(1000);

    private int value;

    Chips(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
