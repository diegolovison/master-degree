package test;

public class ACSFlowShop {

    private int[][] instance;
    private int numberOfJobs;
    private int numberOfMachines;

    private int iteration;
    private int ant;
    private double alpha;
    private double beta;
    private double p;
    private double q0;
    private int ub;
    private int lowerBound;
    private int t0;

    private double[][] solution = null;

    // Initialize the pheromone trail
    // set parameters
    public ACSFlowShop(TaillardInstance instance, int iteration, int ant, double alpha, double beta,
                       double p, double q0) {

        this.instance = instance.getMatrix();
        this.numberOfJobs = instance.getNumberOfJobs();
        this.numberOfMachines = instance.getNumberOfMachines();

        this.iteration = iteration;
        this.ant = ant;
        this.alpha = alpha;
        this.beta = beta;
        this.p = p;
        this.q0 = q0;
        this.ub = instance.getUpperBound();
        this.lowerBound = instance.getLowerBound();

        this.solution = new double[numberOfMachines][numberOfJobs];
    }

    public int solve() {

        int cmax = 0;

        // Loop at this level each loop is called an iteration
        for (int interationCount=0; interationCount<iteration; interationCount++) {

            // Loop at this level each loop is called a step
            for (int antCount=0; antCount<ant; antCount++) {

                // Each ant repeatedly applies state transition rule to select the
                // next node until a tour is constructed
                int[][] path = pathSelection();
            }

            // Apply global updating rule to increase pheromone on edges of the
            // current best tour and decrease pheromone on other edges
            increasePheromone();

            cmax = calculateCMax();
            if (cmax == lowerBound) break;
        }

        return cmax;
    }

    private void increasePheromone() {

    }

    private int[][] pathSelection() {

        int[][] path = new int[numberOfMachines][numberOfJobs];

        findFirstNode(path);
        findOtherNodes(path);

        return path;
    }

    private void decreasePheromone(int i, int j) {

        solution[i][j] = ((1 - p) * solution[i][j]) + (p * t0);
    }

    private int calculateCMax() {

        return 0;
    }

    private void findOtherNodes(int[][] path) {

    }

    private void findFirstNode(int[][] path) {

    }
}
