package report;

/**
 * Created by Diego_Lovison on 6/23/2014.
 */
public class DifferentParametersTest {

    public static void main(String... args) {

        DifferentParametersTest differentParametersTest =
                new DifferentParametersTest();

        //differentParametersTest.testGreaterThanDefault();
        differentParametersTest.testLessThanDefault();


    }

    public void testGreaterThanDefault() {

        TestPaper testPaper = new TestPaper()
                .a(0.2)
                .B(3)
                .p(0.2)
                .q0(1.0);

        testPaper.resolveInstance(50, 5);
        testPaper.resolveInstance(50, 10);
        testPaper.resolveInstance(50, 20);

    }

    public void testLessThanDefault() {

        TestPaper testPaper = new TestPaper()
                .a(0.05)
                .B(1)
                .p(0.05)
                .q0(0.8)
                .maxTrial(1);

        testPaper.execute();

    }
}
