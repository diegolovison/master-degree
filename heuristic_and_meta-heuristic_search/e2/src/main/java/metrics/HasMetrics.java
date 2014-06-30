package metrics;

import java.util.concurrent.TimeUnit;

public abstract class HasMetrics {

    private int iteration = 0;

    protected long start;
    protected long finish;


    public int getIterations() {
        return iteration;
    }

    public void increaseIteration() {
        ++iteration;
    }

    public long getTime() {
        long total = finish - start;
        return TimeUnit.MILLISECONDS.convert(total, TimeUnit.NANOSECONDS);
    }

    public void start() {
        this.start = System.nanoTime();
    }

    public void finish() {
        this.finish = System.nanoTime();
    }
}
