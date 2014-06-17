package report;

/**
 * Created by Diego_Lovison on 6/16/2014.
 */
public class TestInitialQuality {

    public static void main(String... args) {

        TestPaper testPaper = new TestPaper()
                .iteration(1)
                .ant(1)
                .maxTrial(1);

        testPaper.execute();
    }
}
