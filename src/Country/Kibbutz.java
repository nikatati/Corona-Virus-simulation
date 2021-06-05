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

    public RamzorColor CalculateRamzorGrade()
    {
        double color = 0.45 + (Math.pow(Math.pow(1.5, getRamzorColor().getFactor()) * (contagiousPercent()-0.4), 3));

        //Return the color of the city according to the conditions

        if (color < RamzorColor.GREEN.getFactor())
            return RamzorColor.GREEN;
        else if (color < RamzorColor.YELLOW.getFactor())
            return RamzorColor.YELLOW;
        else if (color < RamzorColor.ORANGE.getFactor())
            return RamzorColor.ORANGE;
        else
            return RamzorColor.RED;
    }

    @Override
    public RamzorColor calculateRamzorGrade()
    {
        double color = 0.45 + (Math.pow(Math.pow(1.5, getRamzorColor().getFactor()) * (contagiousPercent()-0.4), 3));

        //Return the color of the city according to the conditions

        if (color < RamzorColor.GREEN.getFactor())
            return RamzorColor.GREEN;
        else if (color < RamzorColor.YELLOW.getFactor())
            return RamzorColor.YELLOW;
        else if (color < RamzorColor.ORANGE.getFactor())
            return RamzorColor.ORANGE;
        else
            return RamzorColor.RED;
    }
}


