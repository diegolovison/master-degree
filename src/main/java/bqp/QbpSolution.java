package bqp;

public class QbpSolution implements Comparable<QbpSolution> {

    private int[] vector;
    private Integer cost;
    private Integer distance;

    public QbpSolution() {
    }

    public QbpSolution(int[] vector, Integer cost) {
        this.vector = vector;
        this.cost = cost;
    }

    @Override
    public int compareTo(QbpSolution o) {
        return cost.compareTo(o.cost);
    }

    public int[] getVector() {
        return vector;
    }

    public void setVector(int[] vector) {
        this.vector = vector;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "cost=" + cost +
                ", distance=" + distance +
                '}';
    }

}