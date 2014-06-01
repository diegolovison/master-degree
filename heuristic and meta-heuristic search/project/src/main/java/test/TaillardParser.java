package test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaillardParser {

    private static final String INSTANCE_SEPARATOR = "number of jobs";

    public static List<TaillardInstance> parse(String fileName) {

        List<TaillardInstance> instances = new ArrayList<TaillardInstance>();

        int jobs, machines, upper, lower, i, j;
        int[][] matrix;
        String line;
        String[] splitted;

        BufferedReader bufferedReader = null;
        InputStream input = null;

        try {

            input = TaillardParser.class.getResourceAsStream("/"+ fileName);
            bufferedReader = new BufferedReader(new InputStreamReader(input));

            while ((line = bufferedReader.readLine()) != null) {

                if (line.startsWith(INSTANCE_SEPARATOR)) {

                    line = bufferedReader.readLine().trim();

                    splitted = line.split("\\s+");

                    jobs = Integer.parseInt(splitted[0]);
                    machines = Integer.parseInt(splitted[1]);
                    upper = Integer.parseInt(splitted[3]);
                    lower = Integer.parseInt(splitted[4]);

                    // skip "processing times :" lines
                    bufferedReader.readLine();

                    matrix = new int[machines][jobs];

                    for (i = 0; i < machines; ++i) {

                        line = bufferedReader.readLine();

                        splitted = line.split("\\s+");

                        for (j = 0; j < jobs; ++j) {
                            matrix[i][j] = Integer.parseInt(splitted[j + 1]);
                        }
                    }

                    instances.add(new TaillardInstance(
                            jobs,
                            machines,
                            upper,
                            lower,
                            matrix));
                }
            }

        } catch (IOException e) {

            throw new IllegalStateException("Impossible to parse: " + fileName, e);

        } finally {

            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {

                }
            }

            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {

                }
            }
        }

        return instances;
    }
}
