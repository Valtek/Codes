import junit.framework.Assert;
import org.junit.Test;

import java.io.File;

public class DeterminantTest {
    @Test
    public void testDeterminant() throws Exception{
        double testResult = 208;

        Matrix A = new Matrix(new File("src\\input.txt"));

        double C = A.determinant();

        Assert.assertEquals(testResult, C);
    }
}
