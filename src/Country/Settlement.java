package Country;

import IO.StatisticsFile;
import Population.Person;
import Location.Location;
import Location.Point;

import java.util.*;

import Location.Size;

import Population.Convalescent;
import Population.Sick;
import Population.Healthy;
import Population.Vaccinated;

import Simulation.Clock;
import Virus.BritishVariant;
import Virus.ChineseVariant;
import Virus.IVirus;
import Virus.SouthAfricanVariant;


public abstract class Settlement implements Runnable {

    private String name;
    private Location location;
    private List<Person> sickPeople;
    private List<Person> healthyPeople;
    private RamzorColor ramzorColor;
    protected int maxPopulation;    //מספר אנשים מרבי בישוב
    private int currentPopulation;    //מספר אנשים עכשווי  בישוב
    private int vaccineDoses;     // מספר מנות חיסון ביישוב -min num is 0
    private List<Settlement> neighbors;    // מערך מייצג שכנים של ישוב מסויים
    public String Type = null;
    protected int DeadPeople = 0;
    private Map map;


    public Random randomx = new Random();
    public Random randomy = new Random();


    //------------------------------------------------------------------------------------------------------------------------------------------


    //abstract methode that calculate the ramzor grade of the settlement
    public abstract RamzorColor calculateRamzorGrade();

    //default constructor
    public Settlement() {
        this.name = "Noname";
        Point p = new Point();
        Size s = new Size();
        this.sickPeople = new ArrayList<Person>(currentPopulation);
        this.healthyPeople = new ArrayList<Person>(currentPopulation);
        this.location = new Location(p, s);
        this.ramzorColor = RamzorColor.GREEN;
        this.neighbors = new ArrayList<Settlement>();
        this.currentPopulation = 0;
        this.maxPopulation = 0;
        this.vaccineDoses = 0;

    }

    //constructor get 4 variables
    public Settlement(String name, Location location, RamzorColor color, List<Person> healthy, List<Person> sick, int currentPopulation, int maxPopulation, int vaccineDoses)
    {

        this.name = name;
        this.location = new Location(location);
        this.sickPeople = new ArrayList<Person>(currentPopulation);
        this.healthyPeople = new ArrayList<Person>(currentPopulation);

        for (Person person : healthyPeople)
            if (person instanceof Sick)
            {
                this.sickPeople.add(person);
            } else {
                this.healthyPeople.add(person);
            }

        neighbors = new ArrayList<Settlement>();
        this.ramzorColor = RamzorColor.GREEN;
        this.currentPopulation = currentPopulation;
        this.maxPopulation = maxPopulation;
        this.vaccineDoses = vaccineDoses;

    }



    //Calculates the percentage of contagious in the city
    public double contagiousPercent()
    {
        int number_of_sick_people = getSickPeople().size(); // How many sick people are there in the city
        int number_of_people = getSickPeople().size()+getHealthyPeople().size();  // How many people are there in the city
        if(number_of_people==0)
        {return 1;}
        return number_of_sick_people / number_of_people;
    }

    public Point randomLocation() {

        //get random x in the settlement
        Random r = new Random();
        int low = location.getPoint().getX();
        int high = low + location.getSize().getWidth();
        int random_x = r.nextInt(high - low) + low;

        //get random y in the settlement
        Random r1 = new Random();
        int high2 = location.getPoint().getY();
        int low2 = high2 - location.getSize().getHeight();
        int random_y = r1.nextInt(high2 - low2) + low2;

        //Creat a new point with the random values

        return new Point(random_x, random_y);

    }

    //add person to the settlement
    public boolean addPerson(Person p)
    {
        if (this.healthyPeople.size()+this.sickPeople.size() < maxPopulation)   //כל עוד מה שיש לי עכשיו ביישוב קטן מהקסימום שהוא יכול להכיל אז אפשר להוסיף עודif (this.healthyPeople.size()+this.sickPeople.size() < maxPopulation)   //כל עוד מה שיש לי עכשיו ביישוב קטן מהקסימום שהוא יכול להכיל אז אפשר להוסיף עוד
        {
            if (p instanceof Healthy||p instanceof Vaccinated ||p instanceof Convalescent)
            {
                this.healthyPeople.add(p);
            }
            else
                {
                    this.sickPeople.add(p);
                }
            return true;
        }
        return false;
    }


