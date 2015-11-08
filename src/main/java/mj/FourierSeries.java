package mj;

public class FourierSeries {
    private final MeshFunction meshFunction;

    private FourierSeries(MeshFunction meshFunction) {
        this.meshFunction = meshFunction;
    }

    static FourierSeries valueOf(MeshFunction meshFunction) {
        return new FourierSeries(meshFunction);
    }

    double getAn(int n) {
        if (n < 0)
            throw new IllegalArgumentException("negative n");
        MeshFunction temp = meshFunction.multiply(x -> Math.cos(n * x));
        return temp.integrate() / Math.PI;
    }

    double getBn(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("non-positive n");
        MeshFunction temp = meshFunction.multiply(x -> Math.sin(n * x));
        return temp.integrate() / Math.PI;
    }

    double getHarmAn(int n, double t) {
        return getAn(n) * Math.cos(n * t);
    }

    double getHarmBn(int n, double t) {
        return getBn(n) * Math.sin(n * t);
    }

    String toString(int termsCount, int precision) {
        String format = "%." + precision + "f";
        String zeros = "0.";
        for (int i = 0; i < precision; i++)
            zeros += '0';

        StringBuilder sb = new StringBuilder();

        sb.append(String.format(format, getAn(0)));
        sb.append("/2");

        for (int n = 1; n <= termsCount; n++) {
            double an = getAn(n);
            String anString = String.format(format, an);
            if (!anString.endsWith(zeros)) {
                if (an > 0)
                    sb.append("+");
                sb.append(anString);
                sb.append("*cos(" + n + "*x)");
            }

            double bn = getBn(n);
            String bnString = String.format(format, bn);
            if (!bnString.endsWith(zeros)) {
                if (bn > 0)
                    sb.append("+");
                sb.append(bnString);
                sb.append("*sin(" + n + "*x)");
            }
        }

        return sb.toString();
    }
}
