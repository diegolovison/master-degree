package acsflowshop;

public class ACSFlowShop {

    private int[][] instance;
    private int numberOfJobs;
    private int numberOfMachines;

    private int iteration;
    private int ant;
    private double a;
    private double beta;
    private double p;
    private double q0;
    private int ub;
    private int lowerBound;
    private int t0;

    private double[][] t = null;

    // Initialize the pheromone trail
    // set parameters
    public ACSFlowShop(TaillardInstance instance, int iteration, int ant, double a, double beta,
                       double p, double q0) {

        this.instance = instance.getMatrix();
        this.numberOfJobs = instance.getNumberOfJobs();
        this.numberOfMachines = instance.getNumberOfMachines();

        this.iteration = iteration;
        this.ant = ant;
        this.a = a;
        this.beta = beta;
        this.p = p;
        this.q0 = q0;
        this.ub = instance.getUpperBound();
        this.lowerBound = instance.getLowerBound();

        this.t = new double[numberOfMachines][numberOfJobs];
    }

    public int solve() {

        int globalBestValue = 0;

        // Loop at this level each loop is called an iteration
        for (int interationCount=0; interationCount<iteration; interationCount++) {

            // Loop at this level each loop is called a step
            for (int antCount=0; antCount<ant; antCount++) {

                // Each ant repeatedly applies state transition rule to select the
                // next node until a tour is constructed
                int[][] path = pathSelection();
            }

            int cmax = calculateCMax();

            // Apply global updating rule to increase pheromone on edges of the
            // current best tour and decrease pheromone on other edges
            increasePheromone(cmax);

            if (cmax < globalBestValue) {
                globalBestValue = cmax;
            }

            if (globalBestValue == lowerBound) break;
        }

        return globalBestValue;
    }

    private void increasePheromone(int cmax) {

        for (int i=0; i<numberOfMachines; i++) {
            for (int j=0; j<numberOfJobs; j++) {

                increasePheromone(i, j, cmax);
            }
        }
    }

    private void increasePheromone(int i, int j, int cmax) {

        t[i][j] = ((1 - a) * t[i][j]) + ((a * pheromoneDelta(i, j, cmax)));
    }

    public boolean belongsGlobalBestTour(int i, int j) {

        return false;
    }

    private double pheromoneDelta(int i, int j, int cmax) {

        if (belongsGlobalBestTour(i, j)) {
            return Math.pow(cmax, -1);
        } else {
            return 0;
        }
    }

    private int[][] pathSelection() {

        int[][] path = new int[numberOfMachines][numberOfJobs];

        findFirstNode(path);
        findOtherNodes(path);

        return path;
    }

    private void decreasePheromone(int i, int j) {

        t[i][j] = ((1 - p) * t[i][j]) + (p * t0);
    }

    private int calculateCMax() {

        return 0;
    }

    private void findOtherNodes(int[][] path) {

    }

    private void findFirstNode(int[][] path) {

    }
}
