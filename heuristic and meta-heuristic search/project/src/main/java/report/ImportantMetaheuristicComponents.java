package report;

/**
 * Created by Diego_Lovison on 6/17/2014.
 */
public class ImportantMetaheuristicComponents {

    public static void main(String... args) {

        ImportantMetaheuristicComponents importantMetaheuristicComponents =
                new ImportantMetaheuristicComponents();

        //importantMetaheuristicComponents.testDisablePheromoneTrail();
        //importantMetaheuristicComponents.testGreedyInitialSolution();
        //importantMetaheuristicComponents.testDisableLocalUpdatePheromone();
        importantMetaheuristicComponents.testDisableGlobalUpdatePheromone();

    }

    public void testDisablePheromoneTrail() {

        TestPaper testPaper = new TestPaper()
                .enablePheromoneTrail(false);

        testPaper.resolveInstance(50, 5);
        testPaper.resolveInstance(50, 10);
        testPaper.resolveInstance(50, 20);
    }

    public void testGreedyInitialSolution() {

        TestPaper testPaper = new TestPaper()
                .iteration(1)
                .ant(1)
                .maxTrial(1)
                .q0(1.0);

        testPaper.execute();
    }

    public void testDisableLocalUpdatePheromone() {

        TestPaper testPaper = new TestPaper()
                .enableLocalUpdatePheromone(false);

        testPaper.resolveInstance(50, 5);
        testPaper.resolveInstance(50, 10);
        testPaper.resolveInstance(50, 20);
    }

    public void testDisableGlobalUpdatePheromone() {

        TestPaper testPaper = new TestPaper()
                .enableGlobalUpdatePheromone(false);

        testPaper.resolveInstance(50, 5);
        testPaper.resolveInstance(50, 10);
        testPaper.resolveInstance(50, 20);
    }
}
