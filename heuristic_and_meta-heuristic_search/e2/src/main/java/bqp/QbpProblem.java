package bqp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static utils.Utils.random;

public class QbpProblem {

    private final int[][] instance;
    private final int[] initialSolution;

    public QbpProblem(int instance, int x) {

        this.instance = read(instance, x);

        this.initialSolution = new int[instance];
        for (int i=0; i<this.initialSolution.length; i++) {
            this.initialSolution[i] = random(0 ,1);
        }
    }

    public int[][] getInstance() {
        return instance;
    }

    public int[] getInitialSolution() {
        return this.initialSolution;
    }

    public int[] getEmptyInitialSolution() {
        return new int[instance.length];
    }

    private static int[][] read(int instance, int x) {

        try {

            String fileName = "bqp"+instance+"-"+x+".sparse";

            InputStream input = QbpProblem.class.getResourceAsStream("/"+fileName);

            BufferedReader bReader = new BufferedReader(new InputStreamReader(input, "UTF-8"));

            String line =  bReader.readLine();

            String[] var = line.split(" ");
            int total = Integer.valueOf(var[0]);

            int[][] matrix = new int[total][total];

            while ((line = bReader.readLine()) != null) {

                String[] split = line.trim().split(" ");

                Integer i = Integer.valueOf(split[0]) - 1;
                Integer j = Integer.valueOf(split[1]) - 1;
                Integer value = Integer.valueOf(split[2]);

                matrix[i][j] = value;
                matrix[j][i] = value;
            }

            bReader.close();

            return matrix;

        } catch (IOException e) {
            throw new IllegalStateException("Impossible read the matrix.", e);
        }


    }
}
