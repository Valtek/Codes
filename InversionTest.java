import junit.framework.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

public class InversionTest {
    @Test
    public void testInversion() throws Exception{
        int testSize = 3;
        double[][] testResult = new double[testSize][testSize];

        testResult[0][0] = -0.06;
        testResult[0][1] = 0.23;
        testResult[0][2] = 0.13;

        testResult[1][0] = 0.13;
        testResult[1][1] = -0.22;
        testResult[1][2] = 0.04;

        testResult[2][0] = 0.06;
        testResult[2][1] = 0.08;
        testResult[2][2] = -0.06;

        Matrix A = new Matrix(new File("src\\input.txt"));

        Matrix C = A.inversion();

        ArrayList<ArrayList<Double>> testResultArr = new ArrayList<>();
        for (int i = 0; i < testResult.length; i++) {
            testResultArr.add(new ArrayList<>());
            for (int j = 0; j < testResult[i].length; j++) {
                testResultArr.get(i).add(testResult[i][j]);
            }
        }

        ArrayList<ArrayList<Double>> result = new ArrayList<>();
        for (int i = 0; i < C.A.length; i++) {
            result.add(new ArrayList<>());
            for (int j = 0; j < C.A[i].length; j++) {
                result.get(i).add(C.A[i][j]);
            }
        }

        Assert.assertEquals(testSize, C.size);
        Assert.assertEquals(testResultArr, result);
    }
}
