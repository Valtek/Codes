package com.alexander.aggregator.aggfunctions;

import com.alexander.aggregator.utility.Pair;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Count extends AggregationFunction {
    public Count() {
        super();
    }

    @Override
    public HashMap<String, ArrayList<String>> doWork() {
        HashMap<String, Integer> temp = new HashMap<>();

        for (int i = startIndex; i < finishIndex; i++) {
            if (!temp.containsKey(data.get(groupByField).get(i))) {
                temp.put(data.get(groupByField).get(i), 1);
            } else {
                temp.put(data.get(groupByField).get(i), temp.get(data.get(groupByField).get(i)) + 1);
            }
        }

        return formResult(temp);
    }

    @Override
    public HashMap<String, ArrayList<String>> finalizeWork(ArrayList<HashMap<String, ArrayList<String>>> data) {
        HashMap<String, Integer> temp = new HashMap<>();

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).get(groupByField).size(); j++) {
                if (!temp.containsKey(data.get(i).get(groupByField).get(j))) {
                    temp.put(data.get(i).get(groupByField).get(j), Integer.parseInt(data.get(i).get("count_" + aggregationField).get(j)));
                } else {
                    temp.put(data.get(i).get(groupByField).get(j), temp.get(data.get(i).get(groupByField).get(j)) + Integer.parseInt(data.get(i).get("count_" + aggregationField).get(j)));
                }
            }
        }

        return formResult(temp);
    }

    /**
     * Forms the actual result from the temporary array
     * @param data current data being calculcated
     * @return the aggregated data
     */
    private HashMap<String, ArrayList<String>> formResult(HashMap<String, Integer> data) {
        HashMap<String, ArrayList<String>> result = new HashMap<>();

        result.put("count_" + aggregationField, new ArrayList<>());
        result.put(groupByField, new ArrayList<>());

        for (Map.Entry<String, Integer> pair : data.entrySet()) {
            result.get("count_" + aggregationField).add("" + pair.getValue());
            result.get(groupByField).add(pair.getKey());
        }

        return result;
    }
}
