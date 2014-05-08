package bqp.algorithm;

import bqp.QbpSolution;

import java.util.*;

import static utils.Utils.copy;
import static utils.Utils.random;

public class QbpGreedyAlfaAlgorithm extends QbpLocalSearch {

    private final int a;

    public QbpGreedyAlfaAlgorithm(int a) {
        this.a = a;
    }

    @Override
    protected QbpSolution search(int[][] matrix, int[] solution) {

        QbpSolution best = new QbpSolution();
        best.setVector(copy(solution));
        best.setCost(getCost(matrix, best.getVector()));

        int sizeAllowed = Math.round(a*matrix.length/100);
        if (sizeAllowed == 0) { sizeAllowed = 1;};

        for (int rep=0; rep<solution.length; rep++) {

            List<QbpSolution> data = new ArrayList<QbpSolution>();

            for (int i=rep; i<solution.length; i++) {

                int[] neighbor = getNeighbor(best.getVector(), i);
                int neighborCost = getNeighborCost(matrix, best.getCost(), neighbor, i);

                QbpSolution neighborSolution = new QbpSolution();
                neighborSolution.setVector(neighbor);
                neighborSolution.setCost(neighborCost);

                data.add(neighborSolution);
            }

            Collections.sort(data);

            while (data.size() > sizeAllowed) {
                data.remove(data.size() - 1);
            }

            int index;
            if (a == 0) {
                index = 0;
            } else {
                int max = Math.min(sizeAllowed - 1, data.size() - 1);
                index = random(0, max);
            }

            QbpSolution current = data.get(index);

            if (current.getCost() < best.getCost()) {
                best = current;
            }
        }

        return best;
    }
}
