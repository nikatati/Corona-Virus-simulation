package Country;
import Population.Person;
import Population.Sick;
import Location.Location;

import java.util.List;

public class Kibbutz extends Settlement
{
    public String Type= "Kibbutz";

    //Default constructor
    public Kibbutz ()
    {
        super ();
    }

    //constructor get 2 variables
    public Kibbutz (String name, Location location, RamzorColor color, List<Person> healthy, List<Person> sick, int currentPopulation, int maxPopulation, int vaccineDoses)
    {
        super(name, location, color,healthy, sick, currentPopulation, maxPopulation, vaccineDoses);
    }


    public String getType() { return Type; }

    @Override
    public String toString() {
        return "Kibbutz " + super.toString();
    }


    @Override
    public RamzorColor calculateRamzorGrade()
    {
        double color = 0.45 + (Math.pow(Math.pow(1.5, getRamzorColor().getFactor()) * (contagiousPercent()-0.4), 3));

        if (color <= 0.4)
            return this.setRamzorColor(RamzorColor.GREEN);
        if (color > 0.4 && color <= 0.6)
            return this.setRamzorColor(RamzorColor.YELLOW);
        if (color > 0.6 && color <= 0.8)
            return this.setRamzorColor(RamzorColor.ORANGE);
        if (color > 0.8)
            return this.setRamzorColor(RamzorColor.RED);
        return null;
    }
}


