package acsflowshop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestPaper {

    public static void main(String... args) {

        if (args.length > 0) {
            new TestPaper().resolveInstance(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
        } else {
            new TestPaper().execute();
        }
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

    public void resolveInstance(int job, int machine) {

        String fileName = getFileName(job, machine);

        List<TaillardInstance> instances = TaillardParser.parse(fileName);

        for (int i=0; i<instances.size(); i++) {

            for (int count=1; count<=5; count++) {

                TaillardInstance instance = instances.get(i);

                long initTime = System.currentTimeMillis();

                double t0 = Math.pow((instance.getNumberOfJobs() * instance.getUpperBound()), -1);

                ACSFlowShop acsFlowShop = new ACSFlowShop(instance.getNumberOfMachines(), instance.getNumberOfJobs(), instance.getInstance())
                        .iteration(5000)
                        .ant(20)
                        .a(0.1)
                        .B(2)
                        .p(0.1)
                        .q0(0.2)
                        .t0(t0)
                        .t(ACSFlowShopHelper.createPhoromone(instance.getNumberOfJobs(), t0))
                        .path(ACSFlowShopHelper.createPath(instance.getInstance()));

                double cost = acsFlowShop.solve();

                double duration = System.currentTimeMillis() - initTime;

                double quality = ((cost - instance.getLowerBound()) / instance.getLowerBound());

                Log.info(String.format("%d %s-%d %.4f %.2f", count, fileName.split("\\.")[0], i+1, quality, duration/1000.0));
            }
        }
    }

    private String getFileName(int job, int machine) {
        return "tai" + job + "_" + machine + ".txt";
    }
}
