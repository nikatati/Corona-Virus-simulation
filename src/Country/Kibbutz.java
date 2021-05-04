package Country;
import Population.Person;
import Population.Sick;
import Location.Location;

public class Kibbutz extends Settlement
{

    //Default constructor
    public Kibbutz ()
    {
        super ();
    }

    //constructor get 2 variables
    public Kibbutz (String name,Location location,int currentPopulation,int maxPopulation,int vaccineDoses)
    {
        super(name, location, currentPopulation, maxPopulation, vaccineDoses);
    }


    public RamzorColor calculateRamzorGrade() {
        double color = 0.45 + (Math.pow(Math.pow(1.5, getRamzorColor().getFactor()) * (contagiousPercent()-0.4), 3));

        //Return the color of the city according to the conditions

        if (color < RamzorColor.GREEN.getFactor())
            return this.setRamzorColor(RamzorColor.GREEN);
        else if (color < RamzorColor.YELLOW.getFactor())
            return this.setRamzorColor(RamzorColor.YELLOW);
        else if (color < RamzorColor.ORANGE.getFactor())
            return this.setRamzorColor(RamzorColor.ORANGE);
        else
            return this.setRamzorColor(RamzorColor.RED);
    }

    @Override
    public String toString() {
        return "Kibbutz " + super.toString();
    }
}


