package IO;

import Country.*;
import Location.Location;
import Population.Healthy;
import Population.Person;

import java.awt.image.Kernel;
import java.util.List;

import static IO.SimulationFile.determiningAge;

public class Factory
{
    public Settlement factoryCreateSettlement(String type, String name, Location l, RamzorColor color, List<Person> healthy, List<Person> sick , int currentPopulation, int vaccineDoses)
    {
        if (type.equals("City"))
        {
            City city=new City(name, l, color,  healthy, sick, currentPopulation, (int)(currentPopulation*1.3),vaccineDoses);
            for (int i=0;i<currentPopulation;i++)
            {
                Healthy h =new Healthy(determiningAge(),city.randomLocation(),city);
                city.addPerson(h);
            }
            return city;
        }
        else if (type.equals("Moshav"))
        {
            Moshav moshav=new Moshav(name, l, color,  healthy, sick, currentPopulation, (int)(currentPopulation*1.3),vaccineDoses);
            for (int i=0;i<currentPopulation;i++)
            {
                Healthy h =new Healthy(determiningAge(),moshav.randomLocation(),moshav);
                moshav.addPerson(h);
            }
            return moshav;
        }
        else
        {
            Kibbutz kibbutz=new Kibbutz(name, l, color,  healthy, sick, currentPopulation, (int)(currentPopulation*1.3),vaccineDoses);
            for (int i=0;i<currentPopulation;i++)
            {
                Healthy h =new Healthy(determiningAge(),kibbutz.randomLocation(),kibbutz);
                kibbutz.addPerson(h);
            }
            return kibbutz;
        }

    }

}
