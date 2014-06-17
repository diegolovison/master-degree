package report;

import acsflowshop.ACSFlowShop;
import acsflowshop.ACSFlowShopHelper;
import acsflowshop.TaillardInstance;
import util.Log;
import util.TaillardParser;

import java.util.List;

public class TestPaper {

    private int iteration = 5000;
    private int ant = 20;
    private double a = 0.1;
    private int B = 2;
    private double p = 0.1;
    private double q0 = 0.9;

    public TestPaper iteration(int iteration) {
        this.iteration = iteration;
        return this;
    }

    public TestPaper ant(int ant) {
        this.ant = ant;
        return this;
    }

    public TestPaper a(int a) {
        this.a = a;
        return this;
    }

    public TestPaper B(int B) {
        this.B = B;
        return this;
    }

    public TestPaper p(int p) {
        this.p = p;
        return this;
    }

    public TestPaper q0(int q0) {
        this.q0 = q0;
        return this;
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

                ACSFlowShop acsFlowShop =
                        new ACSFlowShop(instance.getNumberOfMachines(), instance.getNumberOfJobs(), instance.getInstance())
                        .iteration(iteration)
                        .ant(ant)
                        .a(a)
                        .B(B)
                        .p(p)
                        .q0(q0)
                        .t0(t0)
                        .t(ACSFlowShopHelper.createPhoromone(instance.getNumberOfJobs(), t0))
                        .path(ACSFlowShopHelper.createPath(instance.getInstance()));

                int cost = acsFlowShop.solve();

                double duration = System.currentTimeMillis() - initTime;

                Log.info(String.format("%d %s-%d %d %d %d %.4f %.2f",
                        count,
                        fileName.split("\\.")[0], i + 1,
                        cost,
                        instance.getLowerBound(),
                        instance.getUpperBound(),
                        (cost - instance.getUpperBound()) / instance.getUpperBound(),
                        duration / 1000.0));
            }
        }
    }

    private String getFileName(int job, int machine) {
        return "tai" + job + "_" + machine + ".txt";
    }
}