    //remove person from the settlement
    public boolean removePerson(Person p, Settlement s1) {
        if (p instanceof Healthy) {
            this.healthyPeople.add(p);          //הוספה ברשימה של החולים
            s1.healthyPeople.remove(p);         //מוריד מיישוב ממנו יצא
        }

        if (p instanceof Sick) {
            this.sickPeople.add(p);          //הוספה ברשימה של החולים
            s1.sickPeople.remove(p);         //מוריד מהיישוב ממנו יצא
        }
        return true;
    }


    private boolean transferChance(double p1, double p2) {
        double chance = p1 * p2;
        return chance >= Math.random();
    }


    //transfer person from the settlment to other settlement
    public boolean transferPerson(Person person, Settlement settlement) // for transfer person between settles
    {
        if (this.getCurrentPopulation() >= this.maxPopulation) { return false; }

        double randomProbability = Math.random();

        if (settlement.getRamzorColor().getPassOption() * person.getSettlement().getRamzorColor().getPassOption() <= randomProbability)
        {
            return false;
        }
        return true;
    }

    public Settlement(Settlement s)
    {

        this.name = s.getName();
        this.location = s.getLocation();
        sickPeople = s.getSickPeople();
        healthyPeople = s.getHealthyPeople();
        neighbors = s.getNeighbors();
        this.ramzorColor = s.calculateRamzorGrade();
        this.currentPopulation = s.getCurrentPopulation();
        this.maxPopulation = s.getMaxPopulation();
        this.vaccineDoses = s.getVaccineDoses();
    }

    public RamzorColor getRamzorColor() {
        return ramzorColor;
    }


    public RamzorColor setRamzorColor(RamzorColor color ) {
        this.ramzorColor = calculateRamzorGrade();
        return color;
    }


    public List<Person> getSickPeople() {
        return sickPeople;
    }

    public List<Person> getHealthyPeople() {
        return healthyPeople;
    }

    public int getMaxPopulation() {
        return maxPopulation;
    }

    public int getCurrentPopulation() {
        return currentPopulation;
    }

    public int getVaccineDoses() {
        return vaccineDoses;
    }

    public void setVaccineDoses(int vaccineDoses) {
        this.vaccineDoses = vaccineDoses;
    }

    public String getName() {
        return name;
    }

