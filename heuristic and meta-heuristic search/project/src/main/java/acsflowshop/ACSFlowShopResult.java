package acsflowshop;

public class ACSFlowShopResult {

    private int cost;
    private int lowerBound;
    private long time;

    public ACSFlowShopResult(int cost, int lowerBound, long time) {
        this.cost = cost;
        this.time = time;
        this.lowerBound = lowerBound;
    }

    public long getTime() {
        return time;
    }

    public int getQuality() {
        return cost - lowerBound;
    }
}
