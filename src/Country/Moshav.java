package Country;
import Population.Person;
import Location.Location;
import Location.Point;
import java.util.List;
import java.util.Random;
import Location.Size;

public class Moshav extends Settlement
{
    public String Type= "Moshav";
    //Default constructor
    public Moshav () {
        super ();
    }

    //constructor get 2 variables
    public Moshav (String name,Location location,RamzorColor color, List<Person> healthy,List<Person> sick,int currentPopulation,int maxPopulation,int vaccineDoses)
    {
        super (name,location,color, healthy, sick, currentPopulation, maxPopulation, vaccineDoses);
    }

    public String getType() { return Type; }

    @Override
    public String toString() {
        return "Moshav " + super.toString();
    }

    public RamzorColor CalculateRamzorGrade()
    {
        double color =  0.3+3 * (Math.pow(Math.pow(1.2, getRamzorColor().getFactor()) * (contagiousPercent()-0.35), 5));

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
    public RamzorColor calculateRamzorGrade()
    {
        double color =  0.3+3 * (Math.pow(Math.pow(1.2, getRamzorColor().getFactor()) * (contagiousPercent()-0.35), 5));

        //Return the color of the city according to the conditions

        if (color < RamzorColor.GREEN.getFactor())
            return this.setRamzorColor(RamzorColor.GREEN);
        else if (color < RamzorColor.YELLOW.getFactor())
            return this.setRamzorColor(RamzorColor.YELLOW);
        else if (color < RamzorColor.ORANGE.getFactor())
            return this.setRamzorColor(RamzorColor.ORANGE);
        else
            return this.setRamzorColor(RamzorColor.RED);    }
}
