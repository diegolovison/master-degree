package acsflowshop;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ACSFlowShopTest {

    @Test
    public void testBelongsGlobalBestTour() {

        double[][] t = {{0.5, 1}, {2, 0.3}};

        ACSFlowShop acsFlowShop = new ACSFlowShop(2, 2);

        //assertFalse(acsFlowShop.belongsGlobalBestTour(t, 0, 0));
        //assertTrue(acsFlowShop.belongsGlobalBestTour(t, 0, 1));

        //assertTrue(acsFlowShop.belongsGlobalBestTour(t, 1, 0));
        //assertFalse(acsFlowShop.belongsGlobalBestTour(t, 1, 1));

    }
}
