package UI;


import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import Country.*;
import Population.*;
import Virus.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

public class StatisticTable extends JPanel implements ActionListener
{


    public enum ColumnName
    {

        ZERO ("Settlement Type", 0),
        ONE("Settlement Name",1),
        TWO("Population", 2),
        THREE("Ramzor color", 3),
        FOUR("Vaccine doses", 4),
        FIVE("Sick Percentages", 5),
        SIX("location", 6);


        private final int col;
        private final String colname;

        ColumnName(String name, int col)
        {
            this.col=col;
            this.colname = name;
        }

        public int getcol()
        {
            return col;
        }

        @Override
        public String toString()
        {
            return colname;
        }

    }

    public class StatisticModel extends AbstractTableModel
    {
        private static final double ContagionOPTION = 0.01;
        private List<Settlement> settlementsInfo;
        private final String[] colNames = {"Settlement Type","Settlement Name","Population","Ramzor color","Vaccine doses","Sick Percentages","location"};


        public StatisticModel(List<Settlement> settlementsInfo )
        {
            this.settlementsInfo= settlementsInfo;
        }


        @Override
        public int getColumnCount()
        {
            return 7;
        }



        @Override
        public int getRowCount()
        {
            return settlementsInfo.size();
        }


        public String getColumnName(int column)
        {
            return colNames[column];
        }

        public Class getColumnClass(int clas)
        {
            return getValueAt(0,clas).getClass();
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return columnIndex >=0;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            Settlement settlement =(settlementsInfo.get(rowIndex));
            switch (columnIndex)
            {
                case 0:
                    if (settlement instanceof Moshav)
                        return "Moshav";
                    else if (settlement instanceof City)
                        return "City";
                    else
                        return "Kibbutz";

                case 1:
                    return settlement.getName();
                case 2:
                    return settlement.getCurrentPopulation();
                case 3:
                    return settlement.calculateRamzorGrade();
                case 4:
                    return settlement.getVaccineDoses();
                case 5:
                    return settlement.contagiousPercent();
                case 6:
                    return settlement.getLocation();
            }
            return null;
        }

        public void setSick(int row)
        {
            IVirus virus=null;
            Settlement settlement=settlementsInfo.get(row);
            Random r=new Random();
            int rand_n=r.nextInt();

            if (rand_n%3==0)
                {virus=new ChineseVariant();}
            else if (rand_n%3==1)
                {virus=new BritishVariant();}
            else
                {virus=new SouthAfricanVariant();}

            double numContagion=settlement.getCurrentPopulation()* ContagionOPTION;

            for (int i=0;i<numContagion && settlement.getHealthyPeople().size() >0;i++)
            {
                int x=r.nextInt(settlement.getHealthyPeople().size());

                if(settlement.getHealthyPeople().get(x).contagion(virus) instanceof Sick)
                {
                    settlement.getHealthyPeople().remove(x);
                    settlement.getSickPeople().add(settlement.getHealthyPeople().get(x).contagion(virus));
                }

            }
            settlement.setColor(settlement.calculateRamzorGrade());
            settlement.contagiousPercent();
            fireTableCellUpdated(row, 4);
            fireTableCellUpdated(row, 6);
        }

        public void setdouses(int row,int douses)
        {
            Settlement settlement = settlementsInfo.get(row);
            settlement.setVaccineDoses(douses);
            fireTableCellUpdated(row, 5);
        }

        public void updateTable()
        {
            fireTableDataChanged();
        }
    }
    private int col;
    private JTable table;
    private StatisticModel model;
    private JTextField textField;
    private JComboBox<ColumnName> column=null;
    private TableRowSorter<StatisticModel> to_sort;

    public StatisticTable(List<Settlement> settlementsInfo,String row_name)
    {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JPanel top=new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.LINE_AXIS));
        column = new JComboBox<>(ColumnName.values());
        column.setSelectedIndex(0);
        column.addActionListener(this);
        model = new StatisticModel(settlementsInfo);
        table = new JTable(model);
        table.setRowHeight(10);
        table.setPreferredScrollableViewportSize(new Dimension(500, 150));
        table.setFillsViewportHeight(true);
        table.setRowSorter(to_sort = new TableRowSorter<StatisticModel>(model));
        initialFilter(row_name);
        this.add(new JLabel("filter:"));
        this.add(top);
        this.add(new JScrollPane(table));
        top.add(new JLabel("Col:"));
        top.add(column);
        top.add(new JLabel("Row:"));
        textField = new JTextField();
        textField.setSize(80,30);
        top.add(textField);


        textField.setToolTipText("Filter Row");
        column.setToolTipText("Filter Column");

        textField.getDocument().addDocumentListener(new DocumentListener()
        {

            public void insertUpdate(DocumentEvent event)
            {
                do_new_filt();
            }

            public void removeUpdate(DocumentEvent event)
            {
                do_new_filt();
            }

            public void changedUpdate(DocumentEvent event)
            {
                do_new_filt();
            }

        });

        this.setVisible(true);
    }


    public void updateModel()
    {
        model.updateTable();
        do_new_filt();
    }


    public void setSick()
    {
        if(table.getSelectedRow()>=0)
        { model.setSick(table.getRowSorter().convertRowIndexToModel(table.getSelectedRow())); }
    }


    public void setDouse(int douses)
    {
        if(table.getSelectedRow()>=0)
        {
            model.setdouses(table.getRowSorter().convertRowIndexToModel(table.getSelectedRow()), douses);
        }
    }


    public void actionPerformed1(ActionEvent e)
    {
        col=column.getItemAt(column.getSelectedIndex()).getcol();
        do_new_filt();
    }


    public int getcol()
    {
        return col;
    }



    public JTable getTableFromPanel()
    {
        return table;
    }


    private void do_new_filt()
    {
        try { to_sort.setRowFilter(RowFilter.regexFilter(textField.getText(), getcol())); }
        catch (java.util.regex.PatternSyntaxException ignored) { }
    }


    private void initialFilter(String row_name)
    {
        try { to_sort.setRowFilter(RowFilter.regexFilter(row_name, getcol())); }
        catch (java.util.regex.PatternSyntaxException ignored) { }
    }


    @Override
    public void actionPerformed(ActionEvent e) { }



}