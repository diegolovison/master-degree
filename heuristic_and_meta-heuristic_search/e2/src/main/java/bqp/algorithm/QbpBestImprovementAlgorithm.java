package bqp.algorithm;

import bqp.QbpSolution;

import static utils.Utils.copy;

public class QbpBestImprovementAlgorithm extends QbpLocalSearch {

    @Override
    public QbpSolution search(int[][] matrix, int[] solution) {

        boolean hasImprovement;

        int[] candidate = copy(solution);
        int candidateCost = getCost(matrix, candidate);

        do {

            int[] bestNeighbor = candidate;
            int bestNeighborCost = candidateCost;

            increaseIteration();

            for (int i=0; i<matrix.length; i++) {

                int[] neighbor = getNeighbor(candidate, i);
                int neighborCost = getNeighborCost(matrix, candidateCost, neighbor, i);

                if (neighborCost < bestNeighborCost) {

                    bestNeighbor = neighbor;
                    bestNeighborCost = neighborCost;
                }
            }

            hasImprovement = bestNeighborCost < candidateCost;

            if (hasImprovement) {

                candidate = bestNeighbor;
                candidateCost = bestNeighborCost;
            }

        } while (hasImprovement);

        return new QbpSolution(candidate, candidateCost);
    }
}
