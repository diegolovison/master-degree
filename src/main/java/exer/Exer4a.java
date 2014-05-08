package exer;

import bqp.QbpProblem;
import bqp.QbpSolution;
import bqp.algorithm.QbpScatterSearchAlgorithm;

import java.io.IOException;

public class Exer4a {

    public static void main(String... args) throws IOException {
        new Exer4a().run();
    }

    public void run() throws IOException {

        header();

        int[] files = new int[]{1, 2, 3, 4, 5, 6, 7};
        int[] instances = new int[]{50, 100, 250, 500};
        int[][] bestValues = new int[][]{
                {-2098, -3702, -4626, -3544, -4012, -3693, -4520},
                {-7970, -11036, -12723, -10368, -9083, -10210, -10125},
                {-45607, -44810, -49037, -41274, -47961, -41014, -46757},
                {-116586, -128223,-130812, -130097, -125487, -121772, -122201}
        };

        for (int j=0; j<files.length; j++) {

            int file = files[j];

            for (int i=0; i<instances.length; i++) {

                int instance = instances[i];

                QbpProblem problem = new QbpProblem(instance, file);

                for (int y=1; y<=9; y++) {

                    int refSetSize = 20;

                    int b1 = 2*y;
                    int b2 = refSetSize - 2*y;

                    QbpScatterSearchAlgorithm scatterSearch = new QbpScatterSearchAlgorithm(bestValues[i][j], refSetSize, b1, b2);
                    QbpSolution candidate = scatterSearch.solve(problem.getInstance(), problem.getInitialSolution());

                    report(
                            "SS",
                            String.valueOf(y),
                            String.valueOf(instance+"-"+file),
                            1,
                            scatterSearch.getTime(),
                            scatterSearch.getIterations(),
                            candidate.getCost(),
                            bestValues[i][j]
                    );
                }
            }

        }
    }

    public static void header() {

        System.out.println(String.format("%-3s %-3s %-10s %-3s %-10s %-15s %-15s %-15s", "alg", "p", "instance", "rep", "time", "iterations", "value", "best value"));
    }

    public static void report(String alg, String probability, String instance, int rep, long time, int iterations, int value, int bestValue) {

        System.out.println(String.format("%-3s %-3s %-10s %-3d %-10d %-15d %-15d %-15d", alg, probability, instance, rep, time, iterations, value, bestValue));
    }
}
