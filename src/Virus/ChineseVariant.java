package Virus;
import Population.Person;
import Population.Sick;

import java.util.ArrayList;
import java.util.List;

public class ChineseVariant implements IVirus{

    private static double p_d_18 = 0.001;
    private static double p_d_18_55 = 0.05;
    private static double p_d_55= 0.1;
    private static double p_c_18 = 0.2;
    private static double p_c_18_55 = 0.5;
    private static double p_c_55= 0.7;
    private static boolean MutationBritish=true;
    private static boolean MutationChinese=true;
    private static boolean MutationSouthAfrican=true;


    private static int minContageTime=5;
    private List<IVirus> variant = new ArrayList<IVirus>();


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
        {
            return false;
        }

        if( ((Sick)(p)).getContagiousTime()<minContageTime)
        {
            return false;
        }

        //calculate the probability that the unknown person infected
        //calculate the distance
        double distance = Math.sqrt(Math.pow(p.getLocation().getY() - p_unknown.getLocation().getY(),2)*Math.pow(p.getLocation().getX() - p_unknown.getLocation().getX(),2));
        double p_total = Math.min(1,0.14*(Math.exp(2-0.25*distance)))*contagionProbability(p_unknown);

        return p_total <= Math.random();

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

        long t = p.getContagiousTime() - Simulation.Clock.now();

        //Calculate the total probability to die

        double p_total= Math.max (0 , (vpd_by_age - 0.01 * vpd_by_age * (Math.pow (t-15,2))));

        return p_total <= Math.random();
    }

    public void addMutation(IVirus virus)
    {
        variant.add(virus);
    }

    public List<IVirus> getMutation()
    {
        return variant;
    }

    public void removeMutation(IVirus virus)
    {
        variant.remove(virus);
    }

    public static boolean getMutationBritish() { return MutationBritish; }

    public static boolean getMutationChinese() { return MutationChinese; }

    public static boolean getMutationSouthAfrican() { return MutationSouthAfrican; }

    public static void setMutationBritish(boolean TF) { MutationBritish=TF; }

    public static void setMutationChinese(boolean TF) { MutationChinese=TF; }

    public static void setMutationSouthAfrican(boolean TF) { MutationSouthAfrican=TF; }


    @Override
    public String toString() {
        return "ChineseVariant{" +
                "p_d_18=" + p_d_18 +
                ", p_d_18_55=" + p_d_18_55 +
                ", p_d_55=" + p_d_55 +
                ", p_c_18=" + p_c_18 +
                ", p_c_18_55=" + p_c_18_55 +
                ", p_c_55=" + p_c_55 +
                '}';
    }
}
