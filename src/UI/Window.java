package UI;
import Country.Map;

import IO.SimulationFile;
import IO.StatisticsFile;
import Virus.IVirus;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.util.concurrent.CyclicBarrier;


public class Window extends JFrame implements ActionListener
{

    private Map worldMap = new Map();
    private Map map;
    private MapPanel map_panel= new MapPanel();
    public JFrame frame = new JFrame("Main Window");
    JMenuBar menuBar = new JMenuBar();
    ArrayList<IVirus> variants=new ArrayList<>();
    public static boolean PlayFlag=false;
    double speed=0;
    int xx=0;
    int yy=0;
//--------------------------------------------------------------------


    public Map getWorldMap(){ return worldMap;}


    public void createFrame()
    {

        JLabel mapArea = new JLabel();
        mapArea.setOpaque(true);
        mapArea.setPreferredSize(new Dimension(400, 380));


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 380);
        //frame.setVisible(true);


        JPanel panel = new JPanel();
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, menuBar);
        frame.getContentPane().add(mapArea);
        frame.add(new MapPanel());
        frame.setVisible(true);
        frame.pack();



    }

    //***********************************************************************************************************************************
    public MapPanel map_panel()
    {
        map_panel = new MapPanel();
        //map_panel.setVisible(true);
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
                        StatisticWindow statistic_d =  statistic_Window(map,map.getSettelmentFromMapByIndex(i).getName());
                        statistic_d.setVisible(true);
                        break;
                    }

                }
            }
        });

        map_panel.setVisible(true);
        return map_panel;
    }




    //*************************************************************************************************************************************
    public void File()
    {
        JMenu file_m = new JMenu("File");
        menuBar.add(file_m);


        //----------------------------loud button -------------------------------
        JButton load_b = new JButton("Load");

        load_b.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                load_b.setEnabled(false);
                try
                {
                    worldMap = SimulationFile.LoadFile(); //load
                    frame.pack();

                    for (int i=0;i<worldMap.getMapSize();i++)
                    {
                        worldMap.getSettelmet().get(i).ref(worldMap);
                    }

                    map_panel.setMap(worldMap);
                    worldMap.cyclic_barrier = new CyclicBarrier(worldMap.getSettelmet().size()-1, new Runnable()
                    {
                        public void run()
                        {
                            Simulation.Clock.nextTick();
                            updateAll();
                            try { Thread.sleep((long) speed); }
                            catch (InterruptedException e) { e.printStackTrace(); }
                        }
                    });

                }
                catch (Exception exception) { exception.printStackTrace(); }

            }
        });



        //------------------------statistics button--------------------------
        JButton Bstatistics = new JButton("Statistics");
        Bstatistics.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                    StatisticWindow statistic_window= new StatisticWindow(frame,worldMap," ");
                    statistic_window.setVisible(true);
                    frame.pack();
            }
        });


        //------------------------- edit mutations button------------------------------------------------------
        JButton editMutations = new JButton("Edit Mutations");

        // variants.add(new BritishVariant());
       // variants.add(new ChineseVariant());
       // variants.add(new SouthAfricanVariant());

        MutationWindows MutationsEdit = new MutationWindows(this,variants);

        editMutations.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                MutationsEdit.setVisible(true);
            }
        });

        //----------------------------log button----------------------------------------------------------------
        JButton Blog = new JButton("Log file");

        Blog.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                StatisticsFile.loadFile();
            }
        });


        //----------------------------exit button----------------------------------------------------------------
        JButton exit_b = new JButton("Exit");
        exit_b.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });


        //----------------------------adding all buttons to the file menu part--------------------------------
        file_m.add(load_b);
        file_m.add(Bstatistics);
        file_m.add(editMutations);
        file_m.add(Blog);
        file_m.add(exit_b);
        frame.pack();

    }

    //*************************************************************************************************************************************

    public void simulationSlider()
    {

        JPanel slid=new JPanel();
        slid.setLayout(new BoxLayout(slid, BoxLayout.LINE_AXIS));

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 50, 10);
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setLabelTable(slider.createStandardLabels(10));

        JButton btspeed=new JButton("set speed");
        btspeed.setBackground(new Color(18, 95, 160));
        btspeed.setForeground(Color.WHITE);
        btspeed.setFocusPainted(false);
        btspeed.setFont(new Font("Tahoma", Font.BOLD, 12));
        btspeed.setBounds(10,10,10,10);
        //btspeed.setPreferredSize(new Dimension(10,10));
        btspeed.setVisible(true);

        btspeed.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                speed=1000*slider.getValue();
                JOptionPane.showMessageDialog(frame, "Simulation speed defined");
            }
        });


        //slider.add(btspeed);


        slid.add(btspeed,BorderLayout.BEFORE_LINE_BEGINS);
        slid.add(slider,BorderLayout.SOUTH);
        frame.getContentPane().add(slid,BorderLayout.SOUTH);

    }

    //*************************************************************************************************************************************

    public void simulation_func()
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
                worldMap.setPlay(true);
                TreadStart(worldMap);
                play_b.setEnabled(false);

            }
        });


        //-----------------------------------pause button--------------------------------------------
        JButton pause_b = new JButton("Pause");
        pause_b.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                map.setPlay(false);
                play_b.setEnabled(true);
                pause_b.setEnabled(false);


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
                worldMap=null;
                map_panel.setMap(null);
            }
        });


        //---------------------------set tick per day button---------------------------
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
                Simulation.Clock.setTick_per_day(spinner_tick);
                JOptionPane.showMessageDialog(tick_p, "Tick is set");
                play_b.setEnabled(true);

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


        //--------------------------adding all buttons to the simulation menu part-----------------------------
        simulation_m.add(play_b);
        simulation_m.add(pause_b);
        simulation_m.add(stop_b);
        simulation_m.add(setTicksPerDay_b);

    }

    //*************************************************************************************************************************************
    public void Help()
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
                JOptionPane.showMessageDialog(frame, """
                        Hello, this is a corona simulation.
                        Here you can see all the data about corona in different settlements.\s
                        Each settlement has a color that is painted according to the number of patients
                        The first window is the main window:
                         There are 3 main buttons->
                        File, Simulation, Help.
                        **In the File area we have the option:
                        Open the option to load a txt file.
                         Open the statistics of the file
                         Open the Edit Mutations window: you can update the variants
                         Exit-> exit the app
                        **In the Simulation area we have the option:
                         Play-> play the simulation(Start)
                         Stop-> stops the simulation
                         Set Ticks per day-> opens a window there you can control the ticks that consider for a day
                        **In the Help area we have the option:
                        Help-> see how the app works.
                         About->Shows the details of the writers\s
                         Enjoy !""");
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
    }

    //*************************************************************************************************************************************

    public void updateAll()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                map_panel.repaint();
                StatisticWindow.update_statistics();
            }
        });
    }


