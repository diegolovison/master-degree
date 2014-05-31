package test;

public class ACSFlowShop {

    private int[][] instance;
    private int iteration;
    private int ant;
    private int numberOfJobs;
    private int numberOfMachines;

    private double[][] pheromone;
    private int[][] solution;

    // Initialize the pheromone trail
    // set parameters
    public ACSFlowShop() {

    }

    public void run() {

        // Loop at this level each loop is called an iteration
        for (int interationCount=0; interationCount<iteration; interationCount++) {

            // Loop at this level each loop is called a step
            for (int antCount=0; antCount<ant; antCount++) {

                // Each ant repeatedly applies state transition rule to select the
                // next node until a tour is constructed
                int[][] path = pathSelection();

                decreasePheromone(path);
            }

            // Apply global updating rule to increase pheromone on edges of the
            // current best tour and decrease pheromone on other edges
            increasePheromone(solution);
        }

    }

    private void increasePheromone(int[][] solution) {


    }

    private int[][] pathSelection() {

        int[][] path = new int[numberOfMachines][numberOfJobs];

        findFirstNode(path);
        findOtherNodes(path);

        return path;
    }

    private void decreasePheromone(int[][] path) {

    }

    private void calculateCMax(int[][] path) {

    }

    private void findOtherNodes(int[][] path) {

    }

    private void findFirstNode(int[][] path) {

    }
}
