package appLogic;

public class LogicalClock {
    private int value;

    public LogicalClock() {
        this.value = 0;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void reset() {
        value = 0;
    }
}
