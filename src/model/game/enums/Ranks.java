package model.game.enums;

public enum Ranks {
    ACE(1, 11),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(10),
    QUEEN(10),
    KING(10);

    private int value;
    private int softValue;

    Ranks(int value){
        this.value = value;
        this.softValue = value;
    }

    Ranks(int value, int softValue){
        this.value = value;
        this.softValue = softValue;
    }

    public int getValue() {
        return value;
    }

    public int getSoftValue() {
        return softValue;
    }
}
