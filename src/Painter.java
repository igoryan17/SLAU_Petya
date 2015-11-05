import org.jblas.DoubleMatrix;

import javax.swing.*;
import java.awt.*;

/**
 * Created by igoryan on 05.11.15.
 */
public class Painter extends JFrame {
    private DoubleMatrix X;
    private final int N;
    private int step;

    Painter(SolverSLAU solver) {
        super("Balka - Huyalka");
        setContentPane(new drawPane());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        N = solver.getN();
        setSize(400, 400);
        step = 400 / N;
        if (step == 0) {
            step = 1;
        }
        setVisible(true);
        X = solver.getX();
    }

    class drawPane extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            draw(g);
        }
    }

    private void draw(Graphics g) {
        double max = Math.abs(X.max());
        double scale = 255 / max;
        for (int i = 0; i < X.rows; i++) {
            double x = X.get(i, 0);
            int r = (x > 0) ? (int) Math.round(x * scale) : 0;
            int b = (x < 0) ? (int) Math.round(x * scale) : 0;
            g.setColor(new Color(r, 0 , b));
            g.fillRect(i * step, 0, step, step);
        }
    }
}
