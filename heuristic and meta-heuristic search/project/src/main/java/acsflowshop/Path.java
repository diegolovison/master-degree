package acsflowshop;

import java.util.Comparator;

/**
 * Created by Diego_Lovison on 6/3/2014.
 */
public class Path implements Comparable<Path> {

    private int i;
    private Double pheromone;

    public Path(int i, Double pheromone) {
        this.i = i;
        this.pheromone = pheromone;
    }

    public int getI() {
        return i;
    }

    public Double getPheromone() {
        return pheromone;
    }

    @Override
    public int compareTo(Path o) {
        return o.getPheromone().compareTo(pheromone);
    }
}
