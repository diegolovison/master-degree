package bqp.algorithm;

import bqp.QbpSolution;

import java.util.concurrent.TimeUnit;

import static utils.Utils.copy;
import static utils.Utils.random;

public class QbpStochasticAlgorithm extends QbpLocalSearch {

    private final int bestSolutionCost;
    private final int probability;

    public QbpStochasticAlgorithm(int bestSolutionCost, int probability) {
        this.bestSolutionCost = bestSolutionCost;
        this.probability = probability;
    }

    @Override
    protected QbpSolution search(int[][] matrix, int[] solution) {

        int[] candidate = copy(solution);
        int candidateCost = getCost(matrix, solution);

        int[] bestCandidate = candidate;
        int bestCandidateCost = candidateCost;

        do {

            increaseIteration();

            if (random(0, 100) < probability) { // random walk

                int i = random(0, matrix.length - 1);

                int[] neighbor = getNeighbor(candidate, i);
                int neighborCost = getNeighborCost(matrix, candidateCost, neighbor, i);

                candidate = neighbor;
                candidateCost = neighborCost;

            } else { // local search - first improvement

                for (int i=0; i<matrix.length; i++) {

                    int[] neighbor = getNeighbor(candidate, i);
                    int neighborCost = getNeighborCost(matrix, candidateCost, neighbor, i);

                    if (neighborCost < candidateCost) {

                        candidate = neighbor;
                        candidateCost = neighborCost;

                        break;
                    }
                }
            }

            // return always the best :)
            if (candidateCost < bestCandidateCost) {

                bestCandidate = copy(candidate);
                bestCandidateCost = candidateCost;
            }

        } while(!mayIStop(bestCandidateCost));

        return new QbpSolution(bestCandidate, bestCandidateCost);
    }

    private boolean mayIStop(int solutionCost) {

        boolean isBestSolution = solutionCost == bestSolutionCost;
        boolean is5Minutes = currentTimeInMinutes() >= 5;

        return isBestSolution || is5Minutes;
    }

    private long currentTimeInMinutes() {

        long total = System.nanoTime() - start;
        return TimeUnit.MINUTES.convert(total, TimeUnit.NANOSECONDS);
    }


}
