package Simulation;

public final class Clock {

    private static long time;

    private Clock ()
    { // private constructor
        time = 0;
    }

    public static long now ()
    {
        return time;
    }

    public static void nextTick ()
    {
        time += 1;
    }

    @Override
    public String toString() {
        return "Clock{" +
                "time=" + time +
                '}';
    }
}