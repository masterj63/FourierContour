package mj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MeshFunctionTest {
    MeshFunction sineMeshFunction = MeshFunction.valueOf(Math::sin, -1, 1, 2000);
    MeshFunction sineSquareMeshFunction = sineMeshFunction.multiply(Math::sin);

    MeshFunction cosineMeshFunction = MeshFunction.valueOf(Math::cos, -1, 1, 2000);
    MeshFunction cosineSquareMeshFunction = cosineMeshFunction.multiply(Math::cos);

    @Test
    public void testIntegrate() throws Exception {
        final double EPS = 1e-5;

        assertEquals(0, sineMeshFunction.integrate(), EPS);
        assertEquals(1 - Math.sin(2) / 2, sineSquareMeshFunction.integrate(), EPS);

        assertEquals(2 * Math.sin(1), cosineMeshFunction.integrate(), EPS);
        assertEquals(1 + Math.sin(1) * Math.cos(1), cosineSquareMeshFunction.integrate(), EPS);

        assertEquals(0, sineMeshFunction.multiply(Math::cos).integrate(), EPS);
    }
}