package mj;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
//        MeshFunction squareMeshFunction = MeshFunction.valueOf(x -> x * x, -Math.PI, Math.PI, 2000);
//        FourierSeries fourierSeries = FourierSeries.valueOf(squareMeshFunction);
//        System.out.printf("%.6f\n", fourierSeries.getAn(0) / 2);
//        System.out.printf("%.6f\n", Math.PI * Math.PI / 3);

        System.setProperty("sun.java2d.opengl", "true");

        MeshFunction fun = MeshFunction.valueOf(x -> Math.abs(x), -Math.PI, Math.PI, 2000);
        FourierSeries series = FourierSeries.valueOf(fun);
        for (int i = 1; i <= 20; i++)
            System.out.printf("%d --> %.6f \n", i, series.getAn(i));

        System.out.println();
        System.out.println(series.toString(12, 4));

        new Gui();
    }

    private static double fourier(double x) {
        double sum = 0.0d;
        for (int i = 1; i < 10000; i++) {
            double t = Math.cos(i * x) / i / i;
            if (i % 2 != 0)
                t *= -1;
            sum += t;
        }
        return 4 * sum + Math.PI * Math.PI / 3.0d;
    }

}

class Gui extends JFrame {
    Gui() {
        super("title");
        add(new GameField());
        setSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIgnoreRepaint(true);
        setResizable(false);
        setVisible(true);

    }
}

class GameField extends JPanel {
    double alpha = 0.0d;
    AffinePainter ap = new AffinePainter(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);//fix
    MeshFunction squareMeshFunction = MeshFunction.valueOf(x -> x * x, 0, 35, 300);
    FourierSeries squareFourierSeries = FourierSeries.valueOf(squareMeshFunction);

    GameField() {
        setFocusable(true);
        setDoubleBuffered(true);
        new Timer(300, e -> repaint()).start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        alpha += 0.1d;
        g.drawString(String.valueOf(alpha), 0, 20);

        double x = 0.0d, y = 0.0d;
        for (int n = 2; n <= 30; n++) {
            double newX = x + squareFourierSeries.getHarmAn(n, alpha);
            double newY = y + squareFourierSeries.getHarmBn(n, alpha);
            ap.drawLine(g, (int) x, (int) y, (int) newX, (int) newY);
            x = newX;
            y = newY;
        }
    }
}

class AffinePainter {
    final int CX, CY;


    public AffinePainter(int CX, int CY) {
        this.CX = CX / 2;
        this.CY = CY / 2;
    }

    void drawLine(Graphics g, int x0, int y0, int x1, int y1) {
        g.drawLine(CX + x0, CY + y0, CX + x1, CY + y1);
    }

    void drawOval(Graphics g, int x, int y, int width, int height) {
        g.drawOval(CX + x, CY + y, width, height);
    }
}