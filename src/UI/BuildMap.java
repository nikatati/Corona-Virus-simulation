package UI;

import Country.Map;


import javax.swing.*;
//import java.awt.*;
//import java.awt.geom.Line2D;
//import java.awt.geom.Rectangle2D;
import java.awt.*;
import java.io.IOException;

public class BuildMap {


    public static void Build(Map map)
    {

        JPanel panel = new JPanel();
        JFrame frame = new JFrame("Map");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        DebugGraphics g =new DebugGraphics();
        DebugGraphics g2 = new DebugGraphics();
        int x1=0;
        int y1=0;
        int x2=0;
        int y2=0;
         int size= map.getMapSize();
         for(int i=0;i<size;i++)
         {
             if(map.getSettelmentFromMapByIndex(i).getNeighbors().size()>0 )
             {
                 int neighborsSize=map.getSettelmentFromMapByIndex(i).getNeighbors().size();
                 for(int j=0; j<neighborsSize;j++ )
                 {

                      x1=  map.getSettelmentFromMapByIndex(i).getLocation().getPoint().getX();
                      y1=  map.getSettelmentFromMapByIndex(i).getLocation().getPoint().getY();
                      x2=  map.getSettelmentFromMapByIndex(i).getNeighbors().get(j).getLocation().getPoint().getX();
                      y2=  map.getSettelmentFromMapByIndex(i).getNeighbors().get(j).getLocation().getPoint().getY();

                     g.setColor(Color.BLACK);
                     g.drawLine(x1,y1,x2,y2);
                 }

             }
         }
         for(int k=0;k<size;k++)
         {

            g2.setColor(map.getSettelmentFromMapByIndex(k).getRamzorColor().getRamzorColor());
            g2.drawRect(map.getSettelmentFromMapByIndex(k).getLocation().getPoint().getX(),
                     map.getSettelmentFromMapByIndex(k).getLocation().getPoint().getY(),
                     map.getSettelmentFromMapByIndex(k).getLocation().getSize().getWidth(),
                     map.getSettelmentFromMapByIndex(k).getLocation().getSize().getHeight());
         }

        frame.setVisible(true);


    }
}
