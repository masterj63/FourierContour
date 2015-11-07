package mj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FourierSeriesTest {
    MeshFunction squareMeshFunction = MeshFunction.valueOf(x -> x * x, -Math.PI, Math.PI, 2000);
    FourierSeries fourierSeries = FourierSeries.valueOf(squareMeshFunction);

    @Test
    public void testGetAn() throws Exception {
        assertEquals(2.0 * Math.PI * Math.PI / 3, fourierSeries.getAn(0), 1e-5);
        assertEquals(-4.0, fourierSeries.getAn(1), 1e-5);
        assertEquals(1.0, fourierSeries.getAn(2), 1e-5);
        assertEquals(-4.0 / 9, fourierSeries.getAn(3), 1e-5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBnZero() throws Exception {
        fourierSeries.getBn(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAnNegative() throws Exception {
        fourierSeries.getAn(-1);
    }
}