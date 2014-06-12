package acsflowshop;

/**
 * Created by Diego_Lovison on 6/9/2014.
 */
public class ACSFlowShopHelper {

    public static double[][] createPhoromone(int numberOfJobs, double t0) {

        double[][] t = new double[numberOfJobs][numberOfJobs];

        for (int i=0; i<numberOfJobs; i++) {
            for (int j=0; j<numberOfJobs; j++) {
                t[i][j] = t0;
            }
        }

        return t;
    }

    public static double[] createPath(int[][] instance) {

        int numberOfJobs = instance[0].length;

        double[] path = new double[numberOfJobs];

        double min = Double.MAX_VALUE;

        for (int u=0; u<numberOfJobs; u++) {

            double value = length(instance, u);
            path[u] = value;

            if (value < min) {
                min = value;
            }
        }

        for (int u=0; u<numberOfJobs; u++) {
            path[u] = path[u] - min + 1;
        }

        return path;
    }

    public static double length(int[][] instance, int u) {

        double length = 0;
        int numberOfMachines = instance.length;

        int middle = (numberOfMachines / 2) + 1;
        int var = 1;
        boolean useTheSameValue = numberOfMachines % 2 == 0;

        for (int m=numberOfMachines; m>0; m--) {

            if (m >= middle) { // decrease

                var -= 2;

                length = length + (numberOfMachines - var) * instance[m-1][u];

            } else { // increase

                if (!useTheSameValue) { // when is a par we need to keep the same value
                    var += 2;
                } else {
                    useTheSameValue = false;
                }

                length = length - (numberOfMachines - var) * instance[m-1][u];
            }
        }

        return length;
    }
}
