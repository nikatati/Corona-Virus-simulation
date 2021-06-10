
package IO;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import Country.*;

public class StatisticsFile
{
    public static String string_path =null;
    //public static FileHandler fileHandler =null; //The FileHandler can either write to a specified file, or it can write to a rotating set of files.
    public static Logger logger = Logger.getLogger("");
    public static Memento First=new Memento();
    public static Memento Second=new Memento();
    public static FileHandler fileHandler;

    static {
            try {
                 if(string_path!=null)
                     fileHandler = new FileHandler(string_path, true);
                 } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
            }


    public static void csv(Map map)
    {

        int size = map.getMapSize();


        try {

                PrintWriter writer = new PrintWriter("test.csv");
                StringBuilder sb = new StringBuilder();

                sb.append("Settlement type");
                sb.append(',');
                sb.append("Settlement name");
                sb.append(',');
                sb.append("Population");
                sb.append(',');
                sb.append("Color");
                sb.append(',');
                sb.append("Vaccine dose");
                sb.append(',');
                sb.append("Sick people");
                sb.append(',');
                sb.append("Location");
                sb.append('\n');


                for(int i=0;i<size;i++)
                 {

                    if(map.getSettelmentFromMapByIndex(i) instanceof Kibbutz)// writes type
                        sb.append("Kibbutz");
                    else if(map.getSettelmentFromMapByIndex(i) instanceof City)// writes type
                        sb.append("City");
                    else if(map.getSettelmentFromMapByIndex(i) instanceof Moshav)// writes type
                        sb.append("Moshav");
                    sb.append(',');
                    sb.append(map.getSettelmentFromMapByIndex(i).getName());     //writes name
                    sb.append(',');
                    sb.append(map.getSettelmentFromMapByIndex(i).getCurrentPopulation());       // writes Population
                    sb.append(',');
                    sb.append(map.getSettelmentFromMapByIndex(i).setRamzorColor(map.getSettelmentFromMapByIndex(i).calculateRamzorGrade()));
                    sb.append(',');
                    sb.append(map.getSettelmentFromMapByIndex(i).getVaccineDoses());
                    sb.append(',');
                    sb.append((double)map.getSettelmentFromMapByIndex(i).getSickPeople().size()/(map.getSettelmentFromMapByIndex(i).getSickPeople().size()+map.getSettelmentFromMapByIndex(i).getHealthyPeople().size())*100);
                    sb.append(',');
                    sb.append(map.getSettelmentFromMapByIndex(i).getLocation());
                    sb.append('\n');
                 }

            writer.write(sb.toString());
            writer.close();

            }

        catch (FileNotFoundException fileNotFoundException)
        {
            fileNotFoundException.printStackTrace();
        }
    }



    public static void loadFile()
    {
        if(string_path!=First.getPath())
        {
            First.set(string_path);
        }
        FileDialog fileDialog = new FileDialog((Frame) null, "Please choose a file:", FileDialog.LOAD);
        fileDialog.setVisible(true);
        string_path =fileDialog.getFile();
        try { fileHandler = new FileHandler(string_path); }
        catch (SecurityException | IOException e) { e.printStackTrace(); }


    }


    public static void LogWriting(Settlement s)
    {
        try
        {
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();//Print a brief summary of the LogRecord in a human readable format.
                                                              // The summary will typically be 1 or 2 lines.
            fileHandler.setFormatter(formatter);              //Sets the formatter to be used by this handler.

            logger.info(s.getName()+" Number of sick: "+s.getSickPeople().size()+" Number of dead: "+s.getDeadPeople()+"\n");
            fileHandler.close();
        }
        catch (Exception e) {}
    }
}










