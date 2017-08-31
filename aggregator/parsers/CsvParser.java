package com.alexander.aggregator.parsers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CsvParser extends Parser {
    public CsvParser () {
        super();
    }

    @Override
    protected HashMap<String, ArrayList<String>> parse() {
        HashMap<String, ArrayList<String >> data = new HashMap<>();

        String line;
        String split = ",";

        line = fileContents.get(0);
        String[] columns = line.split(split);

        for(int i = 0; i < columns.length;i++) {
            data.put(columns[i], new ArrayList<>());
        }

        for (int i = 1; i < fileContents.size(); i++) {
            String[] row = fileContents.get(i).split(split);
            for (int j = 0; j < columns.length; j++) {
                data.get(columns[j]).add(row[j]);
            }
        }

        return data;
    }
}
