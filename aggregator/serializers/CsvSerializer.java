package com.alexander.aggregator.serializers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CsvSerializer extends Serializer {
    public CsvSerializer() {
        super();
    }

    @Override
    public void serialize(HashMap<String, ArrayList<String>> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            ArrayList<String> A = new ArrayList<>();
            for(String key : data.keySet()) {
                A.add(key);
            }

            bw.write(A.get(0) + "," + A.get(1));
            bw.newLine();

            for(int i = 0; i < data.get(A.get(0)).size(); i++){
                bw.write(data.get(A.get(0)).get(i) + "," + data.get(A.get(1)).get(i));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
