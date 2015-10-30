import org.jblas.DoubleMatrix;

/**
 * Created by igoryan on 30.10.15.
 */
public class Main {
    public static void main(String[] args) {
        Builder huilder = new BuilderImpl(20);
        huilder.printMatrix(huilder.getA());
        System.out.println("число обусловленностей:" + huilder.getConditionNumber());
        solverSLAU huelver = new solverSLAUImpl(huilder);
        huelver.solve();
        DoubleMatrix X = huelver.getX();
        System.out.println("X");
        for (int i = 0; i < X.rows; i++) {
            System.out.println(X.get(i, 0));
        }
    }
}
