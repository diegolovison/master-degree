package acsflowshop;

import java.util.*;

public class ACSFlowShop {

    private static final Random random = new Random();

    private int[][] instance;
    private double[][] path;
    private int numberOfJobs;
    private int numberOfMachines;

    private int iteration;
    private int ant;
    private double a;
    private double B;
    private double p;
    private double q0;
    private int lowerBound;
    private double t0;

    private double[][] t;

    public ACSFlowShop(int numberOfMachines, int numberOfJobs) {

        this.numberOfMachines = numberOfMachines;
        this.numberOfJobs = numberOfJobs;

        this.t = new double[numberOfJobs][numberOfJobs];
    }

    // Initialize the pheromone trail
    // set parameters
    public ACSFlowShop(TaillardInstance instance, int iteration, int ant, double a, double B,
                       double p, double q0) {

        this(instance.getNumberOfMachines(), instance.getNumberOfJobs());

        this.instance = instance.getInstance();
        this.path = instance.getPath();

        this.iteration = iteration;
        this.ant = ant;
        this.a = a;
        this.B = B;
        this.p = p;
        this.q0 = q0;
        this.t0 = Math.pow((numberOfJobs * instance.getUpperBound()), -1);
        this.lowerBound = instance.getLowerBound();

        for (int i=0; i<numberOfJobs; i++) {
            for (int j=0; j<numberOfJobs; j++) {
                t[i][j] = t0;
            }
        }
    }

    public int solve() {

        int bestGlobalCmax = Integer.MAX_VALUE;

        // Loop at this level each loop is called an iteration
        for (int iterationCount=0; iterationCount<iteration; iterationCount++) {

            int bestLocalCmax = Integer.MAX_VALUE;
            boolean[][] bestLocalPath = null;

            // Loop at this level each loop is called a step
            for (int antCount=0; antCount<ant; antCount++) {

                // Each ant repeatedly applies state transition rule to select the
                // next node until a tour is constructed
                Object[] result = walk();
                List<Integer> schedule = (List<Integer>) result[0];
                boolean[][] localPath = (boolean[][]) result[1];

                int localCmax = getMakeSpan(schedule, instance);

                if (localCmax < bestLocalCmax) {
                    bestLocalCmax = localCmax;
                    bestLocalPath = localPath;
                }
            }

            if (bestLocalCmax < bestGlobalCmax) {
                System.out.println(String.format(" > iteration #{(%d)}, best=#{%d}, lowerbound=#{%d}", iterationCount, bestLocalCmax, lowerBound));
            }

            // Apply global updating rule to increase pheromone on edges of the
            // current best tour and decrease pheromone on other edges
            globalUpdatePheromone(bestLocalPath, bestLocalCmax);

            bestGlobalCmax = calculateCMax();
            if (bestGlobalCmax == lowerBound) break;
        }

        return bestGlobalCmax;
    }

    public int getMakeSpan(List<Integer> schedule, int[][] jobInfo) {

        int[] machinesTime = new int[numberOfMachines];
        int time;

        for (int job : schedule) {
            for (int i = 0; i < numberOfMachines; i++) {
                time = jobInfo[i][job];
                if (i == 0) {
                    machinesTime[i] = machinesTime[i] + time;
                } else {
                    if (machinesTime[i] > machinesTime[i - 1]) {
                        machinesTime[i] = machinesTime[i] + time;
                    } else {
                        machinesTime[i] = machinesTime[i - 1] + time;
                    }
                }
            }
        }

        return machinesTime[numberOfMachines - 1];
    }

    private void globalUpdatePheromone(boolean[][] bestLocalPath, int cmax) {

        for (int i=0; i<numberOfJobs; i++) {
            for (int j=0; j<numberOfJobs; j++) {

                if (bestLocalPath[i][j]) {
                    t[i][j] = ((1.0 - a) * t[i][j]) + ((a * Math.pow(cmax, -1)));
                }
            }
        }
    }

    private Object[] walk() {

        List<Integer> schedule = new ArrayList<Integer>(numberOfJobs);
        boolean[][] localPath = new boolean[numberOfJobs][numberOfJobs];

        for (int i=0; i<numberOfJobs; i++) {

            int j = getNext(schedule, i);

            schedule.add(j);

            localUpdatePheromone(i, j);
            localPath[i][j] = true;
        }

        Object[] result = new Object[2];
        result[0] = schedule;
        result[1] = localPath;

        return result;
    }

    private void localUpdatePheromone(int i, int j) {

        double value = ((1.0 - p) * t[i][j]) + (p * t0);

        t[i][j] = value;
        t[j][i] = value;
    }

    private int calculateCMax() {

        Integer[] schedule = new Integer[numberOfJobs];
        final Double[] data = new Double[numberOfJobs];

        for (int i=0; i<numberOfJobs; i++) {

            double sum = 0;
            for (int j=0; j<numberOfJobs; j++) {
                sum += t[i][j];
            }

            schedule[i] = i;
            data[i] = sum;
        }

        Arrays.sort(schedule, new Comparator<Integer>() {
            @Override
            public int compare(final Integer o1, final Integer o2) {
                return Double.compare(data[o1], data[o2]);
            }
        });

        int makespan = getMakeSpan(Arrays.asList(schedule), instance);

        return makespan;
    }

    private int getNext(List<Integer> schedule, int i) {

        if (q() <= q0) {
            return transitionRule(schedule, i);
        } else {
            return randomProportionalRule(schedule, i);
        }
    }

    private int transitionRule(List<Integer> schedule, int i) {

        double max = -1;
        int index = -1;

        for (int u=0; u<numberOfJobs; u++) {

            if (!isFeasible(schedule, u)) continue;

            double value = transitionValue(i, u);

            if (value > max) {
                max = value;
                index = u;
            }
        }

        return index;
    }

    private double transitionValue(int i, int u) {
        return t[i][u] * Math.pow(path[i][u], B);
    }

    private int randomProportionalRule(List<Integer> schedule, int i) {

        double max = -1;
        int index = -1;

        for (int j = 0; j < numberOfJobs; j++) {

            if (!isFeasible(schedule, j)) continue;

            double dividend = transitionValue(i, j);

            Double divisor = 0.0;

            for (int u = 0; u < numberOfJobs; u++) {

                if (isFeasible(schedule, u)) {
                    divisor += transitionValue(i, u);
                }
            }

            double quotient = dividend / divisor;

            if (quotient > max) {
                max = quotient;
                index = j;
            }
        }

        return index;
    }

    private boolean isFeasible(List<Integer> schedule, int j) {

        if (schedule.contains(j)) {
            return false;
        } else {
            return true;
        }
    }

    private double q() {
        return random.nextDouble();
    }
}
