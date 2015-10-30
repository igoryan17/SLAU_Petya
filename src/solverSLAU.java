import org.jblas.DoubleMatrix;

/**
 * Created by igoryan on 30.10.15.
 */
public interface solverSLAU {
    final double eps = Math.pow(10, -6);
    public DoubleMatrix getX();
    public void solve();
}
