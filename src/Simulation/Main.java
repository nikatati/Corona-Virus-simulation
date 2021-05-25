package Simulation;

import Population.*;
import Virus.BritishVariant;
import Virus.ChineseVariant;
import Country.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Virus.IVirus;
import Virus.SouthAfricanVariant;


public class Main extends Thread {
    static int simulation_loop = 5;// time of simulation loop
    Map map;
    public static void main(String[] args) {

    }


    public static void dataInitialization(Map m)   //טעינת המפה
    {
       // this.map=m;
        System.out.println("make Sick start");
        Thread Ms = new MakeSick(m);
        Ms.run();
        System.out.println("make Sick finished");

        System.out.println("Try to contagion start");
        Thread Tc = new TryContagion(m);
        Tc.run();
        System.out.println("Try to contagion finished");

        System.out.println("Become healthy start");
        Thread Bh = new BecomeHealthy(m);
        Bh.run();
        System.out.println("Become healthy  finished");

        System.out.println("Try To Move start");
        Thread Ttm = new TryToMove(m);
        Ttm.run();
        System.out.println("Try To Move  finished");

        System.out.println("Get Vaccinated start");
        Thread Gv = new GetVaccinated(m);
        Gv.run();
        System.out.println("Get Vaccinated  finished");

        /*

        Random randomx = new Random();
        Random randomy = new Random();
        IVirus Cvirus, Bvirus, Svirus;
        Cvirus = new ChineseVariant();
        Bvirus = new BritishVariant();
        Svirus = new SouthAfricanVariant();


        try {
            //In every settlement show 20% of random sick try to contage them
            for (int i = 0; i < m.getMapSize(); i++) {
                int population = (int) (m.getSettelmentFromMapByIndex(i).getCurrentPopulation());  //num of population

                int randomSickPeople = (int) (m.getSettelmentFromMapByIndex(i).getSickPeople().size() * 0.2);

                for (int j = 0; j < randomSickPeople; j++)      //run until 20% sick people from the settlement
                {
                    int x = randomx.nextInt(population);
                    int y = randomy.nextInt(3);

                    m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(x).toString();                 //דיגום לפני נסיון הדבקה

                    if (y == 0) {
                        m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(x).contagion(Cvirus);      // CONTAGION WITH CHINESE VARIANT
                    }
                    if (y == 1) {
                        m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(x).contagion(Bvirus);         // CONTAGION WITH BRITISH VARIANT
                    }
                    if (y == 2) {
                        m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(x).contagion(Svirus);          // CONTAGION WITH SOUTH AFRICAN VARIANT
                    }
                    population -= 1;
                    m.getSettelmentFromMapByIndex(i).getHealthyPeople().remove(m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(x));
                    m.getSettelmentFromMapByIndex(i).getSickPeople().add(m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(x));
                    m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(x).toString();   //דיגום לאחר נסיון הדבקה
                }
            }

            //-------------------------------------------------------------------------------------------------------------------------------------------
            //-------------------------------------------------------------------------------------------------------------------------------------------

            //In every settlement show 3 not sick people and try to contage them
            int notSick = 3;
            String x;
            IVirus v = null;

            for (int i = 0; i < m.getMapSize(); i++) {
                for (int j = 0; j < m.getSettelmentFromMapByIndex(i).getSickPeople().size(); j++)       //run until size if healty people list
                {
                    x = m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(j).toString();

                    for (int z = 0; z < notSick; z++)    //runs until 3
                    {
                        if (x.contains("BritishVariant")) {
                            v = new BritishVariant();      //BRITISH VARIANT
                        }
                        if (x.contains("SouthAfricanVariant")) {
                            v = new SouthAfricanVariant();         //SouthAfrican Variant
                        }
                        if (x.contains("ChineseVariant")) {
                            v = new ChineseVariant();          //CHINESE VARIANT
                        }
                        if (v.tryToContagion(m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(j), m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(j))) {
                            Sick s1 = new Sick(m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(j).getAge(),
                                    m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(j).getLocation(),
                                    m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(j).getSettlement(),
                                    Clock.now(),
                                    v);
                            m.getSettelmentFromMapByIndex(i).getSickPeople().add(m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(z));//add the person that got contage to the sick list
                            m.getSettelmentFromMapByIndex(i).getHealthyPeople().remove(m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(z));//remove the person that got contage from the healthy list
                        }
                    }

                }
            }
            //-------------------------------------------------------------------------------------------------------------------------------------------
            //-------------------------------------------------------------------------------------------------------------------------------------------

            //make in every settlement all the sick people to convalescent if its been 25 days from the contage day
            for (int i = 0; i < m.getMapSize(); i++) {
                for (int j = 0; j < m.getSettelmentFromMapByIndex(i).getSickPeople().size(); j++) {
                    if ((Clock.now() - ((Sick) m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(j)).getContagiousTime()) > 25)  //if past 25 days from the day the person got contagious
                    {
                        //need to make the sick person to convalescent so I will make new convalescent obj and
                        //put all the data of the sick person to the new obj
                        Convalescent As = new Convalescent(m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(j).getAge(),
                                m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(j).getLocation(),
                                m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(j).getSettlement(),
                                ((Sick) m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(j)).getVirus());

                        m.getSettelmentFromMapByIndex(i).getSickPeople().remove(m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(j));
                        m.getSettelmentFromMapByIndex(i).getHealthyPeople().add(As);
                    }
                }
            }
            //-------------------------------------------------------------------------------------------------------------------------------------------
            //-------------------------------------------------------------------------------------------------------------------------------------------

            //In every settlement show 3% of people that trying to random neighbor
            //Try to move to that neighbor settlement

            List<Person> sickANDhealthyPersonList = new ArrayList<>();
            for (int i = 0; i < m.getMapSize(); i++) {
                for (int j = 0; j < m.getSettelmentFromMapByIndex(i).getHealthyPeople().size(); j++) {
                    sickANDhealthyPersonList.add(m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(j));
                }
                for (int j = m.getSettelmentFromMapByIndex(i).getHealthyPeople().size(); j < m.getSettelmentFromMapByIndex(i).getSickPeople().size(); j++) {
                    sickANDhealthyPersonList.add(m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(j));
                }
                //now the array contains all person- sick and healthy

                for (int j = 0; j < sickANDhealthyPersonList.size() * 0.03; j++) {
                    Random rn = new Random();
                    Random rp = new Random();
                    int a = rp.nextInt(sickANDhealthyPersonList.size());
                    int b = rn.nextInt(sickANDhealthyPersonList.get(a).getSettlement().getNeighbors().size());
                    sickANDhealthyPersonList.
                            get(a).
                            getSettlement().
                            transferPerson(sickANDhealthyPersonList.get(a)
                                    , sickANDhealthyPersonList.get(a).getSettlement().getNeighbors().get(b));
                }
            }

            //-------------------------------------------------------------------------------------------------------------------------------------------
            //-------------------------------------------------------------------------------------------------------------------------------------------

            //inoculate (לחסן) healthy people and update the
            for (int i = 0; i < m.getMapSize(); i++) {
                if (m.getSettelmentFromMapByIndex(i).getVaccineDoses() > 0)   //it means that the settlement has vaccine Doses
                {
                    if (m.getSettelmentFromMapByIndex(i).getVaccineDoses() >= m.getSettelmentFromMapByIndex(i).getHealthyPeople().size()) {
                        for (int j = 0; j < m.getSettelmentFromMapByIndex(i).getHealthyPeople().size(); j++) {
                            Vaccinated personGotVaccinated = new Vaccinated(m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(j).getAge(),
                                    m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(j).getLocation(),
                                    m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(j).getSettlement(),
                                    Clock.now());
                            m.getSettelmentFromMapByIndex(i).setVaccineDoses((m.getSettelmentFromMapByIndex(i).getVaccineDoses()) - 1);
                        }
                    } else {
                        for (int j = 0; j < m.getSettelmentFromMapByIndex(i).getVaccineDoses(); j++) {
                            Vaccinated personGotVaccinated = new Vaccinated(m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(j).getAge(),
                                    m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(j).getLocation(),
                                    m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(j).getSettlement(),
                                    Clock.now());
                            m.getSettelmentFromMapByIndex(i).setVaccineDoses((m.getSettelmentFromMapByIndex(i).getVaccineDoses()) - 1);
                        }
                    }

                }
            }
        } catch (Exception e) {
            System.out.print(e);
        }*/
    }

