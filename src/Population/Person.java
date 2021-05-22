package Population;
import Country.City;
import Location.Point;
import Virus.IVirus;
import Country.Settlement;
import Simulation.Clock;

public abstract class Person
{
    private int age;
    private Point location;
    private Settlement settlement;

    //Default cont - settlement by default is city
    public Person(){
        this (0,new Point(),new City());
    }

    //cont
    public Person(int age,Point location, Settlement settlement)
    {
        this.age=age;
        this.location=location;
        this.settlement=settlement;
    }

    //abstract method that return the contagion Probability in diff. type of person
    public abstract double contagionProbability();

    //method that contagis the person in virus x
    /*public Sick contagion(IVirus x)
    {
        Sick s1= new Sick(age,location,settlement,Clock.now(),x);
        return s1;
    }*/

    public abstract Person contagion(IVirus x);

    //method that tells me if the person is sick. return false by defulte
    public boolean ifSick()
    {
        return false;
    }

    //return person age
    public int getAge() { return this.age; }

    //return person location
    public Point getLocation() { return location; }

    //return person settlement
    public Settlement getSettlement() { return settlement; }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", location=" + location +
                ", settlement=" + settlement +
                '}';
    }

    //copy cont fo person
    public Person (Person p)
    {
        age=p.age;
        location=p.location;
        settlement=p.settlement;
    }

}
