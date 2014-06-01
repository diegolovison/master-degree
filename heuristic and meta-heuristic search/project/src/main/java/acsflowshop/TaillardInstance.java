package acsflowshop;

import java.util.Arrays;
import java.util.Collections;

public class TaillardInstance {

    private int numberOfJobs;
    private int numberOfMachines;
    private int upperBound;
    private int lowerBound;
    private int[][] instance;
    private double[][] path;

    public TaillardInstance(int numberOfMachines, int numberOfJobs, int upperBound,
                            int lowerBound, int[][] instance) {

        this.numberOfMachines = numberOfMachines;
        this.numberOfJobs = numberOfJobs;
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
        this.instance = instance;

        this.path = createPath();
    }

    private double[][] createPath() {

        double[][] path = new double[numberOfJobs][numberOfJobs];

        for (int i=0; i<numberOfJobs; i++) {
            for (int u=0; u<numberOfJobs; u++) {
                path[i][u] = length(i, u);
            }
        }
        return path;
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
}