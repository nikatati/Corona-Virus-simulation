package Population;
import Country.Settlement;
import Location.Point;
import Virus.IVirus;


public class Sick extends Person    //A class that describes a sick person
{                                   //Sick person Can not get sick again
    private long contagiousTime;    //Time infected
    private IVirus virus;           //Virus type infected

    @Override
    public String toString() {
        return "Sick{" +
                "contagiousTime=" + contagiousTime +
                ", virus=" + virus +
                "} " + super.toString();
    }

    //default cont
    public Sick()
    {
        super();
        this.contagiousTime=0;
        this.virus=null;
    }

    //CONT
    public Sick(int age, Point location, Settlement settlement,long contagiousTime,IVirus virus)
    {
        super(age,location,settlement);
        this.contagiousTime= contagiousTime;
        this.virus = virus;
    }

    //copy cont
    public Sick (Sick s)
    {
        super();
        contagiousTime=s.contagiousTime;
        virus=s.virus;
    }

    //return the contagion Probability
    @Override
    public double contagionProbability() {
        return 1;
    }

    //method from the FATHER that contagis the sick person. will throw exeption b/s he is already sick
    public Person contagion(IVirus x)
    {
        System.out.println("Person cant be sick twice");
        return null;
    }

    //return contagious time attribute
    public long getContagiousTime() { return contagiousTime; }

    //method that makes the person to Convalescent
    public Person recover()
    {
        Convalescent c1= new Convalescent(getAge(),getLocation(),getSettlement(),this.virus);
        return c1;
    }

    //A method that guerrilla with probability P whether the person will die as a result of the virus
    public boolean tryToDie()
    {
        if (virus.tryToKill(this))
            return true;
        return false;
    }

    //equals methode
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sick sick = (Sick) o;
        return contagiousTime == sick.contagiousTime && java.util.Objects.equals(virus, sick.virus);
    }

    //return virus
    public IVirus getVirus (){
        return virus;
    }

    //bool methode tells me if the person is sick, must return true
    public boolean ifSick()
    {
        return true;
    }



}
