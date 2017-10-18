package dong.hashMap;

/**
 * Created by DONGSHILEI on 2017/10/13
 */
public class Country {
    String name;
    int population;
    public Country(String name, int population) {
        this.name = name;
        this.population = population;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPopulation() {
        return population;
    }
    public void setPopulation(int population) {
        this.population = population;
    }
    @Override
    public boolean equals(Object o) {
        Country c = (Country) o;
        if (this.name.equalsIgnoreCase(c.name))
            return true;
        return false;
    }
    @Override
    public int hashCode() {
        if(this.name.length()%2==0)
            return 0;
        return 1;
    }

}
