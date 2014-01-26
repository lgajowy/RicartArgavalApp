package appLogic;

public class LogicalClock {
    private static long value = 0;

    public static void increment() {
        value++;
    }

    public static void synchronize(long logicalTime) {
        if (value < logicalTime) {
            value = logicalTime;
        }
    }

    public static long getValue() {
        return value;
    }

    public static void reset() {
        value = 0;
    }
}
