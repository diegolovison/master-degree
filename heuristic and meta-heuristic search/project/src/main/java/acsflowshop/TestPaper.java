package acsflowshop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestPaper {

    public static void main(String... args) {

        Integer round = 1;

        if (args.length > 0) {
            round = Integer.valueOf(args[0]);
        }

        new TestPaper().execute(round);
    }

    public void execute(int round) {

        //int[] jobs = {20, 50, 100, 200, 500};
        //int[][] machines = {{5, 10, 20}, {5, 10, 20}, {5, 10, 20}, {10, 20}, {20}};

        int[] jobs = {20};
        int[][] machines = {{5, 10}};

        for (int i=0; i<jobs.length; i++) {

            for (int j=0; j<machines[i].length; j++) {

                resolveInstance(jobs[i], machines[i][j], round);
            }
        }
    }

    private void resolveInstance(int job, int machine, int round) {

        String fileName = getFileName(job, machine);

        List<TaillardInstance> instances = TaillardParser.parse(fileName);

        //for (int i=0; i<instances.size(); i++) {
        for (int i=0; i<1; i++) {

            TaillardInstance instance = instances.get(i);

            long initTime = System.currentTimeMillis();

            ACSFlowShop acsFlowShop = new ACSFlowShop(instance.getNumberOfMachines(), instance.getNumberOfJobs(), instance.getInstance())
                    .iteration(5000)
                    .ant(20)
                    .a(0.1)
                    .B(2)
                    .p(0.1)
                    .q0(0.9)
                    .t0(instance.getT0())
                    .t(ACSFlowShopHelper.createPhoromone(instance.getNumberOfJobs(), instance.getT0()))
                    .path(ACSFlowShopHelper.createPath(instance.getInstance()));

            double cost = acsFlowShop.solve();

            double duration = System.currentTimeMillis() - initTime;

            double quality = ((cost - instance.getLowerBound()) / instance.getLowerBound()) * 100;

            Log.info(String.format("%d %s-%d %.2f %.2f", round, fileName.split("\\.")[0], i+1, quality, duration/1000.0));
        }
    }

    private String getFileName(int job, int machine) {
        return "tai" + job + "_" + machine + ".txt";
    }
}
