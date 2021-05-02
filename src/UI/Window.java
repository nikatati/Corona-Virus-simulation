package UI;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;


public class Window {
    static final JFrame frame = new JFrame();

    //Usually you will require both swing and awt packages
// even if you are working with just swings.
    public static String showFileDialog(){
        FileDialog fdlg = new FileDialog(frame, "Open file...", FileDialog.LOAD);
        fdlg.setPreferredSize(new Dimension(400,400));
        fdlg.setSize(400, 400);
        fdlg.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        fdlg.setResizable(true);
        //fdlg.setVisible(true);
        //fdlg.pack();
        return fdlg.getFile();
    }
    public static <flag1> void main(String args[]) {

        //Creating the Frame
        JFrame frame = new JFrame("Main Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        //Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("File");
        JMenu m2 = new JMenu("Simulation");
        JMenu m3 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        mb.add(m3);
       // JButton m11 = new JButton("Load");
        /*m11.addActionListener(new ActionListener()
        {
            boolean flag1 = true; //no file selected
            String file_path = null;

            while (flag1)
             {
                file_path = chooseFile();
                if (!Objects.equals("No file selected", file_path)) // File chosen
                    flag1 = false; // while loop over
             }

        });  */ //make sending to file


        final Label fileChosen = new Label("No file chosen yet...");
        Button m11 = new Button("Choose a file");
        m11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChosen.setText(showFileDialog());
            }
        });

        JButton m12 = new JButton("Statistics");
        JButton m13 = new JButton("Edit Mutations");
        JButton m14 = new JButton("Exit");
        m1.add(m11);
        m1.add(m12);
        m1.add(m13);
        m1.add(m14);
        JButton m21 = new JButton("Play");
        JButton m22 = new JButton("Pause");
        JButton m23 = new JButton("Stop");
        JButton m24 = new JButton("Set Ticks Per Day");
        m2.add(m21);
        m2.add(m22);
        m2.add(m23);
        m2.add(m24);
        JButton m31 = new JButton("Help");
        JButton m32 = new JButton("About");
        m3.add(m31);
        m3.add(m32);
        JPanel panel = new JPanel();
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);





        frame.setVisible(true);
    }
}

   /* private static String chooseFile() {
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

*/