    static class MakeSick extends Thread
    {
        public MakeSick(Map m) {
            System.out.println("make Sick start");
            Random randomx = new Random();
            Random randomy = new Random();
            IVirus Cvirus, Bvirus, Svirus;
            Cvirus = new ChineseVariant();
            Bvirus = new BritishVariant();
            Svirus = new SouthAfricanVariant();


            //In every settlement show 20% of random sick try to contage them
            for (int i = 0; i < m.getMapSize(); i++) {
                int population = (int) (m.getSettelmentFromMapByIndex(i).getCurrentPopulation());  //num of population

                int randomSickPeople = (int) (m.getSettelmentFromMapByIndex(i).getSickPeople().size() * 0.2);

                for (int j = 0; j < randomSickPeople; j++)      //run until 20% sick people from the settlement
                {
                    int x = randomx.nextInt(population);
                    int y = randomy.nextInt(3);

                    m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(x).toString();                 //דיגום לפני נסיון הדבקה

                    if (y == 0) {
                        m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(x).contagion(Cvirus);      // CONTAGION WITH CHINESE VARIANT
                    }
                    if (y == 1) {
                        m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(x).contagion(Bvirus);         // CONTAGION WITH BRITISH VARIANT
                    }
                    if (y == 2) {
                        m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(x).contagion(Svirus);          // CONTAGION WITH SOUTH AFRICAN VARIANT
                    }
                    population -= 1;
                    m.getSettelmentFromMapByIndex(i).getHealthyPeople().remove(m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(x));
                    m.getSettelmentFromMapByIndex(i).getSickPeople().add(m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(x));
                    m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(x).toString();   //דיגום לאחר נסיון הדבקה
                }
            }
        }

