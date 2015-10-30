import org.jblas.DoubleMatrix;

/**
 * Created by igoryan on 30.10.15.
 */
public interface Builder {
    public void buildMatrix();
    public void printMatrix(DoubleMatrix A);
    public DoubleMatrix getA();
    public DoubleMatrix getF();
    public DoubleMatrix getX();
    public int getN();
    public double getConditionNumber();
}
