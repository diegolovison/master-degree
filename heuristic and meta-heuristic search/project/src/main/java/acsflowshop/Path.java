package acsflowshop;

import java.util.Comparator;

/**
 * Created by Diego_Lovison on 6/3/2014.
 */
public class Path implements Comparable<Path> {

    private int from;
    private int job;
    private Double pheromone;

    public Path(int job) {
        this.job = job;
    }

    public Path(int from, int job) {

        this(job);

        this.from = from;
    }

    public int getFrom() {
        return from;
    }

    public int getJob() {
        return job;
    }

    public Double getPheromone() {
        return pheromone;
    }

    public void setPheromone(Double pheromone) {
        this.pheromone = pheromone;
    }

    @Override
    public int compareTo(Path o) {
        return o.getPheromone().compareTo(pheromone);
    }

    @Override
    public String toString() {
        return "Path{" +
                "job=" + job +
                ", pheromone=" + pheromone +
                '}';
    }
}
