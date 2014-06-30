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
    private int maxTrial = 5;

    private int[] jobs = {20, 50, 100, 200, 500};
    private int[][] machines = {{5, 10, 20}, {5, 10, 20}, {5, 10, 20}, {10, 20}, {20}};

    private boolean enablePheromoneTrail = true;
    private boolean enableLocalUpdatePheromone = true;
    private boolean enableGlobalUpdatePheromone = true;

    public TestPaper iteration(int iteration) {
        this.iteration = iteration;
        return this;
    }

    public TestPaper ant(int ant) {
        this.ant = ant;
        return this;
    }

    public TestPaper a(double a) {
        this.a = a;
        return this;
    }

    public TestPaper B(int B) {
        this.B = B;
        return this;
    }

    public TestPaper p(double p) {
        this.p = p;
        return this;
    }

    public TestPaper q0(double q0) {
        this.q0 = q0;
        return this;
    }

    public TestPaper maxTrial(int maxTrial) {
        this.maxTrial = maxTrial;
        return this;
    }

    public TestPaper enablePheromoneTrail(boolean enablePheromoneTrail) {
        this.enablePheromoneTrail = enablePheromoneTrail;
        return this;
    }

    public TestPaper enableLocalUpdatePheromone(boolean enableLocalUpdatePheromone) {
        this.enableLocalUpdatePheromone = enableLocalUpdatePheromone;
        return this;
    }

    public TestPaper enableGlobalUpdatePheromone(boolean enableGlobalUpdatePheromone) {
        this.enableGlobalUpdatePheromone = enableGlobalUpdatePheromone;
        return this;
    }

    public void execute() {

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

            for (int count=1; count<=maxTrial; count++) {

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
                        .path(ACSFlowShopHelper.createPath(instance.getInstance()))
                        .enablePheromoneTrail(enablePheromoneTrail)
                        .enableLocalUpdatePheromone(enableLocalUpdatePheromone)
                        .enableGlobalUpdatePheromone(enableGlobalUpdatePheromone);

                double cost = acsFlowShop.solve();

                double duration = System.currentTimeMillis() - initTime;

                Log.info(String.format("%d %s %.2f %d %d %.5f %.2f",
                        count,
                        fileName.split("\\.")[0],
                        cost,
                        instance.getLowerBound(),
                        instance.getUpperBound(),
                        (cost - instance.getUpperBound()) / instance.getUpperBound(),
                        duration / 1000.0));
            }
        }
    }

    public int[][] getMachines() {
        return machines;
    }

    public int[] getJobs() {
        return jobs;
    }

    public String getFileName(int job, int machine) {
        return "tai" + job + "_" + machine + ".txt";
    }
}
