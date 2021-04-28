package Country;

import java.awt.Color;

public enum RamzorColor
{
    GREEN (0.4,Color.GREEN,1.0),
    YELLOW (0.6,Color.YELLOW,0.8),
    ORANGE (0.8,Color.ORANGE,0.6),
    RED(1.0,Color.RED,0.4);

    // declaring private variable for getting values
    private double factor;                          //אחוז ההדבקה בעיר
    private Color color;                           //current color of the settlement
    private double passOption;                    //שדה להסתדברות מעבר ביישוב


    // enum constructor - cannot be public or protected
    private RamzorColor(double action, Color color,double passOption)
    {
        this.factor = action;
        this.color=color;
        this.passOption=passOption;
    }


    public double getFactor()
    {
        return this.factor;
    }

    public Color getColor() { return color; }

    public double getPassOption() { return passOption; }

    @Override
    public String toString() { return "RamzorColor{" + "factor=" + factor + "} " + super.toString(); }
}