//*************************************************************************************************************************************

    public static void main(String[] args) throws IOException
    {
        Window allFrame= new Window();
        allFrame.createFrame();
        allFrame.File();
        allFrame.simulation_func();
        allFrame.Help();
        allFrame.simulationSlider();


    }


    @Override
    public void actionPerformed(ActionEvent e) { }


    public StatisticWindow statistic_Window(Map map,String row_name)
    {
        return new StatisticWindow(this,map,row_name);
    }





    //****************************************************************************************************************************************
    //****************************************************************************************************************************************
    //****************************************************************************************************************************************

    private class MapPanel extends JPanel
    {
        Map map12 = new Map();

        public  MapPanel () { }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            if (worldMap == null) { return; }

            Graphics2D graf1 = (Graphics2D) g;
            graf1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int x1 , y1 , x2 , y2 ;
            int size = worldMap.getMapSize();



            //creating a lines for the map (the settlement)
           for (int i = 0; i < size-1 ; i++)
            {
                g.setColor(Color.black);
                if (worldMap.getSettelmentFromMapByIndex(i).getNeighbors().size() > 0)
                {
                for (int j = 0; j < worldMap.getSettelmentFromMapByIndex(i).getNeighbors().size(); j++)
                    {
                        x1 = worldMap.getSettelmentFromMapByIndex(i).getLocation().getPoint().getX() ;

                        y1 = worldMap.getSettelmentFromMapByIndex(i).getLocation().getPoint().getY() ;

                        x2 = worldMap.getSettelmentFromMapByIndex(i).getNeighbors().get(j).getLocation().getPoint().getX();

                        y2 = worldMap.getSettelmentFromMapByIndex(i).getNeighbors().get(j).getLocation().getPoint().getY() ;

                        g.drawLine((int) x1 , (int) y1 , (int) x2 , (int) y2 );

                    }
                }
            }

            //creating a rectangle for the map (the settlement)
            for (int k = 0; k < size ; k++)
            {
                g.setColor(Color.BLACK);

                g.drawRect((int) worldMap.getSettelmentFromMapByIndex(k).getLocation().getPoint().getX(),
                        (int) worldMap.getSettelmentFromMapByIndex(k).getLocation().getPoint().getY() ,
                        (int) worldMap.getSettelmentFromMapByIndex(k).getLocation().getSize().getWidth(),
                        (int)worldMap.getSettelmentFromMapByIndex(k).getLocation().getSize().getHeight() );

                g.setColor(worldMap.getSettelmentFromMapByIndex(k).getRamzorColor().getRamzorColor());

                g.fillRect((int) worldMap.getSettelmentFromMapByIndex(k).getLocation().getPoint().getX(),
                           (int) worldMap.getSettelmentFromMapByIndex(k).getLocation().getPoint().getY(),
                           (int) worldMap.getSettelmentFromMapByIndex(k).getLocation().getSize().getWidth(),
                           (int) worldMap.getSettelmentFromMapByIndex(k).getLocation().getSize().getHeight());

                g.setColor(Color.BLACK);

                g.drawString(worldMap.getSettelmentFromMapByIndex(k).getName(),
                            (int) worldMap.getSettelmentFromMapByIndex(k).getLocation().getPoint().getX()+10 ,
                            (int) worldMap.getSettelmentFromMapByIndex(k).getLocation().getPoint().getY()+10);

                g.setFont(new Font("Tahoma", Font.PLAIN , 12) );

            }

        }

        //*************************************************************************************************************************************
        public void setMap(Map map)
        {

            this.map12 = map;
            this.setVisible(true);


            xx = 0;
            yy = 0;

            if (map != null)
                for (int i = 0; i < map.getMapSize(); i++)
                {
                    if (map.getSettelmentFromMapByIndex(i).getLocation().getPoint().getX() + map.getSettelmentFromMapByIndex(i).getLocation().getSize().getWidth() > xx)
                    {
                        xx = map.getSettelmentFromMapByIndex(i).getLocation().getPoint().getX() + map.getSettelmentFromMapByIndex(i).getLocation().getSize().getWidth();
                    }
                    if (map.getSettelmentFromMapByIndex(i).getLocation().getPoint().getY() + map.getSettelmentFromMapByIndex(i).getLocation().getSize().getHeight() > yy)
                    {
                        yy = map.getSettelmentFromMapByIndex(i).getLocation().getPoint().getY() + map.getSettelmentFromMapByIndex(i).getLocation().getSize().getHeight();
                    }
                }

            xx=xx+30;
            yy=yy+30;
            this.revalidate();
            this.repaint();
        }


        @Override
        public Dimension getPreferredSize()             //הגדרת הגודל המועדף על הציור – יהיה הגודל בעת זימון pack
        {
            return new Dimension(800,400);
        }

    }

    //********************************************************************************************************************************************

    private static void TreadStart(Map s)
    {
        for (int i=0; i<s.getMapSize();i++)
        {
            new Thread(s.getSettelmet().get(i)).start();
        }
    }




}