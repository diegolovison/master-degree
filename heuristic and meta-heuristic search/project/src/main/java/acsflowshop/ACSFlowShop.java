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
                       double p, double q0, double[][] t, double t0) {

        this(instance.getNumberOfMachines(), instance.getNumberOfJobs());

        this.instance = instance.getInstance();
        this.path = instance.getPath();

        this.iteration = iteration;
        this.ant = ant;
        this.a = a;
        this.B = B;
        this.p = p;
        this.q0 = q0;
        this.t = t;
        this.t0 = t0;
    }

    public int solve() {

        // Loop at this level each loop is called an iteration
        for (int iterationCount=0; iterationCount<iteration; iterationCount++) {

            int bestLocalCmax = Integer.MAX_VALUE;
            List<Path> bestLocalSchedule = null;

            // Loop at this level each loop is called a step
            for (int antCount=0; antCount<ant; antCount++) {

                // Each ant repeatedly applies state transition rule to select the
                // next node until a tour is constructed
                List<Path> localSchedule = walk();

                int localCmax = getMakeSpan(localSchedule, instance);

                if (localCmax < bestLocalCmax) {
                    bestLocalCmax = localCmax;
                    bestLocalSchedule = localSchedule;
                }
            }

            // Apply global updating rule to increase pheromone on edges of the
            // current best tour and decrease pheromone on other edges
            globalUpdatePheromone(bestLocalSchedule, bestLocalCmax);
        }

        return calculateCMax();
    }

    public int getMakeSpan(Collection<Path> schedule, int[][] jobInfo) {

        int[] machinesTime = new int[numberOfMachines];
        int time;

        for (Path path : schedule) {
            for (int i = 0; i < numberOfMachines; i++) {
                time = jobInfo[i][path.getJob()];
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

    private void globalUpdatePheromone(List<Path> bestSchedule, int cmax) {

        for (Path path : bestSchedule) {
            t[path.getFrom()][path.getJob()] = ((1.0 - a) * t[path.getFrom()][path.getJob()]) + ((a * Math.pow(cmax, -1)));
        }
    }

    private List<Path> walk() {

        List<Integer> unvisitJobs = new ArrayList<Integer>(numberOfJobs);
        for (int i=0; i<numberOfJobs; i++) {
            unvisitJobs.add(i);
        }

        List<Path> schedule = new ArrayList<Path>();

        // first node
        int currentJob = getNext(0, unvisitJobs);
        updateData(0, currentJob, schedule, unvisitJobs);


        for (int count=1; count<numberOfJobs; count++) {

            int j = getNext(currentJob, unvisitJobs);

            updateData(currentJob, j, schedule, unvisitJobs);

            currentJob = j;
        }

        return schedule;
    }

    private void updateData(int from, Integer job, List<Path> schedule, List<Integer> unvisitJobs) {

        localUpdatePheromone(from, job);
        schedule.add(new Path(from, job));
        unvisitJobs.remove(job);
    }

    private void localUpdatePheromone(int i, int j) {

        t[i][j] = ((0.99 - p) * t[i][j]) + (p * t0);
    }

    private int calculateCMax() {

        List<Path> schedule = new ArrayList<Path>();

        for (int i=0; i<numberOfJobs; i++) {

            double sum = 0;
            for (int j=0; j<numberOfJobs; j++) {
                sum += t[i][j];
            }

            Path path = new Path(i);
            path.setPheromone(sum);

            schedule.add(path);
        }

        Collections.sort(schedule);

        return getMakeSpan(schedule, instance);
    }

    private int getNext(int i, List<Integer> unvisitJobs) {

        if (q() <= q0) {
            return transitionRule(i, unvisitJobs);
        } else {
            return randomProportionalRule(i, unvisitJobs);
        }
    }

    private int transitionRule(int i, List<Integer> unvisitJobs) {

        double max = -1;
        int index = -1;

        for (int u : unvisitJobs) {

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

    private int randomProportionalRule(int i, List<Integer> unvisitJobs) {

        double max = -1;
        int index = -1;

        for (int j : unvisitJobs) {

            double dividend = transitionValue(i, j);

            Double divisor = 0.0;
            for (int u : unvisitJobs) {
                divisor += transitionValue(i, u);
            }

            double quotient = dividend / divisor;

            if (quotient > max) {
                max = quotient;
                index = j;
            }
        }

        return index;
    }

    private double q() {
        return random.nextDouble();
    }
}
