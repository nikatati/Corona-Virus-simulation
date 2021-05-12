package UI;

import Country.Map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;
//import java.awt.*;
//import java.awt.geom.Line2D;
//import java.awt.geom.Rectangle2D;
import java.awt.*;
import java.io.IOException;
import javax.swing.border.EtchedBorder;

public class MapPanel extends JPanel
{


    private Map map;
    private int xx=0;
    private int yy=0;

    public MapPanel()
    {

    }

    public void paint(Graphics g)
    {
        super.paintComponent(g);
        if (map==null){return;}
        Graphics2D graf1 = (Graphics2D) g;
        graf1.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int x1=0;
        int y1=0;
        int x2=0;
        int y2=0;
        int size= map.getMapSize();

        //creating a lines for the map (the settlement)
        for(int i=0;i<size-1;i++)
         {
             g.setColor(Color.BLACK);
             if(map.getSettelmentFromMapByIndex(i).getNeighbors().size()>0 )
             {
                 for(int j=0; j<map.getSettelmentFromMapByIndex(i).getNeighbors().size();j++ )
                 {
                      x1=  map.getSettelmentFromMapByIndex(i).getLocation().getPoint().getX()+map.getSettelmentFromMapByIndex(i).getLocation().getSize().getHeight()/2;
                      y1=  map.getSettelmentFromMapByIndex(i).getLocation().getPoint().getY()+map.getSettelmentFromMapByIndex(i).getLocation().getSize().getHeight()/2;
                      x2=  map.getSettelmentFromMapByIndex(i).getNeighbors().get(j).getLocation().getPoint().getX()+
                           map.getSettelmentFromMapByIndex(i).getNeighbors().get(j).getLocation().getSize().getWidth()/2;
                      y2=  map.getSettelmentFromMapByIndex(i).getNeighbors().get(j).getLocation().getPoint().getY()+
                           map.getSettelmentFromMapByIndex(i).getNeighbors().get(j).getLocation().getSize().getHeight()/2;
                     g.drawLine((int)x1*(this.getWidth()/xx),(int)y1*(this.getHeight()/yy),(int)x2*(this.getWidth()/xx),(int)y2*(this.getHeight()/yy));

                 }
             }
         }

         //creating a rectangle for the map (the settlement)
         for(int k=0;k<size-1;k++)
         {

            g.setColor(map.getSettelmentFromMapByIndex(k).getRamzorColor().getRamzorColor());

            g.drawRect((int)map.getSettelmentFromMapByIndex(k).getLocation().getPoint().getX()*(this.getWidth()/xx),
                       (int)map.getSettelmentFromMapByIndex(k).getLocation().getPoint().getY()*(this.getHeight()/yy),
                    (int)map.getSettelmentFromMapByIndex(k).getLocation().getSize().getWidth()*(this.getWidth()/xx),
                    (int)map.getSettelmentFromMapByIndex(k).getLocation().getSize().getHeight()*(this.getHeight()/yy) );

            g.drawString(map.getSettelmentFromMapByIndex(k).getName(),10,20);

            g.fillRect((int)map.getSettelmentFromMapByIndex(k).getLocation().getPoint().getX()*(this.getWidth()/xx),
                       (int)map.getSettelmentFromMapByIndex(k).getLocation().getPoint().getY()*(this.getHeight()/yy),
                    (int)map.getSettelmentFromMapByIndex(k).getLocation().getSize().getWidth()*(this.getWidth()/xx),
                    (int)map.getSettelmentFromMapByIndex(k).getLocation().getSize().getHeight()*(this.getHeight()/yy));
         }

    }

    public void set_Map(Map map)
    {
        this.map = map;
        xx=0;
        yy=0;

        if (map != null)
            for (int i = 0; i < map.getMapSize(); i++)
            {
                if (map.getSettelmentFromMapByIndex(i).getLocation().getPoint().getX() + map.getSettelmentFromMapByIndex(i).getLocation().getSize().getWidth() > xx)
                {
                    xx = map.getSettelmentFromMapByIndex(i).getLocation().getPoint().getX()+map.getSettelmentFromMapByIndex(i).getLocation().getSize().getWidth();
                }
                    if (map.getSettelmentFromMapByIndex(i).getLocation().getPoint().getY()+map.getSettelmentFromMapByIndex(i).getLocation().getSize().getHeight() > yy)
                {
                    yy = map.getSettelmentFromMapByIndex(i).getLocation().getPoint().getY()+map.getSettelmentFromMapByIndex(i).getLocation().getSize().getHeight();
                }
            }

        this.repaint();
    }

}
