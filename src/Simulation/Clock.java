package Simulation;

public final class Clock
{
    private static long time;
    private static long tick_per_day;


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
    public String toString()
    {
        return "Clock{" + "time=" + time + '}';
    }

    public static long Num_of_days(int start)
    {
        return Math.round((Clock.now()-start)/tick_per_day);
    }

    public static long getTime() { return time; }

    public static void setTime(long time) { Clock.time = time; }

    public static long getTick_per_day() { return tick_per_day; }

    public static void setTick_per_day(long tick_per_day) { Clock.tick_per_day = tick_per_day; }
}