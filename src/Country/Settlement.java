package Country;
import IO.StatisticsFile;
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
import Simulation.Clock;
import UI.Window;
import Virus.BritishVariant;
import Virus.ChineseVariant;
import Virus.IVirus;
import Virus.SouthAfricanVariant;


public abstract class Settlement implements Runnable
{

    private String name;
    private Location location;
    private List <Person> sickPeople;
    private List <Person> healthyPeople;
    private RamzorColor ramzorColor;
    protected int maxPopulation;    //מספר אנשים מרבי בישוב
    private int currentPopulation;    //מספר אנשים עכשווי  בישוב
    private int vaccineDoses;     // מספר מנות חיסון ביישוב -min num is 0
    private List <Settlement> neighbors;    // מערך מייצג שכנים של ישוב מסויים
    public String Type= null;
    protected int DeadPeople = 0;
    private static double DeadPercent = 0.01;



    public Random randomx=new Random();
    public Random randomy=new Random();


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

    public Settlement (Settlement s)
    {

        this.name = getName();
        this.location = s.getLocation();
        sickPeople = s.getSickPeople();
        healthyPeople = s.getHealthyPeople();
        neighbors=s.getNeighbors();
        this.ramzorColor = s.getRamzorColor();
        this.currentPopulation=s.getCurrentPopulation();
        this.maxPopulation=s.getMaxPopulation();
        this.vaccineDoses= s.getVaccineDoses();
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

    public void getNeighborByIndex(int i) { this.neighbors.get(i); }

    public Person getSickPeronByIndex (int i){ return sickPeople.get(i); }

    public Person getHealthyPeronByIndex (int i){ return healthyPeople.get(i); }

    public Location getLocation (){ return location; }

    public String getType() { return Type; }

    public int getDeadPeople() { return DeadPeople; }

    public void addDeadPeople() { this.DeadPeople++;}

    public void setDeadPeople(int deadPeople) { DeadPeople = deadPeople; }

    @Override
    public String toString()
    {
        return "\n"+
                //"type="+Settlement.class.getClass().toString()+
                "Type="+Type+",\n"+
                "name='" + name + '\''+",\n"+
                " location=" + location +",\n"+
                " sickPeople=" + sickPeople +",\n"+
                " healthyPeople=" + healthyPeople +",\n"+
                " ramzorColor=" + ramzorColor +",\n"+
                " maxPopulation=" + maxPopulation +",\n"+
                " currentPopulation=" + currentPopulation +",\n"+
                " vaccineDoses=" + vaccineDoses +",\n"+
                " neighbors=" + neighbors +
                '}';
    }


    public abstract  RamzorColor CalculateRamzorGrade();

    public void setColor(RamzorColor color)
    {
        this.ramzorColor = color;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Settlement that = (Settlement) o;
        return maxPopulation == that.maxPopulation && currentPopulation == that.currentPopulation && vaccineDoses == that.vaccineDoses && Objects.equals(name, that.name) && Objects.equals(location, that.location) && Objects.equals(sickPeople, that.sickPeople) && Objects.equals(healthyPeople, that.healthyPeople) && ramzorColor == that.ramzorColor && Objects.equals(neighbors, that.neighbors);
    }

    private synchronized void tryTokill()
    {

        if (this.getSickPeople().size() > 0)
        {
            for (int k = 0; k < this.getSickPeople().size(); k++)
            {
                Sick s = (Sick) this.getSickPeople().get(k);
                if (s != null)
                {
                    if (s.tryToDie())
                    {
                        this.getSickPeople().remove(s);
                        this.addDeadPeople();
                    }
                }

            }
        }
        if (this.getDeadPeople() >=this.getCurrentPopulation()* DeadPercent)
        {
            StatisticsFile.LogWriting(this);
        }
    }

    @Override
    public void run()
    {
        Random randomx = new Random();
        Random randomy = new Random();
        IVirus Cvirus, Bvirus, Svirus;
        Cvirus = new ChineseVariant();
        Bvirus = new BritishVariant();
        Svirus = new SouthAfricanVariant();
        Sick sick_p=null;

        System.out.println("hate");

        //In every settlement show 20% of random sick try to contage them

        while (true)
        {
            {
                int population = (int) (this.getCurrentPopulation());  //num of population

                int randomHealthyPeople = (int) (this.getHealthyPeople().size() * 0.2);

                for (int j = 0; j < randomHealthyPeople; j++)      //run until 20% sick people from the settlement
                {
                    int x = randomx.nextInt(population);
                    int y = randomy.nextInt(3);

                    if (y == 0)
                    {
                        sick_p = new Sick(this.getHealthyPeronByIndex(j).getAge(),  // CONTAGION WITH CHINESE VARIANT
                                 this.getHealthyPeronByIndex(j).getLocation(),
                                 this.getHealthyPeronByIndex(j).getSettlement(),
                                 Clock.now(),
                                 Cvirus);
                    }
                    if (y == 1)
                    {
                        sick_p = new Sick(this.getHealthyPeronByIndex(j).getAge(), // CONTAGION WITH BRITISH VARIANT
                                 this.getHealthyPeronByIndex(j).getLocation(),
                                 this.getHealthyPeronByIndex(j).getSettlement(),
                                 Clock.now(),
                                 Bvirus);
                    }
                    if (y == 2)
                    {
                        sick_p = new Sick(this.getHealthyPeronByIndex(j).getAge(),
                                 this.getHealthyPeronByIndex(j).getLocation(),
                                 this.getHealthyPeronByIndex(j).getSettlement(),
                                 Clock.now(),
                                 Svirus);
                    }
                    this.getHealthyPeople().remove(j);
                    this.getSickPeople().add(sick_p);
                    this.getSickPeronByIndex(x).toString();   //דיגום לאחר נסיון הדבקה
                }
            }

            //-------------------------------------------------------------------------------------------------------------------------------------------
            //-------------------------------------------------------------------------------------------------------------------------------------------


            //In every settlement show 3 not sick people and try to contage them
            int notSick = 3;
            String x;
            IVirus v = null;


            {
                for (int j = 0; j < this.getSickPeople().size(); j++)       //run until size if healty people list
                {
                    x = this.getSickPeronByIndex(j).toString();

                    for (int z = 0; z < notSick; z++)    //runs until 3
                    {
                        if (x.contains("BritishVariant"))
                        {
                            v = new BritishVariant();      //BRITISH VARIANT
                        }
                        if (x.contains("SouthAfricanVariant"))
                        {
                            v = new SouthAfricanVariant();         //SouthAfrican Variant
                        }
                        if (x.contains("ChineseVariant"))
                        {
                            v = new ChineseVariant();          //CHINESE VARIANT
                        }

                        if (v.tryToContagion(this.getSickPeronByIndex(j), this.getHealthyPeronByIndex(j)))
                        {
                            Sick s1 = new Sick(this.getHealthyPeronByIndex(j).getAge(),
                                    this.getHealthyPeronByIndex(j).getLocation(),
                                    this.getHealthyPeronByIndex(j).getSettlement(),
                                    Clock.now(),
                                    v);

                            this.getSickPeople().add(this.getHealthyPeronByIndex(z));//add the person that got contage to the sick list
                            this.getHealthyPeople().remove(this.getHealthyPeronByIndex(z));//remove the person that got contage from the healthy list
                        }
                    }

                }
            }

            //-------------------------------------------------------------------------------------------------------------------------------------------
            //-------------------------------------------------------------------------------------------------------------------------------------------
            this.tryTokill();
            //-------------------------------------------------------------------------------------------------------------------------------------------
            //-------------------------------------------------------------------------------------------------------------------------------------------

            //make in every settlement all the sick people to convalescent if its been 25 days from the contage day
            {
                for (int j = 0; j < this.getSickPeople().size(); j++)
                {
                    if ((Clock.now() - ((Sick) this.getSickPeronByIndex(j)).getContagiousTime()) > 25)  //if past 25 days from the day the person got contagious
                    {
                        //need to make the sick person to convalescent so I will make new convalescent obj and
                        //put all the data of the sick person to the new obj
                        Convalescent As =  new Convalescent(((Sick)this.getSickPeople().get(j)).getAge(),
                                               ((Sick) this.getSickPeople().get(j)).getLocation(),
                                               ((Sick)this.getSickPeople().get(j)).getSettlement(),
                                               ((Sick) this.getSickPeople().get(j)).getVirus());

                        this.getSickPeople().remove(j);
                        this.getHealthyPeople().add(As);


                    }
                }
            }
            //-------------------------------------------------------------------------------------------------------------------------------------------
            //-------------------------------------------------------------------------------------------------------------------------------------------

            //In every settlement show 3% of people that trying to random neighbor
            //Try to move to that neighbor settlement

            List<Person> sickANDhealthyPersonList = new ArrayList<>();
            {
                for (int j = 0; j < this.getHealthyPeople().size(); j++) {
                    sickANDhealthyPersonList.add(this.getHealthyPeronByIndex(j));
                }
                for (int j = this.getHealthyPeople().size(); j < this.getSickPeople().size(); j++) {
                    sickANDhealthyPersonList.add(this.getSickPeronByIndex(j));
                }
                //now the array contains all person- sick and healthy

                for (int j = 0; j < sickANDhealthyPersonList.size() * 0.03; j++) {
                    Random rn = new Random();
                    Random rp = new Random();
                    int a = rp.nextInt(sickANDhealthyPersonList.size());
                    int b = rn.nextInt(sickANDhealthyPersonList.get(a).getSettlement().getNeighbors().size());
                    sickANDhealthyPersonList.
                            get(a).
                            getSettlement().
                            transferPerson(sickANDhealthyPersonList.get(a)
                                    , sickANDhealthyPersonList.get(a).getSettlement().getNeighbors().get(b));
                }
            }

            //-------------------------------------------------------------------------------------------------------------------------------------------
            //-------------------------------------------------------------------------------------------------------------------------------------------

            //inoculate (לחסן) healthy people and update the
            {
                if (this.getVaccineDoses() > 0)   //it means that the settlement has vaccine Doses
                {
                    if (this.getVaccineDoses() >= this.getHealthyPeople().size())
                    {
                        for (int j = 0; j < this.getHealthyPeople().size(); j++)
                        {
                            Vaccinated personGotVaccinated = new Vaccinated(this.getHealthyPeronByIndex(j).getAge(),
                                    this.getHealthyPeronByIndex(j).getLocation(),
                                    this.getHealthyPeronByIndex(j).getSettlement(),
                                    Clock.now());
                            this.setVaccineDoses((this.getVaccineDoses()) - 1);
                        }
                    }
                    else
                        {
                        for (int j = 0; j < this.getVaccineDoses(); j++)
                        {
                            Vaccinated personGotVaccinated = new Vaccinated(this.getHealthyPeronByIndex(j).getAge(),
                                    this.getHealthyPeronByIndex(j).getLocation(),
                                    this.getHealthyPeronByIndex(j).getSettlement(),
                                    Clock.now());
                            this.setVaccineDoses((this.getVaccineDoses()) - 1);
                        }
                    }

                }
            }
            //System.out.println(this.healthyPeople.size() +" Healthy people at the settlement "+this.getName());  //** מחזיר 0 -לא עובד**
            //System.out.println(this.sickPeople.size() +" Sick people at the settlement "+this.getName());          ////** מחזיר 0 -לא עובד**

            //System.out.println(this.getRamzorColor() +" ramzor color of the settlement "+this.getName());
            //System.out.println(this.getNeighbors().size() +" Neighbors of the settlement "+this.getName());
            System.out.println(this.currentPopulation +" People in the settlement "+this.getName());
            //System.out.println(this.maxPopulation +" Max People in the settlement "+this.getName());
            //System.out.println(this.getType() +" Type of the settlement "+this.getName());
        }
    }
}