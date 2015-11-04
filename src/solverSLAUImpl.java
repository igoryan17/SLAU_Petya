import org.jblas.DoubleMatrix;
import org.jblas.Solve;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by igoryan on 30.10.15.
 */
public class solverSLAUImpl implements solverSLAU {
    private DoubleMatrix X;
    private DoubleMatrix A;
    private DoubleMatrix ATranspose;
    private DoubleMatrix f;
    private DoubleMatrix D;
    private DoubleMatrix P;
    private DoubleMatrix DInverse;
    private DoubleMatrix g;
    private double normP;
    public final int N;

    //constructor
    public solverSLAUImpl(Builder huilder) {
        A = huilder.getA();
        N = huilder.getN();
        for (int i = 0; i < (A.rows - 1); i++) {
            List<MyHandler> threads = new ArrayList<MyHandler>();
            for (int j = i + 1; j < (A.rows - 1); j++) {
                MyHandler myHandler = new MyHandler(i, j, A);
                myHandler.run();
                threads.add(myHandler);
            }
            for (MyHandler thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i = 0; i < A.rows; i++) {
            for (int j = 0; j < A.columns; j++) {
                System.out.print(A.get(i, j) + " ");
            }
            System.out.println();
        }
    }

    private class MyHandler extends Thread {
        private DoubleMatrix A;
        private int delete;
        private int current;

        MyHandler(int deletePosition, int currentPosition, DoubleMatrix A) {
            delete = deletePosition;
            current = currentPosition;
            this.A = A;
        }

        @Override
        public void run() {
            for (int i = 0; i < A.columns; i++) {
                double deleteElem = A.get(delete, delete);
                double put = (A.get(delete, i) / deleteElem) * (-A.get(current, delete));
                double currentElem = A.get(current, i) + put;
                A.put(current, i, currentElem);
            }
        }
    }

    @Override
    public DoubleMatrix getX() {
        return X;
    }

    @Override
    public void solve() {
        for (int i = A.rows - 1; i >= 0; i--) {
            if (i == A.rows - 1) {
                X.put(i, 0, f.get(i, 0) / A.get(i, i));
            }
            for (int j = i + 1; j < A.rows; j++) {

            }
        }
    }
}
