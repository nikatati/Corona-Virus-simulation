package Simulation;

import Country.City;
import Country.Moshav;
import Country.Settlement;
import Location.Location;
import Location.Point;
import Location.Size;
import Population.*;
import Simulation.Clock;
import UI.Window;
import Virus.BritishVariant;
import Virus.ChineseVariant;
import Country.Map;
import IO.StatisticsFile;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFileChooser;
import java.io.File;
import IO.*;
import Virus.IVirus;
import Virus.SouthAfricanVariant;


public class Main
{

    public static void main(String[] args)
    {


    }






    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~סימולציה ראשונה~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //מעבר על כל היישובים, בחירה של כל חולה, עבורו בחירה אקראית של שישה אנשים מעל אותו יישוב, ולנסות להדביקם.
    private static void simulation1 (Map m)
    {
        ChineseVariant variant = new ChineseVariant(); // Choose kind of Corona variant

        //loop all the settlements
        for(int i = 0; i < m.getMapSize(); i++)
        {
            //loop all people at settlement
            for (int j = 0; j < m.getSettelmentFromMapByIndex(i).getCurrentPopulation(); j++)
            {
                if (m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(j).ifSick() == false)//if the man Healthy
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

                        if (m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(index).ifSick() == true) // the random person is sick
                        {
                            //Try to contagion by Probability contagion function
                            if (variant.tryToContagion(m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(index), m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(j)))
                                try
                                {
                                    Sick sick = new Sick(m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(j).getAge(),
                                            m.getSettelmentFromMapByIndex(i).getLocation().getPoint(),
                                            m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(j).getSettlement(),
                                            Clock.now(),
                                            variant);

                                    m.getSettelmentFromMapByIndex(i).getHealthyPeople().remove(j);
                                    m.getSettelmentFromMapByIndex(i).getSickPeople().add(sick);
                                }
                                catch (Exception e) { System.out.print(e); }
                        }
                    }

            }
        }
    }

    //------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------


    public static void Simulation2(Map m)   //סימולציה 2
    {
        Random randomx = new Random();
        Random randomy = new Random();
        IVirus Cvirus, Bvirus, Svirus;
        Cvirus = new ChineseVariant();
        Bvirus = new BritishVariant();
        Svirus = new SouthAfricanVariant();
        Sick sick_p=null;



        try
        {
            //In every settlement show 20% of random sick try to contage them
            for (int i = 0; i < m.getMapSize(); i++)
            {
                int population = (int) (m.getSettelmentFromMapByIndex(i).getCurrentPopulation());  //num of population

                int randomHealthyPeople = (int) (m.getSettelmentFromMapByIndex(i).getHealthyPeople().size() * 0.2);

                for (int j = 0; j < randomHealthyPeople; j++)      //run until 20% sick people from the settlement
                {
                    int x = randomx.nextInt(population);
                    int y = randomy.nextInt(3);

                    m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(x).toString();                 //דיגום לפני נסיון הדבקה

                    if (y == 0)
                    {
                        sick_p = new Sick(m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(j).getAge(),   // CONTAGION WITH CHINESE VARIANT
                                     m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(j).getLocation(),
                                     m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(j).getSettlement(),
                                     Clock.now(),
                                     Cvirus);

                    }
                    if (y == 1)
                    {
                        sick_p = new Sick(m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(j).getAge(),   // CONTAGION WITH BRITISH VARIANT
                                 m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(j).getLocation(),
                                 m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(j).getSettlement(),
                                 Clock.now(),
                                 Bvirus);
                    }
                    if (y == 2)
                    {
                        sick_p = new Sick(m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(j).getAge(),  // CONTAGION WITH SOUTH AFRICAN VARIANT
                                 m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(j).getLocation(),
                                 m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(j).getSettlement(),
                                 Clock.now(),
                                 Svirus);

                    }
                    //m.getSettelmentFromMapByIndex(i).getHealthyPeople().remove(j);
                    //m.getSettelmentFromMapByIndex(i).getSickPeople().add(sick_p);
                    //m.getSettelmentFromMapByIndex(i).sickPeople.remove(j);
                    //m.getSettelmentFromMapByIndex(i).healthyPeople.add(sick_p);
                    m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(x).toString();   //דיגום לאחר נסיון הדבקה
                }
            }

            //-------------------------------------------------------------------------------------------------------------------------------------------
            //-------------------------------------------------------------------------------------------------------------------------------------------


            //In every settlement show 3 not sick people and try to contage them
            int notSick=3;
            String x;
            IVirus v=null;

            for (int i = 0; i < m.getMapSize(); i++)
            {
                for(int j=0;j<m.getSettelmentFromMapByIndex(i).getSickPeople().size();j++)       //run until size if healty people list
                {
                    x = m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(j).toString();

                    for (int z=0;z<notSick;z++)    //runs until 3
                    {
                            if (x.contains("BritishVariant"))
                            {
                                v=new BritishVariant();      //BRITISH VARIANT
                            }
                            if (x.contains("SouthAfricanVariant"))
                            {
                                v=new SouthAfricanVariant();         //SouthAfrican Variant
                            }
                            if (x.contains("ChineseVariant"))
                            {
                                v=new ChineseVariant();          //CHINESE VARIANT
                            }
                            if (v.tryToContagion(m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(j),m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(j)))
                            {
                                Sick s1= new Sick(m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(j).getAge(),
                                                  m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(j).getLocation(),
                                                  m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(j).getSettlement(),
                                                  Clock.now(),
                                                  v);
                                m.getSettelmentFromMapByIndex(i).getSickPeople().add(m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(z));//add the person that got contage to the sick list
                                m.getSettelmentFromMapByIndex(i).getHealthyPeople().remove(m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(z));//remove the person that got contage from the healthy list
                            }
                    }

                }
            }

            //-------------------------------------------------------------------------------------------------------------------------------------------
            //-------------------------------------------------------------------------------------------------------------------------------------------
            //-------------------------------------------------------------------------------------------------------------------------------------------

            //make in every settlement all the sick people to convalescent if its been 25 days from the contage day
            for (int i = 0; i < m.getMapSize(); i++)
            {
                for (int j=0; j<m.getSettelmentFromMapByIndex(i).getSickPeople().size();j++)
                {
                    if ((Clock.now()-((Sick) m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(j)).getContagiousTime())>25)  //if past 25 days from the day the person got contagious
                    {
                        //need to make the sick person to convalescent so I will make new convalescent obj and
                        //put all the data of the sick person to the new obj
                        Convalescent As =  new Convalescent(((Sick)m.getSettelmentFromMapByIndex(i).getSickPeople().get(j)).getAge(),
                                ((Sick) m.getSettelmentFromMapByIndex(i).getSickPeople().get(j)).getLocation(),
                                ((Sick)m.getSettelmentFromMapByIndex(i).getSickPeople().get(j)).getSettlement(),
                                ((Sick) m.getSettelmentFromMapByIndex(i).getSickPeople().get(j)).getVirus());

                        m.getSettelmentFromMapByIndex(i).getSickPeople().remove(j);
                        m.getSettelmentFromMapByIndex(i).getHealthyPeople().add(As);
                    }
                }
            }
            //-------------------------------------------------------------------------------------------------------------------------------------------
            //-------------------------------------------------------------------------------------------------------------------------------------------

            //In every settlement show 3% of people that trying to random neighbor
            //Try to move to that neighbor settlement

            List <Person> sickANDhealthyPersonList=new ArrayList<>();
            for (int i = 0; i < m.getMapSize(); i++)
            {
                for (int j=0;j<m.getSettelmentFromMapByIndex(i).getHealthyPeople().size(); j++)
                {
                    sickANDhealthyPersonList.add(m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(j));
                }
                for (int j=m.getSettelmentFromMapByIndex(i).getHealthyPeople().size();j<m.getSettelmentFromMapByIndex(i).getSickPeople().size(); j++)
                {
                    sickANDhealthyPersonList.add(m.getSettelmentFromMapByIndex(i).getSickPeronByIndex(j));
                }
                //now the array contains all person- sick and healthy

                for (int j=0;j<sickANDhealthyPersonList.size()*0.03;j++)
                {
                    Random rn = new Random();
                    Random rp = new Random();
                    int a = rp.nextInt(sickANDhealthyPersonList.size());
                    int b = rn.nextInt(sickANDhealthyPersonList.get(a).getSettlement().getNeighbors().size());

                    sickANDhealthyPersonList.get(a).getSettlement().
                            transferPerson(sickANDhealthyPersonList.get(a), sickANDhealthyPersonList.get(a).getSettlement().getNeighbors().get(b));
                }
            }

            //-------------------------------------------------------------------------------------------------------------------------------------------
            //-------------------------------------------------------------------------------------------------------------------------------------------

            //ביישוב יש מנות חיסון ממתינות ואנשים מטיפוס Healthy ,יש לחסן אותם (וכמובן לעדכן את כמות המנות)
            // אין צורך להתעסק בעניין שתי מנות, כל אחד מקבל מנה אחת.

            for (int i = 0; i < m.getMapSize(); i++)
            {
                if (m.getSettelmentFromMapByIndex(i).getVaccineDoses()>0)   //it means that the settlement has vaccine Doses
                {
                    if(m.getSettelmentFromMapByIndex(i).getVaccineDoses()>=m.getSettelmentFromMapByIndex(i).getHealthyPeople().size())
                    {
                        for (int j = 0; j < m.getSettelmentFromMapByIndex(i).getHealthyPeople().size(); j++)
                        {
                            Vaccinated personGotVaccinated = new Vaccinated(m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(j).getAge(),
                                    m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(j).getLocation(),
                                    m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(j).getSettlement(),
                                    Clock.now());
                            m.getSettelmentFromMapByIndex(i).setVaccineDoses((m.getSettelmentFromMapByIndex(i).getVaccineDoses()) - 1);
                        }
                    }
                   else
                    {
                        for (int j = 0; j <m.getSettelmentFromMapByIndex(i).getVaccineDoses(); j++)
                        {
                            Vaccinated personGotVaccinated = new Vaccinated(m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(j).getAge(),
                                    m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(j).getLocation(),
                                    m.getSettelmentFromMapByIndex(i).getHealthyPeople().get(j).getSettlement(),
                                    Clock.now());
                            m.getSettelmentFromMapByIndex(i).setVaccineDoses((m.getSettelmentFromMapByIndex(i).getVaccineDoses()) - 1);
                        }
                    }

                }
            }
        }


        catch (Exception e)
        {
            System.out.print(e);
        }
    }


}