package acsflowshop;

import java.util.*;

public class ACSFlowShop {

    private static final Random random = new Random();

    private int[][] instance;
    private int numberOfJobs;
    private int numberOfMachines;

    private int iteration;
    private int ant;
    private double a;
    private int B;
    private double p;
    private double q0;
    private double t0;

    private double[][] t;
    private double[] path;

    public ACSFlowShop(int numberOfMachines, int numberOfJobs, int[][] instance) {

        this.numberOfMachines = numberOfMachines;
        this.numberOfJobs = numberOfJobs;

        this.instance = instance;
    }

    public ACSFlowShop path(double[] path) {
        this.path = path;
        return this;
    }

    public ACSFlowShop t0(double t0) {
        this.t0 = t0;
        return this;
    }

    public ACSFlowShop t(double[][] t) {
        this.t = t;
        return this;
    }

    public ACSFlowShop q0(double q0) {
        this.q0 = q0;
        return this;
    }

    public ACSFlowShop p(double p) {
        this.p = p;
        return this;
    }

    public ACSFlowShop B(int B) {
        this.B = B;
        return this;
    }

    public ACSFlowShop a(double a) {
        this.a = a;
        return this;
    }

    public ACSFlowShop ant(int ant) {
        this.ant = ant;
        return this;
    }

    public ACSFlowShop iteration(int iteration) {
        this.iteration = iteration;
        return this;
    }

    public int solve() {

        int bestCmax = Integer.MAX_VALUE;
        List<Integer> bestSchedule = new ArrayList<Integer>();

        // Loop at this level each loop is called an iteration
        for (int iterationCount=0; iterationCount<iteration; iterationCount++) {

            int bestLocalCmax = Integer.MAX_VALUE;
            boolean[][] bestLocalPath = null;

            // Loop at this level each loop is called a step
            for (int antCount=0; antCount<ant; antCount++) {

                // Each ant repeatedly applies state transition rule to select the
                // next node until a tour is constructed
                List<Integer> unvisitedJobs = new ArrayList<Integer>(numberOfJobs);
                for (int i=0; i<numberOfJobs; i++) {
                    unvisitedJobs.add(i);
                }

                List<Integer> schedule = new ArrayList<Integer>();
                boolean[][] localPath = new boolean[numberOfJobs][numberOfJobs];

                int currentJob = 0;
                for (int count=0; count<numberOfJobs; count++) {

                    Integer j = getNext(currentJob, unvisitedJobs);

                    localPath[currentJob][j] = true;

                    localUpdatePheromone(currentJob, j);

                    schedule.add(j);
                    unvisitedJobs.remove(j);

                    currentJob = j;
                }

                int localCmax = getMakeSpan(schedule, instance);

                if (localCmax < bestLocalCmax) {
                    bestLocalCmax = localCmax;
                    bestLocalPath = localPath;
                }

                if (localCmax < bestCmax) {
                    bestCmax = localCmax;
                    bestSchedule = schedule;
                }
            }

            // Apply global updating rule to increase pheromone on edges of the
            // current best tour and decrease pheromone on other edges
            globalUpdatePheromone(bestLocalPath, bestLocalCmax);
        }

        return bestCmax;
    }

    public int getMakeSpan(Collection<Integer> schedule, int[][] jobInfo) {

        int[] machinesTime = new int[numberOfMachines];
        int time;

        for (Integer job : schedule) {
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

        for (int i=0; i<numberOfJobs;i++) {
            for (int j=0; j<numberOfJobs; j++) {

                if (bestLocalPath[i][j]) {
                    t[i][j] = ((1 - a) * t[i][j]) + (a * (Math.pow(cmax, -1)));
                } else {
                    t[i][j] = ((1 - a) * t[i][j]);
                }
            }
        }
    }

    private void localUpdatePheromone(int i, int j) {

        t[i][j] = (1 - p) * t[i][j] + (p * t0);
    }

    private int getNext(int i, List<Integer> unvisitedJobs) {

        if (q() <= q0) {
            return transitionRule(i, unvisitedJobs);
        } else {
            return randomProportionalRule(i, unvisitedJobs);
        }
    }

    private int transitionRule(int i, List<Integer> unvisitedJobs) {

        double max = -1;
        int index = -1;

        for (int u : unvisitedJobs) {

            double value = transitionValue(i, u);

            if (value > max) {
                max = value;
                index = u;
            }
        }

        return index;
    }

    private double transitionValue(int i, int u) {

        return t[i][u] * Math.pow(path[u], B);
    }

    private int randomProportionalRule(int i, List<Integer> unvisitedJobs) {

        List<Double> probabilities = new ArrayList<Double>();

        double last = 0.0;

        Double divisor = 0.0;
        for (int u : unvisitedJobs) {
            divisor += transitionValue(i, u);
        }

        for (int j : unvisitedJobs) {

            double dividend = transitionValue(i, j);

            double quotient = dividend / divisor;

            quotient = quotient + last;

            probabilities.add(quotient);

            last = quotient;
        }

        double randomValue = rand(probabilities.get(0), probabilities.get(probabilities.size() - 1));

        int j = -1;

        for (int index=0; index<probabilities.size(); index++) {

            int next = unvisitedJobs.get(index);

            Double probability = probabilities.get(index);

            if (probability >= randomValue) {
                j = next;
                break;
            }
        }

        return j;
    }

    private double rand(double rangeMin, double rangeMax) {
        return rangeMin + (rangeMax - rangeMin) * random.nextDouble();
    }

    private double q() {
        return random.nextDouble();
    }
}
