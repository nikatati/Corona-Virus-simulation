package Population;
import Country.City;
import Country.Settlement;
import Location.Location;
import Location.Point;
import Location.Size;
import Population.Person;
import Virus.IVirus;
import Simulation.Clock;

public class Vaccinated extends Person  //class comes to describe a vaccinated person
{                                       //Can get sick again after straining
    private long vaccinationTime;

    //Dcont
    public Vaccinated()
    {
        this (0,new Point(), new City(), 0);
    }

    //cont
    public Vaccinated(int age, Point location, Settlement settlement, long vaccinationTime)
    {
        super(age,location,settlement);
        this.vaccinationTime= vaccinationTime;
    }

    //return vaccinationTime attribute
    public long getvaccinationTime() { return vaccinationTime; }

    //The probability coefficient of illness is calculated as a dependency
    //t-> represents the number of days that elapsed from the moment of illness until the moment of examination
    public double contagionProbability()
    {
        long t=Clock.now()-getvaccinationTime();
        if (t<21)
        {
            return Math.min(1,0.56+0.15*Math.sqrt(21-t));
        }
        else
            return Math.max(0.05,1.05/(t-14));
    }

    //equals methode
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vaccinated that = (Vaccinated) o;
        return vaccinationTime == that.vaccinationTime;
    }

    @Override
    public String toString() {
        return "Vaccinated{" +
                "vaccinationTime=" + vaccinationTime +
                "} " + super.toString();
    }
}