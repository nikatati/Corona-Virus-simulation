package UI;


import Country.Map;
import IO.SimulationFile;
import Simulation.Clock;
import Simulation.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import static IO.StatisticsFile.csv;


public class Window extends JFrame implements ActionListener
{
    // private final JFileChooser openFileChooser;
    //static final JFrame frame = new JFrame();
    static Map worldMap = new Map();

    public Map getWorldMap(){ return worldMap;}
    private MapPanel map_panel;




    public MapPanel map_panel()
    {
        map_panel = new MapPanel();
        map_panel.setVisible(true);
        map_panel.repaint();

        map_panel.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent event)
            {
                for(int i=0;i<worldMap.getMapSize();i++)
                {

                    int x_c=worldMap.getSettelmentFromMapByIndex(i).getLocation().getPoint().getX();
                    int y_c=worldMap.getSettelmentFromMapByIndex(i).getLocation().getPoint().getY();
                    int height_c=worldMap.getSettelmentFromMapByIndex(i).getLocation().getSize().getHeight();
                    int width_c=worldMap.getSettelmentFromMapByIndex(i).getLocation().getSize().getWidth();

                    if(x_c<=event.getPoint().getX()
                       && event.getPoint().getX()<=x_c+width_c
                       && y_c<=event.getPoint().getY()
                       && event.getPoint().getY()<=y_c+height_c)
                    {
                        System.out.println(worldMap.getSettelmentFromMapByIndex(i).getName());
                        break;
                    }

                }
            }
        });

        return map_panel;
    }



    public static void main(String args[]) throws IOException
    {

        //Creating the Frame
        JFrame frame = new JFrame("Main Window");

        JLabel mapArea = new JLabel();
        mapArea.setOpaque(true);
        //MapPanel.MapPanel(worldMap);
        //mapArea.setBorder(MapPanel.MapPanel(worldMap));
        mapArea.setBackground(new Color(247, 216, 159));
        mapArea.setPreferredSize(new Dimension(400, 380));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JLabel label = new JLabel("Simulation Speed Slider");
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 40, 10);
        slider.setMinorTickSpacing(5);
        slider.setMajorTickSpacing(20);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setLabelTable(slider.createStandardLabels(10));


        //frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 380);
        frame.getContentPane().add(slider,BorderLayout.SOUTH);
        frame.setVisible(true);

        //slider.pack();
        //slider.setVisible(true);



        //Window s = new Window();

                     //Creating the MenuBar and adding components
        //---------------------------------------------------------------------------------
        JMenuBar menuBar = new JMenuBar();
        JMenu file_m = new JMenu("File");
        JMenu simulation_m = new JMenu("Simulation");
        JMenu help_m = new JMenu("Help");
        menuBar.add(file_m);
        menuBar.add(simulation_m);
        menuBar.add(help_m);

        // final Label fileChosen = new Label("No file chosen yet...");
        //m11.setPreferredSize(new Dimension(100,100));

                                    //loud button
        //-------------------------------------------------------------------------------
        JButton load_b = new JButton("Load");

        load_b.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                load_b.setEnabled(false);
                //OpenFile of = new OpenFile();
                try
                {
                    worldMap = SimulationFile.SimulationFile(); //load

                    //mapPanel.set_Map(worldMap);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

                                    //statistics button
        //-------------------------------------------------------------------------------
        JButton statistics_b = new JButton("Statistics");
        statistics_b.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                try
                {
                    csv(worldMap);
                }
                catch (IOException ioException)
                {
                    ioException.printStackTrace();
                }
            }
        });

                                    //edit mutations button
        //-------------------------------------------------------------------------------
        JButton editMutations = new JButton("Edit Mutations");


                                    //exit button
        //-------------------------------------------------------------------------------
        JButton exit_b = new JButton("Exit");
        exit_b.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });

                          //adding all buttons to the file menu part
        //-------------------------------------------------------------------------------
        file_m.add(load_b);
        file_m.add(statistics_b);
        file_m.add(editMutations);
        file_m.add(exit_b);

                                    //play button
        //-------------------------------------------------------------------------------
        JButton play_b = new JButton("Play");
        play_b.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Main.dataInitialization(worldMap);

            }
        });

                                    //pause button
        //-------------------------------------------------------------------------------
        JButton pause_b = new JButton("Pause");
        pause_b.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                for (Thread t : Thread.getAllStackTraces().keySet())
                {
                    if (t.getState()==Thread.State.RUNNABLE)
                    {
                        try
                        {
                            t.sleep(200);
                        }
                        catch (InterruptedException interruptedException)
                        {
                            interruptedException.printStackTrace();
                        }
                    }
                }
            }
        });

                                    //stop button
        //-------------------------------------------------------------------------------
        JButton stop_b = new JButton("Stop");
        stop_b.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                for (Thread t : Thread.getAllStackTraces().keySet())
                {
                    if (t.getState()==Thread.State.RUNNABLE)
                    t.stop();
                }

            }
        });

                                //set tick per day button
        //-------------------------------------------------------------------------------
        JButton setTicksPerDay_b = new JButton("Set Ticks Per Day");

        SpinnerModel ticksForAday = new SpinnerNumberModel(1, 1, 100, 1);
        JSpinner spinner = new JSpinner(ticksForAday);

        JPanel tick_p = new JPanel();
        JButton ok_b = new JButton("Ok");
        JLabel tick_l = new JLabel("ticks for a day:");
        tick_p.add(tick_l);
        tick_p.add(spinner);

        tick_p.add(ok_b);
        ok_b.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int spinner_tick = (Integer) spinner.getValue();
                Clock.setTick_per_day(spinner_tick);
            }
        });

        JDialog set = new JDialog();
        set.setBounds(200, 200, 200, 300);
        set.getContentPane().add(tick_p);
        set.pack();
        setTicksPerDay_b.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                set.setVisible(true);
            }
        });

                        //adding all buttons to the simulation menu part
        //-------------------------------------------------------------------------------
        simulation_m.add(play_b);
        simulation_m.add(pause_b);
        simulation_m.add(stop_b);
        simulation_m.add(setTicksPerDay_b);

                                    //help button
        //-------------------------------------------------------------------------------
        JButton help_b = new JButton("Help");
        help_b.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(frame, "Hello, this is a corona simulation.\n" +
                                                             "Here you can see all the data about corona in different settlements. \n" +
                                                             "Each settlement has a color that is painted according to the number of patients\n"+
                                                              "The first window is the main window:\n There are 3 main buttons->\nFile, Simolation, Help.\n**In the File area we have the option:\n" +
                                                               "Open the option to load a txt file.\n Open the statistics of the file\n " +
                                                               "Open the Edit Mutations window: you can update the variants\n Exit-> exit the app"+
                                                                "\n**In the Simulation area we have the option:\n Play-> play the simulation(Start)"+
                                                                "\n Stop-> stops the simulation\n Set Ticks per day-> opens a window there you can control the ticks that consider for a day"+
                                                                "\n**In the Help area we have the option:\nHelp-> see how the app works.\n About->Shows the details of the writers "+
                                                                "\n Enjoy !");
            }
        });

                                    //about button
        //-------------------------------------------------------------------------------
        JButton about_b = new JButton("About");
        about_b.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(frame, "Writers:\nNika Tatikishvili ID:321735433 \nLiubov Vasilchuk ID:327248605\n Date Written:3/5/2021\n");
            }
        });

        help_m.add(help_b);
        help_m.add(about_b);

        JPanel panel = new JPanel();
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, menuBar);
        frame.getContentPane().add(mapArea);

        //frame.getContentPane().add(slider, BorderLayout.SOUTH);
        frame.getContentPane().add(BorderLayout.SOUTH,label);
        frame.add(BorderLayout.SOUTH,slider);

        frame.setVisible(true);


    }


    @Override
    public void actionPerformed(ActionEvent e)
    {

    }


}