        //System.out.println("make Sick start");

       //public void MakeSick1(Map m) {

       }


}
    class TryContagion extends Thread
    {
        public TryContagion(Map m) {
            //In every settlement show 3 not sick people and try to contage them
            int notSick = 3;
            String x;
            IVirus v = null;

            for (int i = 0; i < m.getMapSize(); i++) {
                for (int j = 0; j < m.getSettelmentFromMapByIndex(i).getSickPeople().size(); j++)       //run until size if healty people list
                {
                    x = m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(j).toString();

                    for (int z = 0; z < notSick; z++)    //runs until 3
                    {
                        if (x.contains("BritishVariant")) {
                            v = new BritishVariant();      //BRITISH VARIANT
                        }
                        if (x.contains("SouthAfricanVariant")) {
                            v = new SouthAfricanVariant();         //SouthAfrican Variant
                        }
                        if (x.contains("ChineseVariant")) {
                            v = new ChineseVariant();          //CHINESE VARIANT
                        }
                        if (v.tryToContagion(m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(j), m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(j))) {
                            Sick s1 = new Sick(m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(j).getAge(),
                                    m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(j).getLocation(),
                                    m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(j).getSettlement(),
                                    Clock.now(),
                                    v);
                            m.getSettelmentFromMapByIndex(i).getSickPeople().add(m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(z));//add the person that got contage to the sick list
                            m.getSettelmentFromMapByIndex(i).getHealthyPeople().remove(m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(z));//remove the person that got contage from the healthy list
                        }
                    }

                }
            }
        }

      //  public static void TryContegion1(Map m) {
          //  Random randomx = new Random();
           // Random randomy = new Random();
           // IVirus Cvirus, Bvirus, Svirus;
           // Cvirus = new ChineseVariant();
           // Bvirus = new BritishVariant();
           // Svirus = new SouthAfricanVariant();

    }


    class BecomeHealthy extends Thread
    {
        public  BecomeHealthy(Map m) {
            /*Random randomx = new Random();
            Random randomy = new Random();
            IVirus Cvirus, Bvirus, Svirus;
            Cvirus = new ChineseVariant();
            Bvirus = new BritishVariant();
            Svirus = new SouthAfricanVariant();*/

            //make in every settlement all the sick people to convalescent if its been 25 days from the contage day
            for (int i = 0; i < m.getMapSize(); i++) {
                for (int j = 0; j < m.getSettelmentFromMapByIndex(i).getSickPeople().size(); j++) {
                    if ((Clock.now() - ((Sick) m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(j)).getContagiousTime()) > 25)  //if past 25 days from the day the person got contagious
                    {
                        //need to make the sick person to convalescent so I will make new convalescent obj and
                        //put all the data of the sick person to the new obj
                        Convalescent As = new Convalescent(m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(j).getAge(),
                                m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(j).getLocation(),
                                m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(j).getSettlement(),
                                ((Sick) m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(j)).getVirus());

                        m.getSettelmentFromMapByIndex(i).getSickPeople().remove(m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(j));
                        m.getSettelmentFromMapByIndex(i).getHealthyPeople().add(As);
                    }
                }
            }
        }
    }
    class TryToMove extends Thread
    {
        public TryToMove(Map m) {
            /*Random randomx = new Random();
            Random randomy = new Random();
            IVirus Cvirus, Bvirus, Svirus;
            Cvirus = new ChineseVariant();
            Bvirus = new BritishVariant();
            Svirus = new SouthAfricanVariant();*/

            //In every settlement show 3% of people that trying to random neighbor
            //Try to move to that neighbor settlement

            List<Person> sickANDhealthyPersonList = new ArrayList<>();
            for (int i = 0; i < m.getMapSize(); i++) {
                for (int j = 0; j < m.getSettelmentFromMapByIndex(i).getHealthyPeople().size(); j++) {
                    sickANDhealthyPersonList.add(m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(j));
                }
                for (int j = m.getSettelmentFromMapByIndex(i).getHealthyPeople().size(); j < m.getSettelmentFromMapByIndex(i).getSickPeople().size(); j++) {
                    sickANDhealthyPersonList.add(m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(j));
                }
                //now the array contains all person- sick and healthy

                for (int j = 0; j < sickANDhealthyPersonList.size() * 0.03; j++) {
                    Random rn = new Random();
                    Random rp = new Random();
                    int a = rp.nextInt(sickANDhealthyPersonList.size());
                    int b = rn.nextInt(sickANDhealthyPersonList.get(a).getSettlement().getNeighbors().size());
                    sickANDhealthyPersonList.
                            get(a).
                            getSettlement().
                            transferPerson(sickANDhealthyPersonList.get(a)
                                    , sickANDhealthyPersonList.get(a).getSettlement().getNeighbors().get(b));
                }
            }
        }
    }
    class GetVaccinated extends Thread
    {
        public GetVaccinated(Map m) {
            /*Random randomx = new Random();
            Random randomy = new Random();
            IVirus Cvirus, Bvirus, Svirus;
            Cvirus = new ChineseVariant();
            Bvirus = new BritishVariant();
            Svirus = new SouthAfricanVariant();*/

            for (int i = 0; i < m.getMapSize(); i++) {
                if (m.getSettelmentFromMapByIndex(i).getVaccineDoses() > 0)   //it means that the settlement has vaccine Doses
                {
                    if (m.getSettelmentFromMapByIndex(i).getVaccineDoses() >= m.getSettelmentFromMapByIndex(i).getHealthyPeople().size()) {
                        for (int j = 0; j < m.getSettelmentFromMapByIndex(i).getHealthyPeople().size(); j++) {
                            Vaccinated personGotVaccinated = new Vaccinated(m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(j).getAge(),
                                    m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(j).getLocation(),
                                    m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(j).getSettlement(),
                                    Clock.now());
                            m.getSettelmentFromMapByIndex(i).setVaccineDoses((m.getSettelmentFromMapByIndex(i).getVaccineDoses()) - 1);
                        }
                    } else {
                        for (int j = 0; j < m.getSettelmentFromMapByIndex(i).getVaccineDoses(); j++) {
                            Vaccinated personGotVaccinated = new Vaccinated(m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(j).getAge(),
                                    m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(j).getLocation(),
                                    m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(j).getSettlement(),
                                    Clock.now());
                            m.getSettelmentFromMapByIndex(i).setVaccineDoses((m.getSettelmentFromMapByIndex(i).getVaccineDoses()) - 1);
                        }
                    }

                }
            }

        }
    }


   /* private static void simulation (Map m)
    {
        ChineseVariant variant = new ChineseVariant(); // Choose kind of Corona variant

        //loop all the settlements
        for(int i = 0; i < m.getMapSize(); i++)
        {
            //loop all people at settlement
            for (int j = 0; j < m.getSettelmentFromMapByIndex(i).getCurrentPopulation(); j++)
            {
                if (m.getSettelmentFromMapByIndex(i).getPeronByIndex(j).ifSick() == false)//if the man Healthy
                    for (int k = 0; k < 6; k++)   //try 6 times so contagion
                    {
                        int index = 0;
                        boolean flag = false;
                        while (!flag)
                         {
                            Random x = new Random();
                            index = x.nextInt(m.getSettelmentFromMapByIndex(i).getCurrentPopulation());// get random person
                            if (index != j)// its not the same healthy person
                                flag = true;
                        }

                        if (m.getSettelmentFromMapByIndex(i).getPeronByIndex(index).ifSick() == true) // the random person is sick
                        {
                            //Try to contagion by Probability contagion function
                            if (variant.tryToContagion(m.getSettelmentFromMapByIndex(i).getPeronByIndex(index), m.getSettelmentFromMapByIndex(i).getPeronByIndex(j)))
                                try {
                                    Sick sick = new Sick(m.geByIndtSettelmentFromMapByIndex(i).getPeronByIndex(j).contagion(new ChineseVariant()));
                                    m.getSettelmentFromMapByI     ndex(i).removePerson(m.getSettelmentFromMapByIndex(i).getPeronByIndex(j));
                                    m.getSettelmentFromMapByI     ndex(i).addPerson(sick);
                                } catch (Exception e) {
                                    System.out.print(e);
                                }
                        }
                    }

            }
        }
    }*/
