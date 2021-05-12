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

import java.awt.Graphics;
import java.awt.Graphics2D;
import  java.awt.Component;

import static IO.StatisticsFile.csv;





public class Window extends JFrame implements ActionListener
{
    private Map worldMap = new Map();

    public Map getWorldMap(){ return worldMap;}

    private MapPanel map_panel;

    JFrame frame = new JFrame("Main Window");

    JMenuBar menuBar = new JMenuBar();

    int xx=0;
    int yy=0;



    public void createFrame()
    {
        //Creating the Frame
        //JFrame frame = new JFrame("Main Window");

        JLabel mapArea = new JLabel();
        mapArea.setOpaque(true);
        //MapPanel.MapPanel(worldMap);
        //mapArea.setBorder(MapPanel.MapPanel(worldMap));
        //mapArea.setBackground(new Color(247, 216, 159));
        mapArea.setPreferredSize(new Dimension(400, 380));


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 380);
        frame.setVisible(true);


        JPanel panel = new JPanel();
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, menuBar);
        frame.getContentPane().add(mapArea);


        frame.setVisible(true);


        //Creating the MenuBar and adding components
        //---------------------------------------------------------------------------------

      //frame.add(File());

    }



    //*************************************************************************************************************************************
    public Component File()
    {
        JMenu file_m = new JMenu("File");
        menuBar.add(file_m);
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

                    set_Map(worldMap);
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

        return null;
    }

    //*************************************************************************************************************************************

    public void simulationSlider()
    {
        JLabel label = new JLabel("Simulation Speed Slider");
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 40, 10);
        slider.setMinorTickSpacing(5);
        slider.setMajorTickSpacing(20);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setLabelTable(slider.createStandardLabels(10));

        frame.getContentPane().add(BorderLayout.SOUTH,label);
        frame.add(BorderLayout.SOUTH,slider);

        frame.getContentPane().add(slider,BorderLayout.SOUTH);

    }

    //*************************************************************************************************************************************

    public Component simulation_func()
    {
        JMenu simulation_m = new JMenu("Simulation");
        menuBar.add(simulation_m);

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

        return null;
    }

    //*************************************************************************************************************************************
    public Component Help()
    {
        JMenu help_m = new JMenu("Help");
        menuBar.add(help_m);
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
        return null;
    }

    //*************************************************************************************************************************************


    public MapPanel map_panel()
    {
        map_panel = new MapPanel();
        map_panel.repaint();
        map_panel.setVisible(true);

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

//*************************************************************************************************************************************

    public static void main(String args[]) throws IOException
    {
        Window allFrame= new Window();
        allFrame.createFrame();
        allFrame.File();
        allFrame.simulation_func();
        allFrame.Help();
        allFrame.simulationSlider();

    }


    @Override
    public void actionPerformed(ActionEvent e)
    {

    }


    //****************************************************************************************************************************************
    //****************************************************************************************************************************************
    //****************************************************************************************************************************************

    public void paint(Graphics g)
    {
        super.paint(g);
        if (worldMap==null){return;}
        Graphics2D graf1 = (Graphics2D) g;
        graf1.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);

        int x1=0;
        int y1=0;
        int x2=0;
        int y2=0;
        int size= worldMap.getMapSize();

        //creating a lines for the map (the settlement)
        for(int i=0;i<size-1;i++)
        {
            if(worldMap.getSettelmentFromMapByIndex(i).getNeighbors().size()>0 )
            {
                for(int j=0; j<worldMap.getSettelmentFromMapByIndex(i).getNeighbors().size();j++ )
                {
                    g.setColor(Color.BLACK);
                    x1=  worldMap.getSettelmentFromMapByIndex(i).getLocation().getPoint().getX()+worldMap.getSettelmentFromMapByIndex(i).getLocation().getSize().getHeight()/2;
                    y1=  worldMap.getSettelmentFromMapByIndex(i).getLocation().getPoint().getY()+worldMap.getSettelmentFromMapByIndex(i).getLocation().getSize().getHeight()/2;
                    x2=  worldMap.getSettelmentFromMapByIndex(i).getNeighbors().get(j).getLocation().getPoint().getX()+
                         worldMap.getSettelmentFromMapByIndex(i).getNeighbors().get(j).getLocation().getSize().getWidth()/2;
                    y2=  worldMap.getSettelmentFromMapByIndex(i).getNeighbors().get(j).getLocation().getPoint().getY()+
                         worldMap.getSettelmentFromMapByIndex(i).getNeighbors().get(j).getLocation().getSize().getHeight()/2;
                    //g.drawLine((int)x1*(this.getWidth()/xx),(int)y1*(this.getHeight()/yy),(int)x2*(this.getWidth()/xx),(int)y2*(this.getHeight()/yy));
                    g.drawLine(10,10,20,20);

                }
            }
        }

        //creating a rectangle for the map (the settlement)
        for(int k=0;k<size-1;k++)
        {

            g.setColor(worldMap.getSettelmentFromMapByIndex(k).getRamzorColor().getRamzorColor());

            g.drawRect((int)worldMap.getSettelmentFromMapByIndex(k).getLocation().getPoint().getX()*(this.getWidth()/xx),
                    (int)worldMap.getSettelmentFromMapByIndex(k).getLocation().getPoint().getY()*(this.getHeight()/yy),
                    (int)worldMap.getSettelmentFromMapByIndex(k).getLocation().getSize().getWidth()*(this.getWidth()/xx),
                    (int)worldMap.getSettelmentFromMapByIndex(k).getLocation().getSize().getHeight()*(this.getHeight()/yy) );

            g.drawString(worldMap.getSettelmentFromMapByIndex(k).getName(),10,20);

            g.fillRect((int)worldMap.getSettelmentFromMapByIndex(k).getLocation().getPoint().getX()*(this.getWidth()/xx),
                    (int)worldMap.getSettelmentFromMapByIndex(k).getLocation().getPoint().getY()*(this.getHeight()/yy),
                    (int)worldMap.getSettelmentFromMapByIndex(k).getLocation().getSize().getWidth()*(this.getWidth()/xx),
                    (int)worldMap.getSettelmentFromMapByIndex(k).getLocation().getSize().getHeight()*(this.getHeight()/yy));
        }

    }
    //*************************************************************************************************************************************
    public void set_Map(Map map)
    {

        this.worldMap=map;

        xx=yy=1;

        if (map != null)
            for (int i = 0; i < map.getMapSize(); i++)
            {
                if (worldMap.getSettelmentFromMapByIndex(i).getLocation().getPoint().getX() +worldMap.getSettelmentFromMapByIndex(i).getLocation().getSize().getWidth() > xx)
                {
                    xx = worldMap.getSettelmentFromMapByIndex(i).getLocation().getPoint().getX()+worldMap.getSettelmentFromMapByIndex(i).getLocation().getSize().getWidth();
                }
                if (worldMap.getSettelmentFromMapByIndex(i).getLocation().getPoint().getY()+worldMap.getSettelmentFromMapByIndex(i).getLocation().getSize().getHeight() > yy)
                {
                    yy = worldMap.getSettelmentFromMapByIndex(i).getLocation().getPoint().getY()+worldMap.getSettelmentFromMapByIndex(i).getLocation().getSize().getHeight();
                }
            }


        this.repaint();
    }

}