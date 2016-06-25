import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
import java.lang.String;
import java.io.FileOutputStream;
import java.io.IOException;


class Myexc extends Exception {
    public Myexc() {
    }
}

class Myexcpow extends Exception {
    public Myexcpow() {
    }
}

class Matrix {
    int size;
    double A[][];

    Matrix(File f) {
        try (Scanner sc = new Scanner(new FileReader(f))){
        size = sc.nextInt();
        A = new double[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                A[i][j] = sc.nextInt();
            }

        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        }
    }

    Matrix(int s, double B[][]) {
        size = s;
        A = B;
    }

    int getSize() {
        return size;
    }

    double getMatr() {
        return A[size][size];
    }

    double getElement(int i, int j) {
        return A[i][j];
    }

    void printMatrix(JTextArea area) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                area.append(A[i][j] + "  ");
            area.append("\n");

        }
    }

    void writeToFile() {
        try (FileOutputStream fos = new FileOutputStream("C:\\Users\\Alexander\\Desktop\\output.txt")) {

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++)
                    fos.write((A[i][j] + " ").getBytes());
                fos.write("\r\n".getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    double determinant() {
        double D = 0;
        switch (size) {
            case 1:
                D = A[0][0];
                break;
            case 2:
                D = A[0][0] * A[1][1] - A[0][1] * A[1][0];
                break;
            case 3:
                int a, b;
                for (int j = 0; j < size; j++) {
                    a = 1;
                    b = 1;
                    for (int i = 0; i < size; i++) {
                        a *= A[i][(j + i) % size];
                        b *= A[size - i - 1][(j + i) % size];
                    }
                    D += a - b;
                }
                break;
            default:
                int sign = 1;
                for (int k = 0; k < size; k++) {
                    double newmx[][] = new double[size - 1][size - 1];
                    for (int i = 1; i < size; i++) {
                        for (int j = 0; j < k; j++)
                            newmx[i - 1][j] = A[i][j];
                        for (int j = k + 1; j < size; j++)
                            newmx[i - 1][j - 1] = A[i][j];
                    }
                    Matrix m = new Matrix(size - 1, newmx);
                    D += sign * A[0][k] * m.determinant();
                    sign *= (-1);
                }


        }
        return D;
    }

    Matrix eMatr(){
        double a[][] = new double[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                a[i][j] = A[i][j];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                a[i][j] = 1;
        Matrix res = new Matrix(size,a);
        return res;

    }

    Matrix inversion() throws Myexc {
        double temp;

        if (determinant() != 0) {
            double a[][] = new double[size][size];
            double[][] E = new double[size][size];
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                    a[i][j] = A[i][j];
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++) {
                    E[i][j] = 0;

                    if (i == j)
                        E[i][j] = 1;
                }

            for (int k = 0; k < size; k++) {
                temp = a[k][k];

                for (int j = 0; j < size; j++) {
                    a[k][j] /= temp;
                    E[k][j] /= temp;
                }

                for (int i = k + 1; i < size; i++) {
                    temp = a[i][k];

                    for (int j = 0; j < size; j++) {
                        a[i][j] -= a[k][j] * temp;
                        E[i][j] -= E[k][j] * temp;
                    }
                }
            }

            for (int k = size - 1; k > 0; k--) {
                for (int i = k - 1; i >= 0; i--) {
                    temp = a[i][k];

                    for (int j = 0; j < size; j++) {
                        a[i][j] -= a[k][j] * temp;
                        E[i][j] -= E[k][j] * temp;
                    }
                }
            }

            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                    a[i][j] = new BigDecimal(E[i][j]).setScale(2, RoundingMode.HALF_UP).doubleValue();

            Matrix res = new Matrix(size, a);
            return res;
        } else throw new Myexc();


    }



}

class OperMatr {
    int size;
    double C[][];

    Matrix plus(Matrix mx1, Matrix mx2) {
        size = mx1.getSize();
        C = new double[size][size];
        for (int i = 0; i < mx1.getSize(); i++)
            for (int j = 0; j < mx1.getSize(); j++)
                C[i][j] = mx1.getElement(i, j) + mx2.getElement(i, j);
        Matrix res = new Matrix(size, C);
        return res;
    }

    Matrix mult(Matrix mx1, Matrix mx2) {
        size = mx1.getSize();
        C = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    C[i][j] += mx1.getElement(i, k) * mx2.getElement(k, j);
                }
            }
        }
        Matrix res = new Matrix(size, C);
        return res;
    }

     Matrix power(Matrix matr, int n) throws Myexcpow {


        if(n>1)
            return mult(matr, power(matr, n-1));
        else if(n == 1)
            return matr;
        else if(n == 0)
            return matr.eMatr();
        else throw new Myexcpow();



    }
}

public class Main {

    public static void main(String[] args) {


            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new Interface();
                }
            });
    }
}

