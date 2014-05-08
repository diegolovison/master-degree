package exer;

import bqp.*;
import bqp.algorithm.QbpBestImprovementAlgorithm;
import bqp.algorithm.QbpFirstImprovementAlgorithm;
import bqp.algorithm.QbpStochasticAlgorithm;

import java.io.IOException;

public class List1 {

    public static void main(String... args) throws IOException {
        new List1().run();
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

            for (int rep=1; rep<=5; rep++) {

                QbpFirstImprovementAlgorithm firstImprovement = new QbpFirstImprovementAlgorithm();
                QbpSolution candidate = firstImprovement.solve(problem.getInstance(), problem.getInitialSolution());
                report(
                        "PM",
                        "NA",
                        instance,
                        rep,
                        firstImprovement.getTime(),
                        firstImprovement.getIterations(),
                        candidate.getCost()
                );

                QbpBestImprovementAlgorithm bestImprovement = new QbpBestImprovementAlgorithm();
                candidate = bestImprovement.solve(problem.getInstance(), problem.getInitialSolution());
                report(
                        "MM",
                        "NA",
                        instance,
                        rep,
                        bestImprovement.getTime(),
                        bestImprovement.getIterations(),
                        candidate.getCost()
                );
            }


            for (int probability : probabilities) {
               for (int rep=1; rep<=5; rep++) {

                    QbpStochasticAlgorithm stochastic = new QbpStochasticAlgorithm(bestSolution[i], probability);
                    QbpSolution candidate = stochastic.solve(problem.getInstance(), problem.getInitialSolution());
                    report(
                            "BL",
                            String.valueOf(probability),
                            instance,
                            rep,
                            stochastic.getTime(),
                            stochastic.getIterations(),
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
