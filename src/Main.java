import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by igoryan on 30.10.15.
 */
public class Main {
    public static void main(String[] args) {
        Builder huilder = new BuilderImpl(10);
        //System.out.println("число обусловленностей:" + huilder.getConditionNumber());
        SolverSLAU huelver = new Gauss(huilder);
        huelver.solve();
        makePlotData();
    }

    public static void makePlotData() {
        File outPut = new File("time.txt");
        if (!outPut.exists()) {
            try {
                outPut.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(outPut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 7; i < 300; i++) {
            Builder builder = new BuilderImpl(i);
            SolverSLAU solver = new Gauss(builder);
            long start = System.nanoTime();
            solver.solve();
            long finish = System.nanoTime();
            printWriter.println(i + " " + (finish - start) / 1000);
        }
        printWriter.close();
    }
}