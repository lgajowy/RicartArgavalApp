package appLogic;

public class LogicalClock {
    private static long value = 0;
    private static long valueBeforeSynchronization = 0;

    public static long getValueBeforeSynchronization() {
        return valueBeforeSynchronization;
    }

    private static void setValueBeforeSynchronization(long valueBeforeSynchronization) {
        LogicalClock.valueBeforeSynchronization = valueBeforeSynchronization;
    }

    public static void increment() {
        valueBeforeSynchronization = value;
        value++;
    }

    public static void synchronizeOrIncrementClock(long logicalTime) {
        setValueBeforeSynchronization(value);
        if (value < logicalTime) {
            value = logicalTime;
        } else {
            increment();
        }
    }

    public static long getValue() {
        return value;
    }

}
