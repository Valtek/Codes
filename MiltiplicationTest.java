import junit.framework.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

public class MiltiplicationTest {
    @Test
    public void testMultiplication() {
        int testSize = 3;
        double[][] testResult = new double[testSize][testSize];

        testResult[0][0] = 0;
        testResult[0][1] = 46;
        testResult[0][2] = 23;

        testResult[1][0] = 8;
        testResult[1][1] = 10;
        testResult[1][2] = 13;

        testResult[2][0] = -6;
        testResult[2][1] = 12;
        testResult[2][2] = 26;

        Matrix A = new Matrix(new File("src\\input.txt"));
        Matrix B = new Matrix(new File("src\\input2.txt"));
        OperMatr C = new OperMatr();
        C.mult(A, B);

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
