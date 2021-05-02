package Location;

public class Size
{
    private int width;
    private int height;


    //d.cont
    public Size()
    {
        this.width=0;
        this.height=0;
    }

    //cont
    public Size(int width,int height)
    {
        this.width=width;
        this.height=height;
    }


    //copy cont
    public Size(Size s)
    {
        width=s.width;
        height=s.height;
    }

    //return attribut height
    public int getHeight() { return height; }

    //return attribut width
    public int getWidth() { return width; }

    //eqals methode
    public boolean equals(Object other)
    {
        boolean ans= false;
        if (other instanceof  Size)
        {
            ans = (width==((Size)other).width&&height==((Size)other).height);
        }
        return ans;
    }

    //to string methode- helps me to represent obj as a string
    public String toString()
    {
        return " The Size:" +"\t" + "width=" + width +
                " || " + "height=" + height ;
    }
}
