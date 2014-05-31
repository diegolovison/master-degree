package test;

/**
 * Created by diego on 31/05/2014.
 */
public class TaillardInstance {

    private int numberOfJobs;
    private int numberOfMachines;
    private int upperBound;
    private int lowerBound;
    private int[][] matrix;

    public TaillardInstance(int numberOfJobs, int numberOfMachines, int upperBound,
                            int lowerBound, int[][] matrix) {

        this.numberOfJobs = numberOfJobs;
        this.numberOfMachines = numberOfMachines;
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
        this.matrix = matrix;
    }

    public int getNumberOfJobs() {
        return numberOfJobs;
    }

    public int getNumberOfMachines() {
        return numberOfMachines;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public int[][] getMatrix() {
        return matrix;
    }
}
