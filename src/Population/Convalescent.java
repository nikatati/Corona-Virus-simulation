package Population;
import Country.City;
import Country.Settlement;
import Location.Point;
import Virus.ChineseVariant;
import Virus.IVirus;
import Population.Person;
import Population.Sick;

import java.util.Objects;

public class Convalescent extends Person //The class comes to describe a recovering person
        //Recovering person may be sick again with probability coefficient of 2.0
{
    private IVirus virus;

    //Dcont
    public Convalescent()
    {
        this (0,new Point(), new City(), new ChineseVariant());
    }

    //cont
    public Convalescent(int age, Point location, Settlement settlement, IVirus virus)
    {
        super(age,location,settlement);
        this.virus= virus;
    }

    //Recovering person may be sick again with probability coefficient of 2.0
    public double contagionProbability() { return 0.2; }

    //to string methode- helps me to represent obj as a string
    public String toString()
    {
        return "Convalescent{" + "virus=" + virus + '}';
    }

    //  equals methode
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Convalescent that = (Convalescent) o;
        return Objects.equals(virus, that.virus);
    }


}
