package Country;

import Location.Location;
import Population.Person;

import java.util.List;


public class City extends Settlement
{

    public String Type= "City";


    @Override
    public RamzorColor calculateRamzorGrade()
    {
        double color = 0.2  * (Math.pow (4, 1.25 * super.contagiousPercent()));

        //Return the color of the city according to the conditions

        if (color <= 0.4)
            return this.setRamzorColor(RamzorColor.GREEN);
        if (color >0.4 && color<=0.6)
            return this.setRamzorColor(RamzorColor.YELLOW);
        if (color >0.6 && color <=0.8)
            return this.setRamzorColor(RamzorColor.ORANGE);
        if(color>0.8)
            return this.setRamzorColor(RamzorColor.RED);
        return null;
    }

    //Default constructor
    public City () {
        super ();
    }

    //constructor get 4 variables
    public City (String name, Location location, RamzorColor color, List<Person> healthy, List<Person> sick, int currentPopulation, int maxPopulation, int vaccineDoses)
    {
        super (name,location, color,  healthy, sick, currentPopulation, maxPopulation, vaccineDoses);
    }



    public String getType() { return Type; }

    @Override
    public String toString() { return "City " + super.toString(); }


}
