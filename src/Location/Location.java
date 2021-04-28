package Location;

public class Location
{
    private Point position;
    private Size size;

    //dcont
    public Location()
    {
        this.position = new Point();
        this.size = new Size();
    }

    //cont
    public Location(Point position,Size size)
    {
        this.position = new Point(position);
        this.size = new Size(size);
    }


    //copy cont
    public Location (Location l)
    {
        position=l.position;
        size=l.size;
    }

    //equals method
    public boolean equals(Object other)
    {
        boolean ans= false;
        if (other instanceof  Size)
        {
            ans = (position==((Location)other).position&&size==((Location)other).size);
        }
        return ans;
    }

    //to string methode- helps me to represent obj as a string
    public String toString()
    {
        return " The Location:" + "\n\t" + "position=" + position+
                "\n\t" + "size=" + size ;
    }

    //return the position, to know the point
    public Point getPoint() {
        return position;
    }

    //return the size attribute
    public Size getSize() {
        return size;
    }

}
