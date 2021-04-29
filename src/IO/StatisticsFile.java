
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

    public static void csv(Map map,File file) throws IOException
    {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
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

                int i=0;
            while (line != null)
            {
                // System.out.println(line);
                // read next line
                line = reader.readLine();
                String[] words = line.split(";");
                sb.append(words[0]);   // writes type
                sb.append(',');
                sb.append(words[1]);     //writes name
                sb.append(',');
                sb.append(words[6]);       // writes popu
                sb.append(',');
                sb.append(map.getSettelmentFromMapByIndex(i).getRamzorColor());
                sb.append(',');
                sb.append(map.getSettelmentFromMapByIndex(i).getVaccineDoses());
                sb.append(',');
                sb.append(map.getSettelmentFromMapByIndex(i).getSickPeople().size());
                sb.append(',');
                sb.append(map.getSettelmentFromMapByIndex(i).getHealthyPeople().size());
                sb.append('\n');

                i=i+1;
                writer.write(sb.toString());
                }

            } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        }


}





