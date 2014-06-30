package exer;

import bqp.QbpProblem;
import bqp.QbpSolution;
import bqp.algorithm.QbpTabuSearchAlgorithm;
import utils.Utils;

import java.io.IOException;

public class Exer2 {

    public static void main(String... args) throws IOException {
         new Exer2().run();
    }

    public static void run() throws IOException {

        header();

        int[] instances = new int[]{50, 100, 250, 500};
        int[] bestSolution = new int[]{-2098, -7970, -45607, -116586};
        int[] probabilities = new int[]{0, 25, 50, 75, 100};
        int[] tabuDurations = new int[]{25, 50, 100, 200};

        for (int i=0; i<instances.length; i++) {

            int instance = instances[i];

            QbpProblem problem = new QbpProblem(instance, 1);

            for (int tabuDuration : tabuDurations) {

                long d = Math.round((1.0 / tabuDuration) * problem.getInstance().length);

                for (int rep=1; rep<=10; rep++) {

                    QbpTabuSearchAlgorithm tabuSearch = new QbpTabuSearchAlgorithm(d);
                    QbpSolution candidate = tabuSearch.solve(problem.getInstance(), problem.getInitialSolution());
                    report(
                            "TB",
                            String.valueOf(tabuDuration),
                            instance,
                            rep,
                            tabuSearch.getTime(),
                            tabuSearch.getIterations(),
                            candidate.getCost()
                    );
                }
            }

            for (int tabuDuration : tabuDurations) {

                long d = Math.round((1.0 / tabuDuration) * problem.getInstance().length) + Utils.random(1, 10);

                for (int rep=1; rep<=10; rep++) {

                    QbpTabuSearchAlgorithm tabuSearch = new QbpTabuSearchAlgorithm(d);
                    QbpSolution candidate = tabuSearch.solve(problem.getInstance(), problem.getInitialSolution());
                    report(
                            "TR",
                            String.valueOf(tabuDuration),
                            instance,
                            rep,
                            tabuSearch.getTime(),
                            tabuSearch.getIterations(),
                            candidate.getCost()
                    );
                }
            }
        }
    }

    public static void header() {

        System.out.println(String.format("%-3s %-3s %-8s %-3s %-10s %-15s %-15s", "alg", "p", "instance", "rep", "time", "iterations", "value"));
    }

    public static void report(String alg, String probability, int instance, int rep, long time, int iterations, int value) {

        System.out.println(String.format("%-3s %-3s %-8s %-3d %-10d %-15d %-15d", alg, probability, instance, rep, time, iterations, value));
    }

}
