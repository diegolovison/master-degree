package bqp.algorithm;

import bqp.QbpSolution;
import bqp.algorithm.QbpLocalSearch;
import bqp.sorter.QbpSolutionDistanceSorter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class QbpScatterSearchAlgorithm extends QbpLocalSearch {

    public enum PathRelinkOption {
        FORWARD, BACKWARD;
    }

    private static final QbpSolutionDistanceSorter DISTANCE_SORTER = new QbpSolutionDistanceSorter();

    private final int bestSolutionCost;
    private final int refSetSize;
    private final int b1;
    private final int b2;

    private boolean runLocalSearch = true;
    private PathRelinkOption pathRelinkOption = PathRelinkOption.FORWARD;

    public QbpScatterSearchAlgorithm(int bestSolutionCost, int refSetSize, int b1, int b2) {
        this.bestSolutionCost = bestSolutionCost;
        this.refSetSize = refSetSize;
        this.b1 = b1;
        this.b2 = b2;
    }

    public QbpScatterSearchAlgorithm(int bestSolutionCost, int refSetSize, int b1, int b2, PathRelinkOption pathRelinkOption) {
        this(bestSolutionCost, refSetSize, b1, b2);
        this.pathRelinkOption = pathRelinkOption;
    }

    public QbpScatterSearchAlgorithm(int bestSolutionCost, int refSetSize, int b1, int b2, boolean runLocalSearch) {
        this(bestSolutionCost, refSetSize, b1, b2);
        this.runLocalSearch = runLocalSearch;
    }

    @Override
    protected QbpSolution search(int[][] matrix, int[] solutionVector) {

        QbpFirstImprovementAlgorithm firstImprovementAlgorithm = new QbpFirstImprovementAlgorithm();

        int diverseSetSize = (refSetSize*refSetSize-refSetSize)/2;

        List<QbpSolution> refSet = refset(getDiverseSet(diverseSetSize, matrix), refSetSize, b1, b2, matrix);

        QbpSolution best = refSet.get(0);

        all: do {

            increaseIteration();

            List<QbpSolution> subSet = getSubSet(refSet);

            for (int x = 0; x < subSet.size() - 1; x++) {

                QbpSolution solution1 = subSet.get(x);
                QbpSolution solution2 = subSet.get(x+1);

                QbpSolution candidateSolution = recombine(solution1, solution2, matrix);

                QbpSolution candidate;
                if (runLocalSearch) {
                    candidate = firstImprovementAlgorithm.solve(matrix, candidateSolution.getVector());
                } else {
                    candidate = candidateSolution;
                }

                if (candidate.getCost() < best.getCost()) {

                    best = candidate;

                    if (best.getCost() == bestSolutionCost) {
                        break all;
                    }
                }
            }

            refSet = refset(getDiverseSet(diverseSetSize, matrix), refSetSize, b1, b2, matrix);

        } while (!mayIStop());

        return best;
    }

    private QbpSolution recombine(QbpSolution solution1, QbpSolution solution2, int[][] matrix) {

        QbpSolution best = solution2;

        if (pathRelinkOption.equals(PathRelinkOption.FORWARD)) {

            for (int x = 0; x < solution1.getVector().length; x++) {

                if (solution1.getVector()[x] != solution2.getVector()[x]) {

                    int[] neightbor = getNeighbor(solution1.getVector(), x);
                    int neighborCost = getNeighborCost(matrix, solution1.getCost(), neightbor, x);

                    if (neighborCost < best.getCost()) {

                        QbpSolution neighborSolution = new QbpSolution(neightbor, neighborCost);
                        best = neighborSolution;

                        break;
                    }
                }
            }

        } else if (pathRelinkOption.equals(PathRelinkOption.BACKWARD)) {

            for (int x = solution1.getVector().length - 1; x >= 0; x--) {

                if (solution1.getVector()[x] != solution2.getVector()[x]) {

                    int[] neightbor = getNeighbor(solution1.getVector(), x);
                    int neighborCost = getNeighborCost(matrix, solution1.getCost(), neightbor, x);

                    if (neighborCost < best.getCost()) {

                        QbpSolution neighborSolution = new QbpSolution(neightbor, neighborCost);
                        best = neighborSolution;

                        break;
                    }
                }
            }
        }

        return best;
    }

    private List<QbpSolution> getSubSet(List<QbpSolution> refSet) {

        List<QbpSolution> subSet = new ArrayList<QbpSolution>();

        for (int x = 0 ; x < refSet.size() - 1; x++) {

            subSet.add(refSet.get(x));

            for (int y = x + 1; y < refSet.size(); y++) {
                subSet.add(refSet.get(y));
            }
        }

        return subSet;
    }

    private List<QbpSolution> refset(List<QbpSolution> diverseSet, int refSetSize, int b1, int b2, int[][] matrix) {

        List<QbpSolution> refSet = new ArrayList<QbpSolution>();

        Collections.sort(diverseSet);

        for (int i=0; i<b1; i++) {
            refSet.add(diverseSet.get(i));
        }

        List<QbpSolution> worst = new ArrayList<QbpSolution>();

        for (int pi=b1; pi<diverseSet.size(); pi++){

            int distance = 0;

            for (int ri=0; ri<b1; ri++){
                distance += getHammingDistance(matrix, diverseSet.get(pi), diverseSet.get(ri));
            }

            QbpSolution s = diverseSet.get(pi);
            s.setDistance(distance);

            worst.add(s);
        }

        Collections.sort(worst, DISTANCE_SORTER);

        for (int i=worst.size() - (refSetSize - b1); i<worst.size(); i++) {
            refSet.add(worst.get(i));
        }

        Collections.sort(refSet);

        return refSet;
    }

    private int getHammingDistance(int[][] matrix, QbpSolution s1, QbpSolution s2){

        int distance = 0;
        for (int i=0; i<matrix.length; i++){
            if (s1.getVector()[i] != s2.getVector()[i]){
                distance++;
            }
        }

        return distance;
    }

    private List<QbpSolution> getDiverseSet(int diverseSetSize, int[][] matrix) {

        List<QbpSolution> diverseSet = new ArrayList<QbpSolution>();

        for (int i=0; i<diverseSetSize; i++) {

            QbpSolution solution = new QbpSolution();
            solution.setVector(getRandomSolution(matrix.length));
            solution.setCost(getCost(matrix, solution.getVector()));

            diverseSet.add(solution);
        }

        return diverseSet;
    }

    private boolean mayIStop() {

        boolean is5Minutes = currentTimeInMinutes() >= 5;

        return is5Minutes;
    }

    private long currentTimeInMinutes() {

        long total = System.nanoTime() - start;
        return TimeUnit.MINUTES.convert(total, TimeUnit.NANOSECONDS);
    }
}
