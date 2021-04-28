package Population;
import Country.Settlement;
import Location.Point;
import Virus.IVirus;
import Population.Person;
import Country.Settlement;
import Location.Point;
import Population.Vaccinated;
import Simulation.Clock;



public class Healthy extends Person //המחלקה באה לתאר אדם בריא שעדיין לא התחסן
{
    //Dcont
    public Healthy(){
        super();
    }

    //cont
    public Healthy(int age, Point location, Settlement settlement)

    {
        super(age,location,settlement);
    }

    //contagion Probability return 1 by default
    public double contagionProbability() { return 1; }

    //Making a healthy person vaccinated
    public Person vaccinate()
    {
        Vaccinated v1= new Vaccinated(getAge(),getLocation(),getSettlement(),Clock.now());
        return v1;
    }

    @Override
    public String toString() {
        return "Healthy{} " + super.toString();
    }

    //equals methode
    @Override
    public boolean equals(Object obj)
    {
        return super.equals(obj);
    }

}
