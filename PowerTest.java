import junit.framework.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

public class PowerTest {
    @Test
    public void testPower() throws Exception{
        int testSize = 3;
        double[][] testResult = new double[testSize][testSize];

        testResult[0][0] = 242;
        testResult[0][1] = 281;
        testResult[0][2] = 544;

        testResult[1][0] = 98;
        testResult[1][1] = 83;
        testResult[1][2] = 284;

        testResult[2][0] = 349;
        testResult[2][1] = 266;
        testResult[2][2] = -135;

        Matrix A = new Matrix(new File("src\\input.txt"));
        OperMatr C = new OperMatr();
        C.power(A, 3);

        ArrayList<ArrayList<Double>> testResultArr = new ArrayList<>();
        for (int i = 0; i < testResult.length; i++) {
            testResultArr.add(new ArrayList<>());
            for (int j = 0; j < testResult[i].length; j++) {
                testResultArr.get(i).add(testResult[i][j]);
            }
        }

        ArrayList<ArrayList<Double>> result = new ArrayList<>();
        for (int i = 0; i < C.C.length; i++) {
            result.add(new ArrayList<>());
            for (int j = 0; j < C.C[i].length; j++) {
                result.get(i).add(C.C[i][j]);
            }
        }

        Assert.assertEquals(testSize, C.size);
        Assert.assertEquals(testResultArr, result);
    }
}
