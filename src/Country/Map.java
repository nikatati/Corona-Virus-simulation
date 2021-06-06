package Country;

import UI.StatisticWindow;
import UI.Window;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class Map
{

    private List<Settlement> settlements;
    public CyclicBarrier cyclic_barrier;
    private boolean stop=false;
    private boolean play=false;

    public Map()
    {
        settlements = new ArrayList<Settlement>();
    }

    public boolean addSettelmentToMap(Settlement s)
    {
        return settlements.add(s);
    }

    public boolean removeSettelmentToMap(Settlement s)
    {
        return settlements.remove(s);
    }

    public Settlement getSettelmentFromMapByIndex(int i)
    {
        return settlements.get(i);
    }

    public List<Settlement> getSettelmet()
    {
        return settlements;
    }

    public int getMapSize (){
        return settlements.size();
    }

    public Map (Map map){this.settlements =map.settlements;}

    @Override
    public String toString() { return "Map{" + "settlements=" + settlements + '}'; }


    public CyclicBarrier getCyclic_barrier() {
        return cyclic_barrier;
    }

    public void setCyclic_barrier(CyclicBarrier cyclic_barrier) {
        this.cyclic_barrier = cyclic_barrier;
    }

    public boolean getPlay(){
        return play;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }

    public boolean getStop(){
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }




}

