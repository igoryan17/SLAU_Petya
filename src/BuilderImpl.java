import org.jblas.DoubleMatrix;
import org.jblas.Solve;

/**
 * Created by igoryan on 30.10.15.
 */
public class BuilderImpl implements Builder {
    private DoubleMatrix A;
    private DoubleMatrix X;
    private DoubleMatrix f;
    private final int N;
    private final double h;

    public BuilderImpl(int n) {
        N = n + 1;
        h = Math.pow(n, -1);
        buildMatrix();
    }

    @Override
    public void buildMatrix() {
        A = new DoubleMatrix(N, N);
        X = new DoubleMatrix(N, 1);
        f = new DoubleMatrix(N, 1);
        for (int i = 0; i < N; i++) {
            int res = (0 <= i && i <= 1 || (N - 2) <= i && i <= (N - 1)) ? 0 : 1;
            f.put(i, 0, res);
            X.put(i, 0, 0);
            for (int j = 0; j < N; j++) {
                initialization(i, j);
            }
        }
        /*
        boolean res = false;
        for (int i = 0; i < A.rows; i++) {
            for (int j = 0; j < A.columns; j++) {
                if (i!= j) {
                    boolean flag = false;
                    double temp = A.get(i, j);
                    for (int k = 0; k < A.rows; k++) {
                        if (A.get(k, k) > temp) {
                            flag = true;
                            break;
                        }
                    }
                    res = res && flag;
                }
            }
        }
        */
    }

    @Override
    public void printMatrix(DoubleMatrix A) {
        for (int i = 0; i < A.rows; i++) {
            for (int j = 0; j < A.columns; j++) {
                System.out.print(A.get(i, j) + " ");
            }
            System.out.println();
        }
    }

    private void initialization(int i, int j) {
        if (i == 0) {
            writeFirstRow(j);
            return;
        }
        if (i == (N - 1)) {
            writeLastRow(j);
            return;
        }
        if (i == 1) {
            writeSecondRow(j);
            return;
        }
        if (i == (N - 2)) {
            writePenaltRow(j);
            return;
        }
        writeMiddleRow(i, j);
    }

    private void writeFirstRow(int j) {
        int put = (j == 0) ? 1 : 0;
        A.put(0, j, put);
    }

    private void writeLastRow(int j) {
        int put = (j == (N - 1)) ? 1 : 0;
        A.put(N - 1, j, put);
    }

    private void writeSecondRow(int j) {
        switch (j) {
            case 0:
                A.put(1, j, -3);
                break;
            case 1:
                A.put(1, j, 4);
                break;
            case 2:
                A.put(1, j, -1);
                break;
            default:
                A.put(1, j, 0);
                break;
        }
    }

    private void writePenaltRow(int j) {
        if (j == (N - 1)) {
            A.put(N - 2, j, 3);
            return;
        }
        if (j == (N - 2)) {
            A.put(N - 2, j , -4);
            return;
        }
        if (j == (N - 3)) {
            A.put(N - 2, j, 1);
            return;
        }
        A.put(N - 2, j, 0);
    }

    private void writeMiddleRow(int i, int j) {
        double expression = Math.pow(h, -4);
        if (i == j) {
            A.put(i, j, 6 * expression + 1);
            return;
        }
        if (j == (i - 2)) {
            A.put(i, j, expression);
            return;
        }
        if (j == (i - 1)) {
            A.put(i, j, (-4) * expression);
            return;
        }
        if (j == (i + 1)) {
            A.put(i, j, (-4) * expression);
            return;
        }
        if (j == (i + 2)) {
            A.put(i, j, expression);
            return;
        }
        A.put(i, j, 0);
    }

    @Override
    public DoubleMatrix getA() {
        return A;
    }

    @Override
    public DoubleMatrix getX() {
        return X;
    }

    @Override
    public int getN() {
        return N;
    }

    @Override
    public double getConditionNumber() {
        return Solve.pinv(A).norm1() * (A).norm1();
    }

    @Override
    public DoubleMatrix getF() {
        return f;
    }
}
