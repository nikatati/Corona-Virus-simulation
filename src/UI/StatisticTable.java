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
            return settlementsInfo.size()-1;
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
                    return settlement.getRamzorColor();
                case 4:
                    return settlement.getVaccineDoses();
                case 5:
                    return settlement.contagiousPercent()*100+"%";
                case 6:
                    return settlement.getLocation();
            }
            return null;
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

        public void setSick(int row)
        {
            IVirus virus=null;
            Settlement settlement=settlementsInfo.get(row);
            Random rand=new Random();
            int rand_n=rand.nextInt();

            if (rand_n==0)
                {virus=new ChineseVariant();}
            else if (rand_n==1)
                {virus=new BritishVariant();}
            else
                {virus=new SouthAfricanVariant();}

            double numContagion=settlement.getCurrentPopulation()* ContagionOPTION;

            for (int i=0;i<numContagion && settlement.getCurrentPopulation()<settlement.getMaxPopulation();i++)
            {
                int x=Math.abs(rand.nextInt(settlement.getHealthyPeople().size()));
                if(settlement.getHealthyPeronByIndex(x).contagion(virus) instanceof Sick)
                {
                    settlement.getSickPeople().add(settlement.getHealthyPeople().get(x).contagion(virus));
                    settlement.getHealthyPeople().remove(x);
                }
            }
            settlement.setColor(settlement.CalculateRamzorGrade());
            fireTableCellUpdated(row, 4);
            fireTableCellUpdated(row, 3);
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
    private TableRowSorter<StatisticModel> sorter;
    private JTextField tbFilterText;
    private int col;
    private JTable table;
    private StatisticModel model;
    private JComboBox<ColumnName> column=null;

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
        table.setRowSorter(sorter = new TableRowSorter<StatisticModel>(model));
        InitialFilter(row_name);
        this.add(new JLabel("filter:"));
        this.add(top);
        this.add(new JScrollPane(table));
        top.add(new JLabel("Col:"));
        top.add(column);
        top.add(new JLabel("Row:"));
        tbFilterText = new JTextField();
        tbFilterText.setSize(80,30);
        top.add(tbFilterText);


        tbFilterText.setToolTipText("Filter Row");
        column.setToolTipText("Filter Column");
        tbFilterText.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { newFilter(); }
            public void removeUpdate(DocumentEvent e) { newFilter(); }
            public void changedUpdate(DocumentEvent e) { newFilter(); }

        });

        this.setVisible(true);
    }


    public void updateModel()
    {
        model.updateTable();
        newFilter();
    }


    public void setSick()
    {
        if(table.getSelectedRow()>=0)
        { model.setSick(table.getRowSorter().convertRowIndexToModel(table.getSelectedRow())); }
    }


    public void setDouse(int douses)
    {
        if(table.getSelectedRow()>=0)
            model.setdouses(table.getRowSorter().convertRowIndexToModel(table.getSelectedRow()),douses);
    }


    public void actionPerformed1(ActionEvent e)
    {
        col=column.getItemAt(column.getSelectedIndex()).getcol();
        newFilter();
    }


    public int getcol()
    {
        return col;
    }


    public JTable getTableFromPanel()
    {
        return table;
    }


    private void newFilter()
    {
        try { sorter.setRowFilter(RowFilter.regexFilter(tbFilterText.getText(), getcol())); }
        catch (java.util.regex.PatternSyntaxException ignored) { }
    }


    private void InitialFilter(String row_name)
    {
        try { sorter.setRowFilter(RowFilter.regexFilter(row_name, getcol())); }
        catch (java.util.regex.PatternSyntaxException ignored) { }
    }


    @Override
    public void actionPerformed(ActionEvent e) { }



}