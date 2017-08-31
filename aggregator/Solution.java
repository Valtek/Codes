package com.alexander.aggregator;

import com.alexander.aggregator.exceptions.ArgException;
import com.alexander.aggregator.exceptions.ArgNumException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Solution {
    public static void main(String[] args) throws Exception{
//        args = new String[10];
//        args[0] = "-itype";
//        args[1] = "csv";
//        args[2] = "-if";
//        args[3] = "./1.csv";
//        args[4] = "-agg";
//        args[5] = "user:sum:type";
//        args[6] = "-otype";
//        args[7] = "csv";
//        args[8] = "-of";
//        args[9] = "./result.csv";

        if (args.length % 2 != 0){
            try {
                throw new ArgNumException();
            }catch (ArgNumException ex){ }
        }

        HashMap<String, String> arguments = new HashMap<String, String>();
        for (int i = 0; i < args.length; i += 2) {
            arguments.put(args[i], args[i + 1]);
        }

        ArrayList<String> inputFileContents = new ArrayList<>();
        if (System.in.available() > 0) {
            try (BufferedReader bufReader = new BufferedReader(new InputStreamReader(System.in))) {
                String line = null;
                while ((line = bufReader.readLine()) != null) {
                    inputFileContents.add(line);
                }
            }
        }

        if (!arguments.containsKey("-if") && inputFileContents.size() == 0) {
            throw new ArgException();
        }

        Aggregator aggregator;
        if (!arguments.containsKey("-if")) {
            aggregator = new Aggregator(arguments, inputFileContents);
        } else {
            aggregator = new Aggregator(arguments);
        }

        aggregator.aggregate();
    }
}
