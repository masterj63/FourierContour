package mj;

import java.util.function.DoubleUnaryOperator;

class MeshFunction {
    private final double[] args;
    private final double[] vals;
    private final double square;

    private MeshFunction(double[] args, double[] vals) {
        assert args.length == vals.length;
        assert args.length >= 2;
        assert MeshFunction.sorted(args);

        this.args = args;
        this.vals = vals;

        double square = 0.0d;
        for (int i = 1; i < args.length; i++)
            square += (args[i] - args[i - 1]) * (vals[i] + vals[i - 1]) / 2;
        this.square = square;
    }

    static MeshFunction valueOf(DoubleUnaryOperator function, double from, double to, int meshSize) {
        assert meshSize >= 2;

        double[] args = new double[meshSize];
        for (int i = 0; i < meshSize; i++)
            args[i] = from + (to - from) / (meshSize - 1) * i;

        double[] vals = new double[meshSize];
        for (int i = 0; i < meshSize; i++)
            vals[i] = function.applyAsDouble(args[i]);

        return valueOf(args, vals);
    }

    static MeshFunction valueOf(double[] args, double[] vals) {
        return new MeshFunction(args, vals);
    }

    private static boolean sorted(double[] a) {
        for (int i = 1; i < a.length; i++)
            if (a[i - 1] > a[i])
                return false;
        return true;
    }

    double integrate() {
        return square;
    }

    MeshFunction multiply(DoubleUnaryOperator function) {
        double[] vals = new double[this.vals.length];
        for (int i = 0; i < vals.length; i++)
            vals[i] = this.vals[i] * function.applyAsDouble(args[i]);
        return new MeshFunction(args, vals);
    }
}
