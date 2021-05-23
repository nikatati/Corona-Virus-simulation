package UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import Country.Map;
import Country.Settlement;
import IO.StatisticsFile;
//import Simulation.MainClass;


public class StatisticWindow extends JDialog
{



    private Map map=null;
    private static StatisticTable Table;


    public StatisticWindow(JFrame windowFrame, Map map,String rowName)
    {


        super(windowFrame,"Statistics",false);
        windowFrame.setBounds(350,150,170,320);
        getContentPane().setLayout(new GridLayout(2, 1));

        JPanel bottom_panel=new JPanel(new GridLayout(1, 3));
        bottom_panel.setLayout(new GridLayout(1, 3));
        bottom_panel.setBounds(50,150,30,70);



        List<Settlement> settlements=map.getSettelmet();
        Table = new StatisticTable(settlements,rowName);




        //---------------------------------Save Button----------------------------------------
        JButton Bsave = new JButton("Save");
        Bsave.setBounds(10,10,10,10);


        Bsave.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                StatisticsFile.csv(map);
            }
        });

        //--------------------------------Add Sick Button-------------------------------------
        JButton Bsick = new JButton("Add Sick");
        Bsick.setBounds(10,10,10,10);

        Bsick.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    Table.setSick();
                }
                catch (Exception e1) { e1.printStackTrace(); }
            }
        });


        //---------------------------------------Douses Button-------------------------------------
        JButton Bvaccinate= new JButton("Set Vaccinate doses");
        Bvaccinate.setBounds(10,10,10,10);

        SpinnerModel vaccinateModel=new SpinnerNumberModel(1,1,100,1);
        JSpinner spinner = new JSpinner(vaccinateModel);
        JPanel Pdouses=new JPanel();
        JButton Bdouses = new JButton("Set");
        JLabel Ldouses = new JLabel("Set douses:");

        Pdouses.add(Ldouses);
        Pdouses.add(spinner);
        Pdouses.add(Bdouses);

        Bdouses.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {

                int douses = (Integer) spinner.getValue();
                Table.setDouse(douses);

            }
        });

        //------------------------------------vaccinate Dialog--------------------------------------
        JDialog vaccinate=new JDialog(this,"Set vaccinate douses",true);
        vaccinate.setBounds(350,150,170,320);
        vaccinate.getContentPane().add(Pdouses);
        vaccinate.pack();
        Bvaccinate.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                vaccinate.setVisible(true);
            }
        });

        //-----------------------------------------------------------------------------------------

        Bsave.setPreferredSize( new Dimension(20,20));
        Bsick.setPreferredSize( new Dimension(20,20));
        Bvaccinate.setPreferredSize( new Dimension(20,20));
        bottom_panel.add(Bsave);
        bottom_panel.add(Bsick);
        bottom_panel.add(Bvaccinate);
        getContentPane().add(Table);
        getContentPane().add(bottom_panel);


        pack();
    }
    public JTable getTableFromDialog()
    {

        return Table.getTableFromPanel();
    }

    public static void update_statistics()
    {

        if(Table != null)
        {
            Table.updateModel();
        }

    }

}