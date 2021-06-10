package UI;
import javax.swing.JFrame;
import Virus.*;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

class MutationWindows extends JDialog
{
    private static class MutationModel extends AbstractTableModel
    {
        private ArrayList<IVirus>virusType;

        private final String[] col_names ={"Chinese Mutation","British Mutation","SouthAfrican Mutation"};

        @Override
        public int getRowCount() { return virusType.size(); }

        @Override
        public int getColumnCount() { return 3; }

        //CONT
        public MutationModel(ArrayList<IVirus> virusType)
        {
            virusType.add(new ChineseVariant());
            virusType.add(new BritishVariant());
            virusType.add(new SouthAfricanVariant());
            this.virusType = (ArrayList<IVirus>) virusType;
        }

        @Override
        public Object getValueAt(int rowi, int coli)
        {

            IVirus virus = virusType.get(rowi);
            if(rowi == 0)
            {
                if (coli==0)
                    return BritishVariant.getMutationChinese();
                else if(coli==1)
                    return BritishVariant.getMutationBritish();
                else if(coli==2)
                    return BritishVariant.getMutationSouthAfrican();
            }

            if(rowi == 1)
            {
                if (coli==0)
                    return BritishVariant.getMutationChinese();
                if(coli==1)
                    return BritishVariant.getMutationBritish();
                if(coli==2)
                    return BritishVariant.getMutationSouthAfrican();
            }

            if(rowi == 2)
            {
                if (coli==0)
                    return BritishVariant.getMutationChinese();
                if(coli==1)
                    return BritishVariant.getMutationBritish();
                if(coli==2)
                    return BritishVariant.getMutationSouthAfrican();
            }
            return null;

        }

        @Override
        public void setValueAt(Object aValue, int row, int col)
        {
            boolean TrueFalse=(Boolean) aValue;
            if(row == 0)
            {
                if(col==0)
                    BritishVariant.setMutationChinese(TrueFalse);
                if(col==1)
                    BritishVariant.setMutationBritish(TrueFalse);
                if(col==2)
                    BritishVariant.setMutationSouthAfrican(TrueFalse);
            }

            if(row == 1)
            {
                if(col==0)
                    SouthAfricanVariant.setMutationChinese(TrueFalse);
                if(col==1)
                    BritishVariant.setMutationBritish(TrueFalse);
                if(col==2)
                    ChineseVariant.setMutationSouthAfrican(TrueFalse);
            }

            if(row == 2)
            {
                if(col==0)
                    SouthAfricanVariant.setMutationChinese(TrueFalse);
                if(col==1)
                    BritishVariant.setMutationBritish(TrueFalse);
                if(col==2)
                    SouthAfricanVariant.setMutationSouthAfrican(TrueFalse);
            }

            fireTableCellUpdated(row, col);
        }

        @Override
        public String getColumnName(int col) { return col_names[col]; }

        @Override
        public boolean isCellEditable(int row, int col) { return true; }

        @Override
        public Class getColumnClass(int col) { return getValueAt(0, col).getClass(); }

    }
    @Override
    public Dimension getPreferredSize() { return new Dimension(500,100); }

    public MutationWindows(JFrame window, ArrayList<IVirus> virusType)
    {
        super(window);
        super.setTitle("Edit Mutation");
        super.setModal(true);
        MutationModel m = new MutationModel(virusType);
        JTable Mtable = new JTable(m);
        String Mn[]= {"Chinese Mutation","British Mutation","SouthAfrican Mutation"};
        Mtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);   //choose only one
        Mtable.setPreferredScrollableViewportSize(new Dimension(getPreferredSize()));
        RowedTableScroll RowedTS =new RowedTableScroll(Mtable,Mn);
        this.add(new RowedTableScroll(Mtable,Mn));
        this.setPreferredSize( new Dimension(500,100));
        setBounds(350,150,220,320);
        this.pack();
    }


}