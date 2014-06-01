package test;

/**
 * Created by diego on 31/05/2014.
 */
public class ACSFlowShopResult {

    private int cost;
    private int lowerBound;
    private long time;

    public ACSFlowShopResult(int cost, int lowerBound, long time) {
        this.cost = cost;
        this.time = time;
        this.lowerBound = lowerBound;
    }

    public int getCost() {
        return cost;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public long getTime() {
        return time;
    }

    public int getDiff() {
        return cost - lowerBound;
    }
}
