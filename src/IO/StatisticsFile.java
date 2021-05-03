
package IO;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import Country.*;

public class StatisticsFile
{

    public static void csv(Map map) throws IOException
    {
        int size = map.getMapSize();

        try {

                    PrintWriter writer = new PrintWriter(new File("test.csv"));
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
                sb.append("Healthy people");
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
                    sb.append(map.getSettelmentFromMapByIndex(i).getCurrentPopulation());       // writes popu
                    sb.append(',');
                    sb.append(map.getSettelmentFromMapByIndex(i).getRamzorColor());
                    sb.append(',');
                    sb.append(map.getSettelmentFromMapByIndex(i).getVaccineDoses());
                    sb.append(',');
                    sb.append(map.getSettelmentFromMapByIndex(i).getSickPeople().size());
                    sb.append(',');
                    sb.append(map.getSettelmentFromMapByIndex(i).getHealthyPeople().size());
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

}








