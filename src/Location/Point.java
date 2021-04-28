package Location;

public class Point
{
    private int x;
    private int y;


    //defult cont
    public Point()
    {
        this(0,0);
    }

    //cont
    public Point(int x,int y)
    {
        this.x=x;
        this.y=y;
    }


    //copy cont
    public Point (Point p)
    {
        x=p.x;
        y=p.y;
    }

    //return attribute x
    public int getX() { return x; }

    //return attribute y
    public int getY() { return y; }

    //equals methode
    public boolean equals(Object other)
    {
        boolean ans= false;
        if (other instanceof  Point)
        {
            ans = (x==((Point)other).x&&y==((Point)other).y);
        }
        return ans;
    }

    //to string methode- helps me to represent obj as a string
    public String toString()
    {
        return "The Point(" + x + "," + y + ")";
    }


}
