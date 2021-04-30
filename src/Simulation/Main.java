package Simulation;

import Country.Moshav;
import Country.Settlement;
import Location.Location;
import Location.Point;
import Location.Size;
import Population.Healthy;
import Population.Person;
import Population.Sick;
import Simulation.Clock;
import Virus.BritishVariant;
import Virus.ChineseVariant;
import Country.Map;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFileChooser;
import java.io.File;
import IO.*;
import Virus.SouthAfricanVariant;


public class Main
{
    static int simulation_loop = 5;// time of simulation loop



    public static void main(String[] args)
    {

        Map m = new Map();

       m = SimulationFile.SimulationFile(); //Load data from File to map
        dataInitialization(m);// //contagion 0.01% from the people at the settlement
        for (int i = 0; i < simulation_loop; i++)
        {
            simulation(m);// Play the simulation
            Clock.nextTick();
        }

    }

    private static void simulation(Map m) {

    }

    private static void dataInitialization(Map m) throws Exception {
        Random randomx =new Random();
        int x =0;
        Random randomy =new Random();
        int y =0;
        ChineseVariant C =new ChineseVariant();
        BritishVariant B =new BritishVariant();
        SouthAfricanVariant S = new SouthAfricanVariant();



        for (int i = 0; i < m.getMapSize(); i++)
        {
            int population = (int) (m.getSettelmentFromMapByIndex(i).getCurrentPopulation());

            int sick = (int) (m.getSettelmentFromMapByIndex(i).getCurrentPopulation() * 0.2);

            for(int j=0;j<sick;j++)
            {
                x= randomx.nextInt(population);
                y= randomy.nextInt(3);
                if(y==0)
                {
                    m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(x).contagion(C);      // CONTAGION WITH CHINESE VARIANT
                }
                if(y==1)
                {
                    m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(x).contagion(B);         // CONTAGION WITH BRITISH VARIANT
                }
                if(y==2)
                {
                    m.getSettelmentFromMapByIndex(i).getHealthyPeronByIndex(x).contagion(S);          // CONTAGION WITH SOUTH AFRICAN VARIANT
                }
                population--;

            }


        }
    }

    /*private static void dataInitialization (Map m)
    {
        //contagion 0.01% from the people at the settlement
        for (int i = 0; i < m.getMapSize(); i++)
        {
            for (int j = 0; j < m.getSettelmentFromMapByIndex(i).getCurrentPopulation() * 0.01; j++)
             {
                if (m.getSettelmentFromMapByIndex(i).getPeronByIndex(j).ifSick() == false) // if the man Healthy
                {
                    try
                    {
                        //create new Sick Obj, copy the data from the healthy person and add it to the settlement
                        Sick sick = new Sick(m.getSettelmentFromMapByIndex(i).getPeronByIndex(j).contagion(new ChineseVariant()));
                        m.getSettelmentFromMapByIndex(i).removePerson(m.getSettelmentFromMapByIndex(i).getPeronByIndex(j));
                        m.getSettelmentFromMapByIndex(i).addPerson(sick);
                    }
                     catch (Exception e)
                     {
                        System.out.print(e);
                     }

                }
            }

        }
    }*/

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
                                    Sick sick = new Sick(m.getSettelmentFromMapByIndex(i).getPeronByIndex(j).contagion(new ChineseVariant()));
                                    m.getSettelmentFromMapByIndex(i).removePerson(m.getSettelmentFromMapByIndex(i).getPeronByIndex(j));
                                    m.getSettelmentFromMapByIndex(i).addPerson(sick);
                                } catch (Exception e) {
                                    System.out.print(e);
                                }
                        }
                    }

            }
        }
    }*/
}