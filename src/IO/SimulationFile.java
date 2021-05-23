package IO;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import Country.Moshav;
import Location.Location;
import Location.Point;
import Location.Size;
import Country.Kibbutz;
import Country.Settlement;
import Country.City;
import java.util.*;
import Country.Map;

import javax.swing.*;

public class SimulationFile
{
    private final static double x = 1.3;

    public static Map SimulationFile() {

        ArrayList<Settlement> tempStel = new ArrayList<>();
        ArrayList<String> gonnaBeNeighbor = new ArrayList<>();

        Map simulation_map = new Map();            //מערך של יישובים
        //String file_path = new String();
        //choose a file from documents
        boolean flag1 = true; //no file selected
        String file_path = null;

        while (flag1)
        {
            file_path = chooseFile();
            if (!Objects.equals("No file selected", file_path)) // File chosen
                flag1 = false; // while loop over
        }
        try
        {
            File myObj = new File(file_path);
            Scanner myReader = new Scanner(myObj);
            String str = new String();
            while (myReader.hasNextLine())
            {
                str = myReader.nextLine();
                // read one line from file
                str = str.replaceAll("\\s", "");        //remove all " " from string
                String[] words = str.split(";");                  // split the string to words by ; מערך של שורה אחת

                if (words[0].equals("#"))
                {
                    continue;

                }
                else
                {
                    simulation_map.addSettelmentToMap(createNewSettlementByType
                            (words[0],
                             words[1],
                             new Location(new Point(Integer.parseInt(words[2]), Integer.parseInt(words[3])),
                             new Size(Integer.parseInt(words[4]), Integer.parseInt(words[5]))),
                             Integer.parseInt(words[6]),
                             Integer.parseInt(words[7])));
                   // str = myReader.nextLine();
                }

            }

            myReader.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        // check all the lines with the neighbors and put in right place

        int size = simulation_map.getMapSize();
        for (int i = 0; i < size; i++)
        {
            try
            {
                File myObj = new File(String.valueOf(file_path));
                Scanner myReader1 = new Scanner(myObj);
                String str = new String();
                while (myReader1.hasNextLine())
                {
                    str = myReader1.nextLine();
                    // read one line from file
                    str = str.replaceAll("\\s", "");        //remove all " " from string
                    String[] words = str.split(";");


                    if (words[0].equals("#"))
                    {
                        if (simulation_map.getSettelmentFromMapByIndex(i).getName().equals(words[1])) {
                            for (int j = i + 1; j < size; j++) {
                                if (simulation_map.getSettelmentFromMapByIndex(j).getName().equals(words[2])) {
                                    simulation_map.getSettelmentFromMapByIndex(i).addNeighbor(simulation_map.getSettelmentFromMapByIndex(j));            //add neighbor B to A
                                    simulation_map.getSettelmentFromMapByIndex(j).addNeighbor(simulation_map.getSettelmentFromMapByIndex(i));            //add neighbor A to B
                                }

                            }

                        }

                    }
                 }
                return simulation_map;

            }
            catch (FileNotFoundException e)
            {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        }
        return null;
    }



    private static Settlement createNewSettlementByType (String type, String name, Location l, int currentPopulation,int vaccineDoses)
    {

        if (Objects.equals("City", type))
        {
            //addPeopleToSettlementByAmount(c, people_amount);
            return new City(name, l,currentPopulation, (int)(currentPopulation*x),vaccineDoses);
        }

        else if (Objects.equals("Moshav", type))
        {
            //addPeopleToSettlementByAmount(m, people_amount);
            return new Moshav(name, l, currentPopulation, (int)(currentPopulation*x),vaccineDoses);
        }

        else if (Objects.equals("Kibbutz", type))
        {
            //addPeopleToSettlementByAmount(k,people_amount);
            return new Kibbutz (name,l,currentPopulation, (int)(currentPopulation*x),vaccineDoses);
        }
        return null;
    }






    public static int determiningAge()
    {
        //the age of every person calculate Formula: y+5*x
        //y-> random value between 0-4
        //x->  SIGMA=6 וסטיית תקן  𝜇=9  משתנה מקרי המתפלג לפי התפלגות נומלית עם תוחלת
        int x;
        int y;
        // create instance of Random class
        Random randomX = new Random();
        Random randomY = new Random();
        x=(int)(randomX.nextGaussian()*6+9);  //nextGaussian-> method to get the next pseudorandom
                                              //casting to int b/c nextGaussian is flout
        y= randomY.nextInt(5);         //nextInt-> Generate random integers in range 0 to 4

        int DA=5*x+y;                         //the age of every person calculate Formula

        return Math.abs(DA);
    }



    private static String chooseFile ()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));  //this is the user's profile directory-> sends me to c:/home/user
        int result = fileChooser.showOpenDialog( fileChooser);   //Pops up an "Open File" file chooser dialog.
        if (result == JFileChooser.APPROVE_OPTION)     //Return value if approve (yes, ok) is chosen.
        {
            File selectedFile = fileChooser.getSelectedFile();      //Returns the selected file.
            return (selectedFile.getAbsolutePath());   //returns the absolute pathname of the given file object
        }
        else
            return "No file selected";
    }



    @Override
    public String toString() { return "SimulationFile{}"; }
}


