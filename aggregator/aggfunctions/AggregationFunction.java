package com.alexander.aggregator.aggfunctions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class AggregationFunction implements Serializable {
    /**
     * The index where the thread will start process the data
     */
    protected int startIndex;
    /**
     * The index where the thread will finish process the data (not included)
     */
    protected int finishIndex;
    /**
     * The data being processed
     */
    protected HashMap<String, ArrayList<String>> data;
    /**
     * The name of the aggregation field
     */
    protected String aggregationField;
    /**
     * The name of the field to group the data by
     */
    protected String groupByField;

    /**
     * Simple constructor
     */
    public AggregationFunction() {

    }

    /**
     *
     * @param aggregationField the name of the aggregation field
     */
    public void setAggregationField(String aggregationField) {
        this.aggregationField = aggregationField;
    }

    /**
     *
     * @param data the data to process
     */
    public void setData(HashMap<String, ArrayList<String>> data) {
        this.data = data;
    }

    /**
     *
     * @param finishIndex the index where the thread will finish process the data (not included)
     */
    public void setFinishIndex(int finishIndex) {
        this.finishIndex = finishIndex;
    }

    /**
     *
     * @param groupByField the name of the field to group the data by
     */
    public void setGroupByField(String groupByField) {
        this.groupByField = groupByField;
    }

    /**
     *
     * @param startIndex the index where the thread will start process the data
     */
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    /**
     *
     * @return current data
     */
    public HashMap<String, ArrayList<String>> getData() {
        return data;
    }

    /**
     *
     * @return current finish index for the thread
     */
    public int getFinishIndex() {
        return finishIndex;
    }

    /**
     * current start index for the thread
     * @return
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     *
     * @return current aggregation field name
     */
    public String getAggregationField() {
        return aggregationField;
    }

    /**
     *
     * @return current group by field name
     */
    public String getGroupByField() {
        return groupByField;
    }

    /**
     * Aggregates the data using the specified {@link #aggregationField} and {@link #groupByField}
     * @return the aggregated data
     */
    public abstract HashMap<String, ArrayList<String>> doWork();

    /**
     * Summarizes the work of all threads finishing it
     * @param data the array of the data from all the threads
     * @return the aggregated data
     */
    public abstract HashMap<String, ArrayList<String>> finalizeWork(ArrayList<HashMap<String, ArrayList<String>>> data);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AggregationFunction function = (AggregationFunction) o;

        return this.startIndex == function.startIndex && this.finishIndex == function.finishIndex &&
                this.data.equals(function.data) && this.aggregationField.equals(function.aggregationField) &&
                this.groupByField.equals(function.groupByField);
    }

    @Override
    public int hashCode() {
        int result = aggregationField != null ? aggregationField.hashCode() : 0;

        result = 31 * result + (groupByField != null ? groupByField.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + startIndex + finishIndex;

        return result;
    }

    @Override
    public String toString() {
        return "Parser{" +
                "startIndex='" + startIndex + '\'' +
                ", finishIndex='" + finishIndex + '\'' +
                ", aggregationField='" + aggregationField + '\'' +
                ", groupByField='" + groupByField + '\'' +
                '}';
    }
}
