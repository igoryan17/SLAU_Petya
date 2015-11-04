import org.jblas.DoubleMatrix;

/**
 * Created by igoryan on 30.10.15.
 */
public class Main {
    public static void main(String[] args) {
        Builder huilder = new BuilderImpl(6);
        huilder.printMatrix(huilder.getA());
        System.out.println("число обусловленностей:" + huilder.getConditionNumber());
        solverSLAU huelver = new solverSLAUImpl(huilder);
    }
}
