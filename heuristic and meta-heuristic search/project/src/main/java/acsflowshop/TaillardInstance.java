package acsflowshop;

public class TaillardInstance {

    private int numberOfJobs;
    private int numberOfMachines;
    private int upperBound;
    private int lowerBound;
    private int[][] instance;
    private double[][] path;
    private double[][] t;
    private double t0;

    public TaillardInstance(int numberOfMachines, int numberOfJobs, int upperBound,
                            int lowerBound, int[][] instance) {

        this.numberOfMachines = numberOfMachines;
        this.numberOfJobs = numberOfJobs;
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
        this.instance = instance;

        this.t0 = Math.pow((numberOfJobs * upperBound), -1);

        createPath();
        createPhoromone();
    }

    private void createPhoromone() {

        t = new double[numberOfJobs][numberOfJobs];

        for (int i=0; i<numberOfJobs; i++) {
            for (int j=0; j<numberOfJobs; j++) {
                t[i][j] = t0;
            }
        }
    }

    private void createPath() {

        path = new double[numberOfJobs][numberOfJobs];

        for (int i=0; i<numberOfJobs; i++) {
            for (int u=0; u<numberOfJobs; u++) {
                path[i][u] = length(i, u);
            }
        }
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

    public int[][] getInstance() {
        return instance;
    }

    public double[][] getPath() {
        return path;
    }

    private double length(int i, int u) {

        double length = 0;

        if (i != u) {

            double minLength;

            int m = numberOfMachines - 1;

            length += (numberOfMachines - 1) * instance[m--][u];
            minLength = length;

            for (; m>=0;) {

                length += (numberOfMachines - 3) * instance[m--][u];
                if (length < minLength) {
                    minLength = length;
                }
            }

            length -= (numberOfMachines - 3) * instance[1][u];
            if (length < minLength) {
                minLength = length;
            }

            length -= (numberOfMachines - 1) * instance[0][u];
            if (length < minLength) {
                minLength = length;
            }

            length -= minLength + 1;

            length = 1 / length;
        }

        return length;
    }

    public double[][] getT() {
        return t;
    }

    public double getT0() {
        return t0;
    }
}
