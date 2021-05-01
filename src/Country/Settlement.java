package Country;
import Population.Person;
import Location.Location;
import Location.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import Location.Size;

import Population.Convalescent;
import Population.Sick;
import Population.Healthy;
import Population.Vaccinated;


public abstract class Settlement {

    private String name;
    private Location location;
    private List <Person> sickPeople;
    private List <Person> healthyPeople;
    private RamzorColor ramzorColor;
    private int maxPopulation;    //מספר אנשים מרבי בישוב
    private int currentPopulation;    //מספר אנשים עכשווי  בישוב
    private int vaccineDoses;     // מספר מנות חיסון ביישוב -min num is 0
    private List <Settlement> neighbors;    // מערך מייצג שכנים של ישוב מסויים

    //------------------------------------------------------------------------------------------------------------------------------------------



    //abstract methode that calculate the ramzor grade of the settlement
    public abstract RamzorColor calculateRamzorGrade();

    //default constructor
    public Settlement ()
    {
        this.name = "Noname";
        Point p = new Point();
        Size s = new Size();
        this.sickPeople = new ArrayList<Person>();
        this.healthyPeople = new ArrayList<Person>();
        this.location = new Location(p,s);
        this.ramzorColor = RamzorColor.GREEN;
        this.neighbors=new ArrayList<Settlement>();
        this.currentPopulation=0;
        this.maxPopulation=0;
        this.vaccineDoses=0;
    }

    //constructor get 4 variables
    public Settlement (String name,Location location,int currentPopulation,int maxPopulation, int vaccineDoses)
    {

        this.name = name;
        this.location = new Location(location);
        sickPeople = new ArrayList<Person>();
        healthyPeople = new ArrayList<Person>();
        neighbors=new ArrayList<Settlement>();
        this.ramzorColor = RamzorColor.GREEN;
        this.currentPopulation=currentPopulation;
        this.maxPopulation=maxPopulation;
        this.vaccineDoses= vaccineDoses;
    }

    //Calculates the percentage of contagious in the city
    public double contagiousPercent ()
    {
        int number_of_sick_people = getSickPeople().size(); // How many sick people are there in the city
        int number_of_people = getCurrentPopulation();  // How many people are there in the city
        double result  = number_of_sick_people/number_of_people;
        return result;
    }

    public Point randomLocation ()
    {

        //get random x in the settlement
        Random r = new Random();
        int low = location.getPoint().getX();
        int high = low + location.getSize().getWidth();
        int random_x = r.nextInt(high-low) + low;

        //get random y in the settlement
        Random r1 = new Random();
        int high2 = location.getPoint().getY();
        int low2= high2 - location.getSize().getHeight();
        int random_y = r1.nextInt(high2-low2) + low2;

        //Creat a new point with the random values
        Point p = new Point (random_x, random_y);

        return p;

    }

    //add person to the settlement
    public boolean addPerson (Person p)
    {
        if (getMaxPopulation()>getCurrentPopulation())   //כל עוד מה שיש לי עכשיו ביישוב קטן מהקסימום שהוא יכול להכיל אז אפשר להוסיף עוד
        {
            if (p instanceof Healthy)
            {
                this.healthyPeople.add(p);
            }
            this.sickPeople.add(p);
            return true;
        }
        return false;
    }



    //remove person from the settlement
    public boolean removePerson (Person p, Settlement s1)
    {
        if (p instanceof Healthy)
        {
            this.healthyPeople.add(p);          //הוספה ברשימה של החולים
            s1.healthyPeople.remove(p);         //מוריד מיישוב ממנו יצא
        }
        if (p instanceof Sick)
        {
            this.sickPeople.add(p);          //הוספה ברשימה של החולים
            s1.sickPeople.remove(p);         //מוריד מהיישוב ממנו יצא
        }
        return true;
    }


    private boolean transferChance(double p1, double p2)
    {
        double chance =p1*p2;
        return chance >=Math.random();
    }


    //transfer person from the settlment to other settlement
    public boolean transferPerson (Person p, Settlement s)
    {
        double p1=this.getRamzorColor().getPassOption();
        double p2=s.getRamzorColor().getPassOption();

        if(transferChance(p1,p2)==true)  //מחזיר נכון אם המעבר יצליח
        {
            if(s.addPerson(p))
            {
                if(p instanceof Sick)
                { this.sickPeople.remove(p);
                    s.sickPeople.add(p); }
                else
                    { this.healthyPeople.remove(p);
                    s.healthyPeople.add(p); }
                return true;
            }
        }
        return false;
    }



    public RamzorColor getRamzorColor() { return ramzorColor; }


    public RamzorColor setRamzorColor(RamzorColor ramzorColor)
    {
        this.ramzorColor = ramzorColor;
        return ramzorColor;
    }


    public List<Person> getSickPeople() { return sickPeople; }

    public List<Person> getHealthyPeople() { return healthyPeople; }

    public int getMaxPopulation() { return maxPopulation; }

    public int getCurrentPopulation() { return currentPopulation; }

    public int getVaccineDoses() { return vaccineDoses; }

    public void setVaccineDoses(int vaccineDoses) { this.vaccineDoses = vaccineDoses; }

    public String getName(){return name;}

    public List<Settlement> getNeighbors() { return neighbors; }

    public void setNeighbors(List<Settlement> neighbors) { this.neighbors = neighbors; }

    public void addNeighbor(Settlement s) { this.neighbors.add(s); }

    public Person getSickPeronByIndex (int i){ return sickPeople.get(i); }

    public Person getHealthyPeronByIndex (int i){ return healthyPeople.get(i); }


    @Override
    public String toString()
    {
        return "Settlement{" +
                "name='" + name + '\'' +
                ", location=" + location +
                ", sickPeople=" + sickPeople +
                ", healthyPeople=" + healthyPeople +
                ", ramzorColor=" + ramzorColor +
                ", maxPopulation=" + maxPopulation +
                ", currentPopulation=" + currentPopulation +
                ", vaccineDoses=" + vaccineDoses +
                ", neighbors=" + neighbors +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Settlement that = (Settlement) o;
        return maxPopulation == that.maxPopulation && currentPopulation == that.currentPopulation && vaccineDoses == that.vaccineDoses && Objects.equals(name, that.name) && Objects.equals(location, that.location) && Objects.equals(sickPeople, that.sickPeople) && Objects.equals(healthyPeople, that.healthyPeople) && ramzorColor == that.ramzorColor && Objects.equals(neighbors, that.neighbors);
    }

}