    public List<Settlement> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Settlement> neighbors) {
        this.neighbors = neighbors;
    }

    public void addNeighbor(Settlement s) {
        this.neighbors.add(s);
    }

    public void getNeighborByIndex(int i) {
        this.neighbors.get(i);
    }

    public Person getSickPeronByIndex(int i) {
        return sickPeople.get(i);
    }

    public Person getHealtyPeronByIndex(int i) { return healthyPeople.get(i); }

    //public Person getHealthyPeronByIndex (int i){ return healthyPeople.get(i); }

    public Location getLocation() {
        return location;
    }

    public String getType() {
        return Type;
    }

    public int getDeadPeople() {
        return DeadPeople;
    }

    public void addDeadPeople() {
        this.DeadPeople++;
    }

    public void setDeadPeople(int deadPeople) {
        DeadPeople = deadPeople;
    }


    private List<String> toString_neighbors() {

        List<String> settlement = new ArrayList<String>();
        if (this.getNeighbors().size() != 0) {
            for (int i = 0; i < this.getNeighbors().size(); i++) {
                settlement.add(this.getNeighbors().get(i).getName());
            }
            return settlement;
        }
        settlement.add("NO neighbors");
        return settlement;
    }

    @Override
    public String toString() {
        return this.getName() + " " +
                this.getLocation().toString() +
                " population- " + this.getCurrentPopulation() +
                " Max Population " + this.getMaxPopulation() +
                " Ramzor color- " + this.getRamzorColor() +
                " Neighbors- " + this.toString_neighbors() +
                " Num vaccine doses- " + this.getVaccineDoses();
    }


    public void setColor(RamzorColor color) {
        this.ramzorColor = calculateRamzorGrade();
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Settlement that = (Settlement) o;
        return maxPopulation == that.maxPopulation
                && currentPopulation == that.currentPopulation
                && vaccineDoses == that.vaccineDoses
                && Objects.equals(name, that.name)
                && Objects.equals(location, that.location)
                && Objects.equals(sickPeople, that.sickPeople)
                && Objects.equals(healthyPeople, that.healthyPeople)
                && ramzorColor == that.ramzorColor
                && Objects.equals(neighbors, that.neighbors);
    }



    //-------------------Data Initialization 0.1 present is sick-----------------------
    private synchronized void dataInitialization ()
    {
        IVirus Cvirus = new ChineseVariant();
        //contagion 0.01% from the people at the settlement

            for (int j = 0; j < this.getCurrentPopulation() * 0.01; j++)
            {
                if (this.getPeronByIndex(j).ifSick() == false) // if the man Healthy
                {
                    try
                    {
                        //create mew Sick Obj, copy the data from the healthy person and add it to the settlement
                        Sick sick = new Sick(this.getHealthyPeople().get(j).getAge(),
                                this.getHealthyPeople().get(j).getLocation(),
                                this.getHealthyPeople().get(j).getSettlement(),
                                Simulation.Clock.now(),Cvirus);

                        this.getHealthyPeople().remove(j);
                        this.getSickPeople().add(sick);
                    }

                    catch (Exception e) { System.out.print(e); }

                }
            }
    }

    //-----------------------------Simulation 1------------------------
    private synchronized void simulation1()
    {

        ChineseVariant Cvariant = new ChineseVariant(); // Choose kind of Corona variant
        BritishVariant Bvariant = new BritishVariant(); // Choose kind of Corona variant
        SouthAfricanVariant Svariant = new SouthAfricanVariant(); // Choose kind of Corona variant
        Random random_num=new Random();
        int r=random_num.nextInt(3);


            //loop all people at settlement
            for (int j = 0; j < this.getCurrentPopulation(); j++)
            {
                if (!this.healthyPeople.get(j).ifSick())//if the man Healthy
                    for (int k = 0; k < 6; k++)   //try 6 times so contagion
                    {
                        int index = 0;
                        boolean flag = false;
                        while (!flag)
                        {
                            Random x = new Random();
                            index = x.nextInt(this.getCurrentPopulation());// get random person
                            if (index != j)// its not the same healthy person
                                flag = true;
                        }

                        if (this.healthyPeople.get(index).ifSick()) // the random person is sick
                        {
                            //Try to contagion by Probability contagion function
                            if (Bvariant.tryToContagion(this.healthyPeople.get(index), this.healthyPeople.get(j)))
                                try {
                                        Sick sick = new Sick(this.healthyPeople.get(j).getAge(),
                                             this.getLocation().getPoint(),
                                                this.healthyPeople.get(j).getSettlement(),
                                                Simulation.Clock.now(),
                                                Bvariant);

                                        this.healthyPeople.remove(j);
                                        this.sickPeople.add(sick);
                                    }
                                    catch (Exception e) { System.out.print(e); }
                        }
                    }
            }
    }

    public Person getPeronByIndex(int person) { return healthyPeople.get(person); }

    //*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*
    //-----------------------------Simulation 2------------------------

    private synchronized void simulation2()
    {
        partOneSimoTwo();
        partTwoSimoTwo();
       // tryTokill();
        partThreeSimoTwo();
        partFourSimoTwo();
        partFiveSimoTwo();
        this.setRamzorColor(this.calculateRamzorGrade());
    }

    public synchronized void partOneSimoTwo()
    {
        //In every settlement show 20% of random sick try to contage them

        Random randomx = new Random();
        Random randomy = new Random();
        IVirus Cvirus, Bvirus, Svirus;
        Cvirus = new ChineseVariant();
        Bvirus = new BritishVariant();
        Svirus = new SouthAfricanVariant();
        Sick sick_p = null;

        int population = (int) (this.getCurrentPopulation());  //num of population

        int randomHealthyPeople = (int) (this.getHealthyPeople().size() * 0.2);

        for (int j = 0; j < randomHealthyPeople; j++)      //run until 20% sick people from the settlement
        {
            int x = randomx.nextInt(getHealthyPeople().size());
            int y = randomy.nextInt(3);

            if (y == 0)
            {
                sick_p = new Sick(this.getHealthyPeople().get(j).getAge(),  // CONTAGION WITH CHINESE VARIANT
                        this.getHealthyPeople().get(j).getLocation(),
                        this.getHealthyPeople().get(j).getSettlement(),
                        Clock.now(),
                        Cvirus);
            }

            if (y == 1)
            {
                sick_p = new Sick(this.getHealthyPeople().get(j).getAge(), // CONTAGION WITH BRITISH VARIANT
                        this.getHealthyPeople().get(j).getLocation(),
                        this.getHealthyPeople().get(j).getSettlement(),
                        Clock.now(),
                        Bvirus);
            }

            if (y == 2)
            {
                sick_p = new Sick(this.getHealthyPeople().get(j).getAge(),
                        this.getHealthyPeople().get(j).getLocation(),
                        this.getHealthyPeople().get(j).getSettlement(),
                        Clock.now(),
                        Svirus);
            }

            this.getHealthyPeople().remove(j);
            this.getSickPeople().add(sick_p);
            //this.sickPeople.get(x).toString();   //דיגום לאחר נסיון הדבקה
            this.setColor(calculateRamzorGrade());
            //StatisticsFile.LogWriting(this);
        }
    }


    //*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*

    public synchronized void partTwoSimoTwo()
    {
        //In every settlement show 3 not sick people and try to contage them
        Random randomx = new Random();
        Random randomy = new Random();
        int notSick = 3;
        String x;

        IVirus v = new IVirus()
        {
            @Override
            public double contagionProbability(Person p) { return 0; }

            @Override
            public boolean tryToContagion(Person p_sick, Person p_unknown) { return false; }

            @Override
            public boolean tryToKill(Sick p) { return false; }
        };



       // for (int j = 0; j < this.healthyPeople.size()-1; j++)       //run until size if healthy people list
       // {


            for (int z = 0; z < notSick; z++)    //runs until 3
            {
                int randomSick=0;
                int randomHealthy = 0;
                if(this.sickPeople.size()>0) {
                    randomSick = randomx.nextInt(this.sickPeople.size());
                }
                else
                    break;

                if(this.healthyPeople.size()>0) {
                   randomHealthy = randomy.nextInt(this.healthyPeople.size());
                }
                else
                    break;
                x = this.getSickPeronByIndex(randomSick).toString();

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

                if (v.tryToContagion(this.sickPeople.get(randomSick), this.healthyPeople.get(randomHealthy)))
                {
                    Sick s1 = new Sick(this.healthyPeople.get(randomHealthy).getAge(),
                            this.healthyPeople.get(randomHealthy).getLocation(),
                            this.healthyPeople.get(randomHealthy).getSettlement(),
                            Simulation.Clock.now(),
                            v);

                    this.getSickPeople().add(s1);//add the person that got contage to the sick list
                    this.getHealthyPeople().remove(randomHealthy);//remove the person that got contage from the healthy list
                    this.setColor(calculateRamzorGrade());
                }
            }
       // }

    }
    //*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*

    private synchronized void tryTokill()
    {
        if (this.sickPeople.size() > 0)
        {
            for (int k = 0; k < this.sickPeople.size(); k++)
            {
                Sick sick = (Sick) this.sickPeople.get(k);
                if (sick != null)
                {
                    if (sick.tryToDie())
                    {
                        this.sickPeople.remove(sick);
                        this.addDeadPeople();
                    }
                }
            }
            //System.out.println( " people dead in "+ this.getName());
        }
        double deadPercent = 0.01;
        if (this.getDeadPeople() >= this.getCurrentPopulation() * deadPercent)
        { StatisticsFile.LogWriting(this); }

    }

    //*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*

    public synchronized void partThreeSimoTwo()
    {
        //make in every settlement all the sick people to convalescent if its been 25 days from the contage day
        for (int j = 0; j < this.getSickPeople().size(); j++)
        {
            if ((Simulation.Clock.now() - ((Sick) this.getSickPeople().get(j)).getContagiousTime()) > 25)  //if past 25 days from the day the person got contagious
            {
                //need to make the sick person to convalescent so I will make new convalescent obj and
                //put all the data of the sick person to the new obj
                Person As = new Convalescent(((Sick) this.getSickPeople().get(j)).getAge(),
                        ((Sick) this.getSickPeople().get(j)).getLocation(),
                        ((Sick) this.getSickPeople().get(j)).getSettlement(),
                        ((Sick) this.getSickPeople().get(j)).getVirus());

                this.getSickPeople().remove(j);
                this.getHealthyPeople().add(As);
                //System.out.println(" person become healthy in:  " + this.getName());


            }

        }
    }

    //*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*

    public synchronized void partFourSimoTwo()
    {
        //In every settlement show 3% of people that trying to random neighbor
        //Try to move to that neighbor settlement

        List<Person> sickANDhealthyPersonList = new ArrayList<>(this.getHealthyPeople());

        for (int j = this.getHealthyPeople().size(); j < this.getSickPeople().size(); j++)
        {
            sickANDhealthyPersonList.add(this.getSickPeronByIndex(j));
        }
        //now the array contains all person- sick and healthy

        for (int j = 0; j < sickANDhealthyPersonList.size() * 0.03; j++)
        {
            Random rn = new Random();
            Random rp = new Random();
            int a = rp.nextInt(sickANDhealthyPersonList.size());
            int b = rn.nextInt(sickANDhealthyPersonList.get(a).getSettlement().getNeighbors().size());

            if (sickANDhealthyPersonList.get(a).getSettlement().transferPerson(sickANDhealthyPersonList.get(a),
            sickANDhealthyPersonList.get(a).getSettlement().getNeighbors().get(b)) )
            {
                sickANDhealthyPersonList.remove(j);
            }
        }

    }

    //*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*

    public synchronized void partFiveSimoTwo()
    {

        //inoculate (לחסן) healthy people and update the

        if (this.getVaccineDoses() > 0)   //it means that the settlement has vaccine Doses
        {
            if (this.getVaccineDoses() >= this.getHealthyPeople().size())
            {
                for (int j = 0; j < this.getHealthyPeople().size(); j++)
                {
                    Vaccinated personGotVaccinated = new Vaccinated(this.getHealthyPeople().get(j).getAge(),
                            this.getHealthyPeople().get(j).getLocation(),
                            this.getHealthyPeople().get(j).getSettlement(),
                            Simulation.Clock.now());
                    this.setVaccineDoses((this.getVaccineDoses()) - 1);
                   // System.out.println( " people got vaccinated in"+ this.getName());
                }
            }
            else
            {
                for (int j = 0; j < this.getVaccineDoses(); j++)
                {
                    Vaccinated personGotVaccinated = new Vaccinated(this.getHealthyPeople().get(j).getAge(),
                            this.getHealthyPeople().get(j).getLocation(),
                            this.getHealthyPeople().get(j).getSettlement(),
                            Simulation.Clock.now());
                    this.setVaccineDoses((this.getVaccineDoses()) - 1);
                    //System.out.println( " people got vaccinated in"+ this.getName());
                }
            }

        }


    }

    public void ref(Map worldmap) { this.map = worldmap; }

    @Override
    public void run()
    {

        this.dataInitialization();
        ListIterator<Settlement> settlementListIterator=this.map.getSettelmet().listIterator();
        while(settlementListIterator.hasNext())
        {
            synchronized (map)
            {
                while (!map.getPlay())
                {
                    try { map.wait(); }
                    catch (Exception exception){ exception.printStackTrace(); }
                }
            }

            this.simulation2();

            try { map.cyclic_barrier.await(); }
            catch (Exception exception){exception.printStackTrace();}

          //  System.out.println(this.healthyPeople.size() + " Healthy people at the settlement " + this.getName());
            //System.out.println(this.sickPeople.size() +" Sick people at the settlement "+this.getName());
            //System.out.println(this.getRamzorColor() +" ramzor color of the settlement "+this.getName());
            Clock.nextTick();
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }





            //System.out.println(this.getRamzorColor() +" ramzor color of the settlement "+this.getName());
            //System.out.println(this.getNeighbors().size() +" Neighbors of the settlement "+this.getName());
            //System.out.println(this.currentPopulation +" People in the settlement "+this.getName());
            //System.out.println(this.maxPopulation +" Max People in the settlement "+this.getName());
            //System.out.println(this.getType() +" Type of the settlement "+this.getName());
        }
    }


