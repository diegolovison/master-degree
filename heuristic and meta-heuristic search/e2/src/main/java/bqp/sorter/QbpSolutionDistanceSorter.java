package bqp.sorter;

import bqp.QbpSolution;

import java.util.Comparator;

public class QbpSolutionDistanceSorter implements Comparator<QbpSolution> {

    @Override
    public int compare(QbpSolution o1, QbpSolution o2) {
        return o1.getDistance().compareTo(o2.getDistance());
    }
}