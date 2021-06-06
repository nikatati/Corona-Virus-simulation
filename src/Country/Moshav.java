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


    @Override
    public RamzorColor calculateRamzorGrade()
    {
        double color = 0.3 + 3 * (Math.pow(Math.pow(1.2, getRamzorColor().getFactor()) * (contagiousPercent() - 0.35), 5));

        if (color <= 0.4)
            return RamzorColor.GREEN;
        if (color >0.4 && color<=0.6)
            return RamzorColor.YELLOW;
        if (color >0.6 && color <=0.8)
            return RamzorColor.ORANGE;
        if(color>0.8)
            return RamzorColor.RED;
        return null;
    }



}
