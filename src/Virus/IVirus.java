package Virus;
import Population.Person;
import Population.Sick;

public interface IVirus
{
    public  double contagionProbability(Person p);
    public  boolean tryToContagion(Person p_sick, Person p_unknown);
    public  boolean tryToKill(Sick p);


}
