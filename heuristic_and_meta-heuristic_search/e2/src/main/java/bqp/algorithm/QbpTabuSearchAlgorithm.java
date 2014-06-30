package bqp.algorithm;

import bqp.QbpSolution;

import static utils.Utils.copy;

public class QbpTabuSearchAlgorithm extends QbpLocalSearch {

    private final int d;

    public QbpTabuSearchAlgorithm(long d) {
        this.d = (int)d;
    }

    @Override
    protected QbpSolution search(int[][] matrix, int[] solution) {

        int[] tabuList = new int[matrix.length];

        int maxIteration = 20 * matrix.length;
        int count = 0;

        int[] current = copy(solution);
        int currentCost = getCost(matrix, current);

        int[] best = current;
        int bestCost = currentCost;

        while (count++ != maxIteration) {

            increaseIteration();

            int[] bestCandidate = current;
            int bestCandidateCost = Integer.MAX_VALUE;

            int tabu = -1;
            for (int k=0; k<matrix.length; k++) {

                // if the tabu is not present
                if (tabuList[k] == 0) {

                    int[] neighbor = getNeighbor(current, k);
                    int neighborCost = getNeighborCost(matrix, currentCost, neighbor, k);

                    if (neighborCost < bestCandidateCost) {

                        bestCandidate = neighbor;
                        bestCandidateCost = neighborCost;

                        tabu = k;
                    }
                }
            }

            // add the best one as a tabu
            if (tabu >= 0) tabuList[tabu] = d;

            current = bestCandidate;
            currentCost = bestCandidateCost;

            // delete feature tabu
            for (int i=0; i<tabuList.length; i++) {
                if (tabuList[i] > 0 && i != tabu) tabuList[i] = tabuList[i] - 1;
            }

            // avoid lose the best one
            if (currentCost < bestCost) {

                best = copy(current);
                bestCost = currentCost;

                count = 0;
            }
        }

        return new QbpSolution(best, bestCost);
    }
}
