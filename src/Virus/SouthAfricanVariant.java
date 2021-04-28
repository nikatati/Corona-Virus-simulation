package Virus;
import Population.Person;
import Population.Sick;
import Location.Location;
import Population.Sick;
import Simulation.Clock;

public class SouthAfricanVariant implements IVirus{

    private static double p_d_18 = 0.05;
    private static double p_d_18_55 = 0.08;
    private static double p_d_55= 0.08;
    private static double p_c_18 = 0.6;
    private static double p_c_18_55 = 0.5;
    private static double p_c_55= 0.5;

    public  double contagionProbability(Person p){

        // Find the probability of a person contagion by his age

        double vpc_by_age; //the probability that person contagion by his age
        if (p.getAge() <= 18)
            vpc_by_age = p_c_18;
        else if (p.getAge() > 18 && p.getAge() <= 55)
            vpc_by_age = p_c_18_55;
        else
            vpc_by_age = p_c_55;

        return vpc_by_age*p.contagionProbability();

    }

    public boolean tryToContagion(Person p, Person p_unknown) {

        //check if the unknown is sick
        if (p_unknown instanceof Sick)
            return false;
        else
        {
            //calculate the probability that the unknown person infected
            //calculate the distance
            double distance = Math.sqrt(Math.pow(p.getLocation().getY() - p_unknown.getLocation().getY(),2)*Math.pow(p.getLocation().getX() - p_unknown.getLocation().getX(),2));
            double p_total = Math.min(1,0.14*(Math.exp(2-0.25*distance)))*contagionProbability(p_unknown);

            return p_total <= Math.random();

        }
    }

    public boolean tryToKill(Sick p) {

        // Find the probability of a person die by his age

        double vpd_by_age; //the probability that person die by his age
        if (p.getAge() <= 18)
            vpd_by_age = p_d_18;
        else if (p.getAge() > 18 && p.getAge() <= 55)
            vpd_by_age = p_d_18_55;
        else
            vpd_by_age = p_d_55;

        //Calculate the time from the moment of infection to now

        long t = p.getContagiousTime() - Clock.now();

        //Calculate the total probability to die

        double p_total= Math.max (0 , (vpd_by_age - 0.01 * vpd_by_age * (Math.pow (t-15,2))));

        return p_total <= Math.random();
    }

    @Override
    public String toString() {
        return "SouthAfricanVariant{" +
                "p_d_18=" + p_d_18 +
                ", p_d_18_55=" + p_d_18_55 +
                ", p_d_55=" + p_d_55 +
                ", p_c_18=" + p_c_18 +
                ", p_c_18_55=" + p_c_18_55 +
                ", p_c_55=" + p_c_55 +
                '}';
    }
}
