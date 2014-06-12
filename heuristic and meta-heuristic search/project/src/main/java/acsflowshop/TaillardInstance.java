package acsflowshop;

public class TaillardInstance {

    private int numberOfJobs;
    private int numberOfMachines;
    private int lowerBound;
    private int upperBound;
    private int[][] instance;

    public TaillardInstance(int numberOfMachines, int numberOfJobs, int upperBound,
                            int lowerBound, int[][] instance) {

        this.numberOfMachines = numberOfMachines;
        this.numberOfJobs = numberOfJobs;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.instance = instance;
    }

    public int getNumberOfJobs() {
        return numberOfJobs;
    }

    public int getNumberOfMachines() {
        return numberOfMachines;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public int[][] getInstance() {
        return instance;
    }

    public int getUpperBound() {
        return upperBound;
    }
}
