package com.alexander.aggregator.serializers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class DbSerializer extends Serializer {
    public DbSerializer() {
        super();
    }

    @Override
    public void serialize(HashMap<String, ArrayList<String>> data) {

        Properties prop = new Properties();
        InputStream input = null;
        String JDBC_DRIVER;
        String DB_URL;
        String USER;
        String PASS;
        Connection conn = null;
        Statement stmt = null;

        try {
            input = new FileInputStream(filePath);

            prop.load(input);

            JDBC_DRIVER = prop.getProperty("dbdriver");
            DB_URL = prop.getProperty("dburl");
            USER = prop.getProperty("dbuser");
            PASS = prop.getProperty("dbpass");

            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            ArrayList<String> A = new ArrayList<>();
            for (String key : data.keySet())
                A.add(key);


            stmt = conn.createStatement();
            String crTable = "DROP TABLE IF EXISTS result;" + "CREATE TABLE  result " +
                    "(id INTEGER not NULL AUTO_INCREMENT, " +
                    "" + A.get(0) + " VARCHAR(255), " +
                    " " + A.get(1) + " VARCHAR(255), " +
                    " PRIMARY KEY (id))";
            stmt.executeUpdate(crTable);

            for (int i = 0; i < data.get(A.get(0)).size(); i++) {
                stmt = conn.createStatement();
                String sql = "INSERT INTO result " + "VALUES (DEFAULT," + data.get(A.get(0)).get(i) + "," + data.get(A.get(1)).get(i) + ")";
                stmt.executeUpdate(sql);
            }

            stmt.close();
            conn.close();
        } catch (SQLException se) {

            se.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
                try {
                    if (stmt != null) stmt.close();
                } catch (SQLException se2) {
                }
                try {
                    if (conn != null) conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }
}