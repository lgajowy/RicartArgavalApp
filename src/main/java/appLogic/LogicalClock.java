package appLogic;

public class LogicalClock {
    private static int value;

    public LogicalClock() {
        this.value = 0;
    }

    public static void increment(){
        value++;
    }

    public static int getValue() {
        return value;
    }

    public static void reset() {
        value = 0;
    }
}
