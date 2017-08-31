package com.alexander.aggregator.aggfunctions;

import com.alexander.aggregator.utility.Pair;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.Map;

public class Avg extends AggregationFunction {
    public Avg() {
        super();
    }

    @Override
    public HashMap<String, ArrayList<String>> doWork() {
        HashMap<String, Pair<Integer, Double>> temp = new HashMap<>();

        for (int i = startIndex; i < finishIndex; i++) {
            if (!temp.containsKey(data.get(groupByField).get(i))) {
                Pair<Integer, Double> pair = new Pair<>();
                pair.setFirst(1);
                pair.setSecond(Double.parseDouble(data.get(aggregationField).get(i)));
                temp.put(data.get(groupByField).get(i), pair);
            } else {
                Pair<Integer, Double> pair = temp.get(data.get(groupByField).get(i));
                pair.setFirst(pair.getFirst() + 1);
                pair.setSecond(pair.getSecond() + Double.parseDouble(data.get(aggregationField).get(i)));
                temp.put(data.get(groupByField).get(i), pair);
            }
        }

        return formResult(temp);
    }

    @Override
    public HashMap<String, ArrayList<String>> finalizeWork(ArrayList<HashMap<String, ArrayList<String>>> data) {
        HashMap<String, Pair<Integer, Double>> temp = new HashMap<>();

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).get(groupByField).size(); j++) {
                if (!temp.containsKey(data.get(i).get(groupByField).get(j))) {
                    Pair<Integer, Double> pair = new Pair<>();
                    pair.setFirst(1);
                    pair.setSecond(Double.parseDouble(data.get(i).get("avg").get(j)));
                    temp.put(data.get(i).get(groupByField).get(j), pair);
                } else {
                    Pair<Integer, Double> pair = temp.get(data.get(i).get(groupByField).get(j));
                    pair.setFirst(pair.getFirst() + 1);
                    pair.setSecond(pair.getSecond() + Double.parseDouble(data.get(i).get("avg").get(j)));
                    temp.put(data.get(i).get(groupByField).get(j), pair);
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
    private HashMap<String, ArrayList<String>> formResult(HashMap<String, Pair<Integer, Double>> data) {
        HashMap<String, ArrayList<String>> result = new HashMap<>();

        result.put("avg", new ArrayList<>());
        result.put(groupByField, new ArrayList<>());

        for (Map.Entry<String, Pair<Integer, Double>> pair : data.entrySet()) {
            result.get("avg").add("" + (pair.getValue().getSecond() / pair.getValue().getFirst()));
            result.get(groupByField).add(pair.getKey());
        }

        return result;
    }
}
