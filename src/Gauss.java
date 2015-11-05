import org.jblas.DoubleMatrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by igoryan on 30.10.15.
 */
public class Gauss implements SolverSLAU {
    private DoubleMatrix X;
    private DoubleMatrix A;
    private DoubleMatrix f;
    private DoubleMatrix AOld;
    private DoubleMatrix fOld;
    public final int N;

    //constructor
    public Gauss(Builder huilder) {
        A = huilder.getA();
        N = huilder.getN();
        f = huilder.getF();
        X = new DoubleMatrix(N, 1);
        AOld = A;
        fOld = f;
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
                double number = (f.get(delete, 0) / deleteElem) * (-A.get(current, delete));
                f.put(current, 0, f.get(current, 0) + number);
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
        X.put(X.rows - 1, 0, f.get(f.rows - 1, 0) / A.get(A.rows - 1, A.rows - 1));
        for (int i = X.rows - 2; i >=0; i--) {
            double x = f.get(i, 0);
            for (int j = i + 1; j < A.columns; j++) {
                x-= A.get(i, j) * X.get(j, 0);
            }
            x/= A.get(i, i);
            X.put(i, 0, x);
        }
        DoubleMatrix r = AOld.mmul(X).sub(fOld);
    //    System.out.println("nevyazka:" + r.normmax());
    }

    @Override
    public int getN() {
        return N;
    }

    private void printX() {
        for (int i = 0; i < X.rows; i++) {
            System.out.println(X.get(i, 0));
        }
    }
}
