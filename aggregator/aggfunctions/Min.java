package com.alexander.aggregator.aggfunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Min extends AggregationFunction {
    public Min() {
        super();
    }

    @Override
    public HashMap<String, ArrayList<String>> doWork() {
        HashMap<String, Double> temp = new HashMap<>();

        for (int i = startIndex; i < finishIndex; i++) {
            if (!temp.containsKey(data.get(groupByField).get(i))) {
                temp.put(data.get(groupByField).get(i), Double.parseDouble(data.get(aggregationField).get(i)));
            } else {
                double value = Double.parseDouble(data.get(aggregationField).get(i));
                if (value < temp.get(data.get(groupByField).get(i))) {
                    temp.put(data.get(groupByField).get(i), value);
                }
            }
        }

        return formResult(temp);
    }

    @Override
    public HashMap<String, ArrayList<String>> finalizeWork(ArrayList<HashMap<String, ArrayList<String>>> data) {
        HashMap<String, Double> temp = new HashMap<>();

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).get(groupByField).size(); j++) {
                if (!temp.containsKey(data.get(i).get(groupByField).get(j))) {
                    temp.put(data.get(i).get(groupByField).get(j), Double.parseDouble(data.get(i).get("min_" + aggregationField).get(j)));
                } else {
                    double value = Double.parseDouble(data.get(i).get("min_" + aggregationField).get(j));
                    if (value < temp.get(data.get(i).get(groupByField).get(j))) {
                        temp.put(data.get(i).get(groupByField).get(j), value);
                    }
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
    private HashMap<String, ArrayList<String>> formResult(HashMap<String, Double> data) {
        HashMap<String, ArrayList<String>> result = new HashMap<>();

        result.put("min_" + aggregationField, new ArrayList<>());
        result.put(groupByField, new ArrayList<>());

        for (Map.Entry<String, Double> pair : data.entrySet()) {
            result.get("min_" + aggregationField).add("" + pair.getValue());
            result.get(groupByField).add(pair.getKey());
        }

        return result;
    }
}
