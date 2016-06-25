import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.*;
import java.lang.NullPointerException;
import java.lang.String;

public class Interface {
    Matrix m1, m2;
    Interface() {
        JFrame jfrm = new JFrame("Операции над матрицами");
        jfrm. setLayout (new FlowLayout());
        jfrm.setSize(350, 500);
        jfrm.setLocationRelativeTo(null);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFileChooser fileopen = new JFileChooser("C:\\Users\\Alex\\Desktop");


        jfrm.setVisible(true);
        JButton jbtnGetFile1 = new JButton("Загрузить Матрицу 1");
        JButton jbtnGetFile2 = new JButton("Загрузить Матрицу 2");
        JButton jbtnMult = new JButton("Умножить матрицы");
        JButton jbtnPlus = new JButton("Сложить матрицы");
        JButton jbtnDet = new JButton("Определитель матрицы");
        JButton jbtnPow = new JButton("Возведение в степень");
        JButton jbtnInvert = new JButton("Обратная матрица");


        JTextArea textArea1=new JTextArea(10,10);
        JTextArea textArea2=new JTextArea(10,10);
        JTextArea textArea3=new JTextArea(25,25);
        textArea1.setEditable(false);
        textArea2.setEditable(false);
        textArea3.setEditable(false);

        jfrm.add(jbtnGetFile1);
        jfrm.add (jbtnGetFile2);
        jfrm.add (jbtnPlus);
        jfrm.add (jbtnMult);
        jfrm.add (jbtnDet);
        jfrm.add (jbtnPow);
        jfrm.add (jbtnInvert);

        jfrm.add(textArea1);
        jfrm.add(textArea2);
        jfrm.add(textArea3);

        jbtnGetFile1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file1 = fileopen.getSelectedFile();

                    m1 = new Matrix(file1);
                    textArea1.setText("");
                    m1.printMatrix(textArea1);

                }
            }
        });

        jbtnGetFile2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file2 = fileopen.getSelectedFile();

                    m2 = new Matrix(file2);
                    textArea2.setText("");
                    m2.printMatrix(textArea2);

                }
            }
        });

       jbtnMult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            try {
                OperMatr B = new OperMatr();
                textArea3.setText("");
                B.mult(m1, m2).printMatrix(textArea3);
            }
            catch (NullPointerException y){
                 JOptionPane.showMessageDialog(jfrm,
                        "Загрузите обе матрицы!");
            }
            }
        });

        jbtnPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    OperMatr B = new OperMatr();
                    textArea3.setText("");
                    B.plus(m1, m2).printMatrix(textArea3);
                }
                catch (NullPointerException y){
                    JOptionPane.showMessageDialog(jfrm,
                            "Загрузите обе матрицы!");
                }
            }
        });

        jbtnDet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JDialog.setDefaultLookAndFeelDecorated(true);
                    Object[] selectionValues = {"Матрица 1", "Матрица 2"};
                    String initialSelection = "Матрица 1";
                    Object selection = JOptionPane.showInputDialog(null, "Выберите матрицу",
                            "Определитель матрицы", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
                    textArea3.setText("");
                    if (selection == "Матрица 1") {
                        textArea3.setText("");
                        textArea3.append("Определитель равен: " + m1.determinant() + "");
                    } else if (selection == "Матрица 2") {
                        textArea3.setText("");
                        textArea3.append("Определитель равен: " + m2.determinant() + "");
                    }
                }
                catch (NullPointerException y){
                    JOptionPane.showMessageDialog(jfrm,
                            "Загрузите нужную матрицу!");
                }
            }
        });

        jbtnPow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    OperMatr B = new OperMatr();
                    JDialog.setDefaultLookAndFeelDecorated(true);
                    Object[] selectionValues = {"Матрица 1", "Матрица 2"};
                    String initialSelection = "Матрица 1";
                    Object selection = JOptionPane.showInputDialog(null, "Выберите матрицу",
                            "Возведение в степень", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
                    textArea3.setText("");
                    if (selection == "Матрица 1") {

                        String result = JOptionPane.showInputDialog(jfrm, "Введите степень:");
                        int res = Integer.parseInt(result);
                        if(res < 0)
                            JOptionPane.showMessageDialog(jfrm, "Степень должна быть положительной и целочисленной!");
                            else{
                            textArea3.setText("");
                            B.power(m1,res).printMatrix(textArea3);
                                }
                    } else if (selection == "Матрица 2") {
                        String result = JOptionPane.showInputDialog(jfrm, "Введите степень:");
                        int res = Integer.parseInt(result);
                        if(res < 0)
                            JOptionPane.showMessageDialog(jfrm, "Степень должна быть положительной и целочисленной!");
                        else{
                            textArea3.setText("");
                            B.power(m2,res).printMatrix(textArea3);
                        }}
                }
                catch (NullPointerException y){
                    JOptionPane.showMessageDialog(jfrm, "Загрузите нужную матрицу!");
                }
                catch (NumberFormatException exc){
                    JOptionPane.showMessageDialog(jfrm, "Степень должна быть положительной и целочисленной!");
                }
                catch (Myexcpow r) {

                }
            }
        });

        jbtnInvert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    OperMatr B = new OperMatr();
                    JDialog.setDefaultLookAndFeelDecorated(true);
                    Object[] selectionValues = {"Матрица 1", "Матрица 2"};
                    String initialSelection = "Матрица 1";
                    Object selection = JOptionPane.showInputDialog(null, "Выберите матрицу",
                            "Обратная матрица", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
                    textArea3.setText("");
                    if (selection == "Матрица 1") {
                        double res = m1.determinant();
                        if(res == 0)
                            JOptionPane.showMessageDialog(jfrm, "Определитель матрицы равен нулю!");
                        else{
                            textArea3.setText("");
                            m1.inversion().printMatrix(textArea3);
                        }
                    } else if (selection == "Матрица 2") {

                        double res = m2.determinant();
                        if(res == 0)
                            JOptionPane.showMessageDialog(jfrm, "Определитель матрицы равен нулю!");
                        else{
                            textArea3.setText("");
                            m2.inversion().printMatrix(textArea3);
                        }}
                }
                catch (NullPointerException y){
                    JOptionPane.showMessageDialog(jfrm, "Загрузите нужную матрицу!");
                }
                catch (Myexc r) {

                }
            }
        });
    }
}