package Population;
import Country.Settlement;
import Location.Point;
import Virus.BritishVariant;
import Virus.ChineseVariant;
import Virus.IVirus;
import Population.Person;
import Country.Settlement;
import Location.Point;
import Population.Vaccinated;
import Simulation.Clock;
import Virus.SouthAfricanVariant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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
