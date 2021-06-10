package Virus;

import java.util.ArrayList;
import java.util.Random;

public enum VirusManager
{
    ChineseVariant(new ChineseVariant(),"Chinese Variant"),
    BritishVariant(new BritishVariant(),"British Variant"),
    SouthAfricanVariant(new SouthAfricanVariant(),"South African Variant");

    private String variant_name;
    private IVirus virus;
    private ArrayList<IVirus> variant_to_become;


    VirusManager(IVirus virus,String name)
    {
        this.variant_name=name;
        this.virus=virus;
        this.variant_to_become=new ArrayList<>(3);
    }

    public IVirus To_develop(IVirus v)
    {
        if (v== BritishVariant.getVirus())
            return BritishVariant.RandVirus();
        if (v== SouthAfricanVariant.getVirus())
            return SouthAfricanVariant.RandVirus();
        if  (v== ChineseVariant.getVirus())
            return ChineseVariant.RandVirus();
        return null;
    }


    public IVirus getVirus() {
        return virus;
    }

    public void add(IVirus v)
    {
        this.variant_to_become.add(v);
    }

    public void remove()
    {
        if (this.variant_to_become.contains(virus))
            this.variant_to_become.remove(virus);
    }

    public IVirus RandVirus()
    {
        Random random=new Random();
        int randomVariant=random.nextInt(variant_to_become.size());
        return variant_to_become.get(randomVariant);
    }


}
