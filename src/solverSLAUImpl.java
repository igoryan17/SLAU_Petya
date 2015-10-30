import org.jblas.DoubleMatrix;
import org.jblas.Solve;

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

    public solverSLAUImpl(Builder huilder) {
        N = huilder.getN();
        X = huilder.getX();
        A = huilder.getA();
        f = huilder.getF();
        double max = Math.pow(A.max(), -1);
        //A = A.muli(max);
        //f = f.muli(max);
        System.out.println("norm A is " + A.norm1());
        //ATranspose = A.transpose();
        //A = ATranspose.mmul(A);
        //f = ATranspose.mmul(f);
        buildD();
        //DInverse = Solve.pinv(D);
        //P = DInverse.mmul(A.sub(D)).mmuli(-1);
        //g = DInverse.mmul(f);
        //normP = P.norm1();
        //System.out.println("normP is " + normP);
        huilder.printMatrix(A);
    }

    public void buildD() {
        D = new DoubleMatrix(N, N);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == j) {
                    D.put(i, j, A.get(i, j));
                }
                else {
                    D.put(i, j, 0);
                }
            }
        }
    }

    @Override
    public DoubleMatrix getX() {
        return X;
    }

    @Override
    public void solve() {
        X = Solve.solve(A, f);
    }
}
