package exer;

import bqp.QbpSolution;
import bqp.algorithm.QbpGreedyAlfaAlgorithm;
import bqp.QbpProblem;

public class Exer3 {

    public static void main(String... args) {
        new Exer3().run();
    }

    public void run() {

        header();

        int[] instances = new int[]{50, 100, 250, 500};
        int[] aValues = new int[]{0, 20, 40, 60, 80, 100};

        for (int i=0; i<instances.length; i++) {

            int instance = instances[i];

            QbpProblem problem = new QbpProblem(instance, 1);

            for (int a : aValues) {

               for (int rep=0; rep<1000; rep++) {

                   QbpGreedyAlfaAlgorithm greedyAlgorithm = new QbpGreedyAlfaAlgorithm(a);
                   QbpSolution candidate = greedyAlgorithm.solve(problem.getInstance(), problem.getEmptyInitialSolution());

                   report(
                           "GR",
                           String.valueOf(a),
                           instance,
                           rep,
                           greedyAlgorithm.getTime(),
                           greedyAlgorithm.getIterations(),
                           candidate.getCost()
                   );

                   if (a == 0) {
                       break;
                   }
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
