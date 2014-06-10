package acsflowshop;

public class TaillardInstance {

    private int numberOfJobs;
    private int numberOfMachines;
    private int lowerBound;
    private int[][] instance;
    private double t0;

    public TaillardInstance(int numberOfMachines, int numberOfJobs, int upperBound,
                            int lowerBound, int[][] instance) {

        this.numberOfMachines = numberOfMachines;
        this.numberOfJobs = numberOfJobs;
        this.lowerBound = lowerBound;
        this.instance = instance;

        this.t0 = Math.pow((numberOfJobs * upperBound), -1);
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

    public double getT0() {
        return t0;
    }
}
