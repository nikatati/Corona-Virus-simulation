package UI;

import javax.swing.*;
import java.io.File;
import java.util.Scanner;

public class OpenFile {

    JFileChooser openFileChooser = new JFileChooser();
    StringBuilder sb =new StringBuilder();
    public File PickMe()throws Exception{
        if(openFileChooser.showOpenDialog(null)== JFileChooser.APPROVE_OPTION){

            java.io.File file=openFileChooser.getSelectedFile();

            Scanner input = new Scanner(file);
            while(input.hasNext()){
                sb.append(input.nextLine());
                sb.append("\n");
            }
            input.close();
            return file;
        }
        else{ sb.append("No file was selected");}

        return null;
    }



}