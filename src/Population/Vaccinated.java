package Population;
import Country.City;
import Country.Settlement;
import Location.Location;
import Location.Point;
import Location.Size;
import Population.Person;
import Virus.BritishVariant;
import Virus.ChineseVariant;
import Virus.IVirus;
import Simulation.Clock;
import Virus.SouthAfricanVariant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @Override
    public Person contagion(IVirus ivirus)
    {
        Sick s;
        Random rand = new Random();
        if (ivirus instanceof BritishVariant)
        {
            List<IVirus> list = new ArrayList<IVirus>(BritishVariant.getMutation());
            int x1=rand.nextInt(list.size());
            ivirus = list.get(x1);
            s=new Sick(this.getAge(),this.getLocation(),this.getSettlement(),Clock.now(),ivirus);
            return s;
        }
        if (ivirus instanceof ChineseVariant)
        {
            List<IVirus> list = new ArrayList<IVirus>(BritishVariant.getMutation());
            int x1=rand.nextInt(list.size());
            ivirus = list.get(x1);
            s=new Sick(this.getAge(),this.getLocation(),this.getSettlement(),Clock.now(),ivirus);
            return s;
        }
        if (ivirus instanceof SouthAfricanVariant)
        {
            List<IVirus> list = new ArrayList<IVirus>(BritishVariant.getMutation());
            int x1=rand.nextInt(list.size());
            ivirus = list.get(x1);
            s=new Sick(this.getAge(),this.getLocation(),this.getSettlement(),Clock.now(),ivirus);
            return s;
        }
        return s=new Sick(this.getAge(),this.getLocation(),this.getSettlement(),Clock.now(),ivirus);
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
    public String toString()
    {
        return "Vaccinated{" +
                "vaccinationTime=" + vaccinationTime +
                "} " + super.toString();
    }
}