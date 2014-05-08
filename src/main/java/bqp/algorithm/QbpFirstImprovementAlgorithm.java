package bqp.algorithm;

import bqp.QbpSolution;

import static utils.Utils.copy;

public class QbpFirstImprovementAlgorithm extends QbpLocalSearch {

    @Override
    public QbpSolution search(int[][] matrix, int[] solution) {

        boolean hasImprovement;

        int[] candidate = copy(solution);
        int candidateCost = getCost(matrix, candidate);

        do {

            hasImprovement = false;

            increaseIteration();

            for (int i=0; i<matrix.length; i++) {

                int[] neighbor = getNeighbor(candidate, i);
                int neighborCost = getNeighborCost(matrix, candidateCost, neighbor, i);

                hasImprovement = neighborCost < candidateCost;

                if (hasImprovement) {

                    candidate = neighbor;
                    candidateCost = neighborCost;

                    break;
                }

            }

        } while (hasImprovement);

        return new QbpSolution(candidate, candidateCost);
    }
}
