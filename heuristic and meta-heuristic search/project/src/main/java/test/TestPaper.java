package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestPaper {

    public static void main(String... args) {

        new TestPaper().execute();
    }

    public void execute() {

        int[] jobs = {20, 50, 100, 200, 500};
        int[][] machines = {{5, 10, 20}, {5, 10, 20}, {5, 10, 20}, {10, 20}, {20}};

        for (int i=0; i<jobs.length; i++) {

            for (int j=0; j<machines[i].length; j++) {

                resolveInstance(jobs[i], machines[i][j]);
            }
        }
    }

    private void resolveInstance(int job, int machine) {

        String fileName = getFileName(job, machine);

        List<TaillardInstance> instances = TaillardParser.parse(fileName);

        Map<Integer, List<ACSFlowShopResult>> results = new HashMap<Integer, List<ACSFlowShopResult>>();

        for (int count=1; count<=5; count++) {

            for (TaillardInstance instance : instances) {

                long initTime = System.currentTimeMillis();

                ACSFlowShop acsFlowShop = new ACSFlowShop(instance, 5000, 20, 0.1, 2, 0.1, 0.9);
                int cost = acsFlowShop.solve();

                long duration = System.currentTimeMillis() - initTime;

                addResult(count, results, new ACSFlowShopResult(cost, instance.getLowerBound(), duration));
            }
        }

        List<ACSFlowShopResult> bestTrial = getBestTrial(results);

        printBest(bestTrial, fileName);
    }

    private void printBest(List<ACSFlowShopResult> bestTrial, String fileName) {

        long totalTime = 0;
        int totalQuality = 0;

        for (ACSFlowShopResult result : bestTrial) {

            totalTime += result.getTime();
            totalQuality += result.getQuality();
        }

        double averageTime = totalTime / bestTrial.size();
        double averageQuality = totalQuality / bestTrial.size();

        System.out.println(String.format("%s %.2f %.2f", fileName.split("\\.")[0], averageQuality, averageTime));
    }

    private void addResult(int count, Map<Integer, List<ACSFlowShopResult>> results, ACSFlowShopResult result) {

        if (!results.containsKey(count)) {
            results.put(count, new ArrayList<ACSFlowShopResult>());
        }

        results.get(count).add(result);
    }

    private List<ACSFlowShopResult> getBestTrial(Map<Integer, List<ACSFlowShopResult>> results) {

        double bestAverage = Double.MAX_VALUE;
        int best = 0;

        for (int count : results.keySet()) {

            int total = 0;
            List<ACSFlowShopResult> trial = results.get(count);

            for (ACSFlowShopResult result : trial) {
                total += result.getQuality();
            }

            double average = total / trial.size();

            if (average < bestAverage) {
                bestAverage = average;
                best = count;
            }
        }

        return results.get(best);
    }

    private String getFileName(int job, int machine) {
        return "tai" + job + "_" + machine + ".txt";
    }
}
