package report;

/**
 * Created by Diego_Lovison on 6/16/2014.
 */
public class GenerateReport {

    public static void main(String... args) {

        if (args.length > 0) {
            new TestPaper().resolveInstance(
                    Integer.valueOf(args[0]),
                    Integer.valueOf(args[1]));
        } else {
            new TestPaper().execute();
        }
    }

}
