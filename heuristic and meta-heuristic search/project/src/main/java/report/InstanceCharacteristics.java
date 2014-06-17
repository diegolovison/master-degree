package report;

import acsflowshop.TaillardInstance;
import util.TaillardParser;

import java.util.List;

public class InstanceCharacteristics {

    public static void main(String... args) {

        TestPaper testPaper = new TestPaper();
        int[] jobs = testPaper.getJobs();
        int[][] machines = testPaper.getMachines();

        for (int i=0; i<jobs.length; i++) {

            for (int j=0; j<machines[i].length; j++) {

                String fileName = testPaper.getFileName(jobs[i], machines[i][j]);

                List<TaillardInstance> instances = TaillardParser.parse(fileName);

                for (TaillardInstance instance : instances) {

                    int[][] times = instance.getInstance();

                    for (int machine=0; machine<times.length; machine++) {

                        System.out.print(i+"-"+j+"-"+fileName + " m" + (machine+1) + " ");

                        double total = 0.0;

                        for (int job=0; job<times[machine].length; job++) {
                            total += times[machine][job];
                        }

                        total = total / times[machine].length;

                        System.out.print(total);

                        System.out.println();
                    }
                }

            }
        }

    }
}
