package IO;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import Country.Moshav;
import Location.Location;
import Location.Point;
import Location.Size;
import Population.Healthy;
import Population.Person;
import Country.Kibbutz;
import Country.Settlement;
import Country.City;
import java.util.*;
import Country.Map;

import javax.swing.*;

public class SimulationFile
{

    public static Map SimulationFile()
    {

        Map simulation_map = new Map();

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
            while (myReader.hasNextLine())
            {

                String str = myReader.nextLine();                        // read one line from file
                str = str.replaceAll("\\s", "");        //remove all " " from string
                String[] words = str.split(";");                  // split the string to words by ;
                simulation_map.addSettelmentToMap(createNewSettlementByType(words[0], words[1],
                        new Location(new Point(Integer.parseInt(words[2]), Integer.parseInt(words[3])),
                                new Size(Integer.parseInt(words[4]),Integer.parseInt(words[5]))),
                                Integer.parseInt(words[6]),
                                Integer.parseInt(words[7])));

            }

            myReader.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return simulation_map;
    }


    private static Settlement createNewSettlementByType (String type, String name, Location l, int currentPopulation,int maxPopulation)
    {

        if (Objects.equals("City", type))
        {
            City c = new City(name, l,currentPopulation, maxPopulation);
            //addPeopleToSettlementByAmount(c, people_amount);
            return c;
        }

        else if (Objects.equals("Moshav", type))
        {
            Moshav m = new Moshav(name, l, currentPopulation, maxPopulation);
            //addPeopleToSettlementByAmount(m, people_amount);
            return m;
        }

        else if (Objects.equals("Kibbutz", type))
        {
            Kibbutz k = new Kibbutz (name,l,currentPopulation, maxPopulation);
            //addPeopleToSettlementByAmount(k,people_amount);
            return k;
        }
        return null;
    }

    private static void addPeopleToSettlementByAmount (Settlement s, int people_amount)
    {

        for (int i = 0; i < people_amount; i++)
            s.addPerson(new Healthy(determiningAge(),s.randomLocation(),s));
    }

    public static int determiningAge()
    {
        //הגיל של כל אדם נקבע על ידי הנוסחא y+5*x

        //y-> ערך אקראי בין 0 עד 4
        //x->  SIGMA=6 וסטיית תקן  𝜇=9  משתנה מקרי המתפלג לפי התפלגות נומלית עם תוחלת
        int x;
        int y;
        // create instance of Random class
        Random randomX = new Random();
        Random randomY = new Random();
        x=(int)(randomX.nextGaussian()*6+9);  //nextGaussian-> method to get the next pseudorandom
        //casting to int b/c nextGaussian is flout
        y= randomY.nextInt(5);          //nextInt-> Generate random integers in range 0 to 4

        int DA=5*x+y;               //הגיל של כל אדם נקבע לפי נוסחה זו

        return DA;
    }

    private static String chooseFile (){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog( fileChooser);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return (selectedFile.getAbsolutePath());
        }
        else
            return "No file selected";
    }

    @Override
    public String toString() {
        return "SimulationFile{}";
    }
}


