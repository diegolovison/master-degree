package bqp.algorithm;

import bqp.QbpSolution;
import metrics.HasMetrics;

import static utils.Utils.copy;
import static utils.Utils.random;

public abstract class QbpLocalSearch extends HasMetrics {

    protected abstract QbpSolution search(int[][] matrix, int[] solution);

    public QbpSolution solve(int[][] matrix, int[] solution) {

        start();

        QbpSolution candidate = search(matrix, solution);

        finish();

        return candidate;
    }

    public int[] getNeighbor(int[]solution, int i) {

        int[] neighbor = copy(solution);
        neighbor[i] = 1 - solution[i];

        return neighbor;
    }

    public int getNeighborCost(int[][] matrix, int solutionCost, int[] neighbor, int k) {

        int sum = 0;
        for (int j = 0; j < matrix.length; j++) {
            if (j != k) {
                sum += matrix[j][k] * neighbor[j];
            }
        }

        int result = (1 - (2 *  neighbor[k])) *  (matrix[k][k] + (2 * sum));

        return solutionCost - result;
    }

    public int getCost(int[][] matrix, int[] solution) {

        int cost = 0;

        for (int i=0; i<matrix.length; i++) {
            for (int j=0; j<matrix[i].length; j++) {
                cost += matrix[i][j] * solution[i] * solution[j];
            }
        }

        return cost;
    }

    public int[] getRandomSolution(int lenght) {

        int[] solution = new int[lenght];

        for (int i=0; i<solution.length; i++) {
            solution[i] = random(0 ,1);
        }

        return solution;
    }
}
