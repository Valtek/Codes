package com.alexander.aggregator;

import com.alexander.aggregator.aggfunctions.*;
import com.alexander.aggregator.exceptions.ArgAggException;
import com.alexander.aggregator.exceptions.ArgException;
import com.alexander.aggregator.exceptions.ArgInputTypeException;
import com.alexander.aggregator.exceptions.ArgOutputTypeException;
import com.alexander.aggregator.parsers.*;
import com.alexander.aggregator.serializers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class Aggregator {
    /**
     * Logger for the current class
     */
    private static Logger log = Logger.getLogger(Aggregator.class.getName());

    private class AggregationThread implements Runnable {
        /**
         * Actual thread
         */
        private Thread thread;
        /**
         * The result of the thread's work
         */
        private HashMap<String, ArrayList<String>> result;
        /**
         * Current aggregation function
         */
        private AggregationFunction aggFunction;

        /**
         * Constructor
         * @param function aggregation function
         */
        AggregationThread(AggregationFunction function) {
            aggFunction = function;

            thread = new Thread(this);
            thread.start();

            log.info("Thread started");
        }

        @Override
        public void run() {
            result = aggFunction.doWork();
        }

        /**
         * Wrapper for the Thread join method
         * @throws InterruptedException
         */
        public void join() throws InterruptedException {
            thread.join();

            log.info("Thread finished");
        }

        /**
         *
         * @return the thread's work result
         */
        public HashMap<String, ArrayList<String>> getResult() {
            return result;
        }
    }

    /**
     * Count of the threads to start
     */
    private static int THREADS_COUNT = 4;
    /**
     * Data to process
     */
    private HashMap<String, ArrayList<String>> data;
    /**
     * Data parser
     */
    private Parser parser;
    /**
     * Data serializer
     */
    private Serializer serializer;

    /**
     * Name of the field to group the data by
     */
    private String groupByField;
    /**
     * Name of the aggregation function
     */
    private String aggregationFunctionName;
    /**
     * Name of the aggregation field
     */
    private String aggregationField;

    /**
     * Constructor
     * @param arguments console arguments
     * @throws Exception
     */
    public Aggregator(HashMap<String, String> arguments) throws Exception {
        if (!arguments.containsKey("-itype") && !arguments.containsKey("-if") && !arguments.containsKey("-agg") &&
                !arguments.containsKey("-otype") && !arguments.containsKey("-of")) {
            ArgException exception = new ArgException();
            log.throwing("Aggregator", "Aggregator", exception);
            throw exception;
        }

        String[] a = arguments.get("-agg").split(":");
        if (a.length == 3) {
        this.groupByField = a[0];
        this.aggregationFunctionName = a[1];
        this.aggregationField = a [2];

        //System.out.println(groupByField +" "+ aggregationFunctionName +" "+ aggregationField);
        } else {
            ArgAggException exception = new ArgAggException();
            log.throwing("Aggregator", "Aggregator", exception);
            throw exception;
        }

        switch (arguments.get("-itype"))  {
            case "csv":
                parser = new CsvParser();
                parser.setFilePath(arguments.get("-if"));
                break;
            case "xml":
                parser = new XmlParser();
                parser.setFilePath(arguments.get("-if"));
                break;
            default:
                ArgInputTypeException exception = new ArgInputTypeException();
                log.throwing("Aggregator", "Aggregator", exception);
                throw exception;
        }
        log.info(String.format("Successfully initialized parser with input type %s and input file %s", arguments.get("-itype"), arguments.get("-if")));

        switch (arguments.get("-otype")) {
            case "csv":
                serializer = new CsvSerializer();
                serializer.setFilePath(arguments.get("-of"));
                break;
            case "xml":
                serializer = new XmlSerializer();
                serializer.setFilePath(arguments.get("-of"));
                break;
            case "db":
                serializer = new DbSerializer();
                serializer.setFilePath(arguments.get("-conf"));
                break;
            default:
                ArgOutputTypeException exception = new ArgOutputTypeException();
                log.throwing("Aggregator", "Aggregator", exception);
                throw exception;
        }
        log.info(String.format("Successfully initialized serializer with output type %s", arguments.get("-otype")));
    }

    /**
     * Constructor
     * @param arguments console arguments
     * @param inputFileContents file contents piped into the program
     * @throws Exception
     */
    public Aggregator(HashMap<String, String> arguments, ArrayList<String> inputFileContents) throws Exception {
        if (!arguments.containsKey("-itype") && !arguments.containsKey("-agg") &&
                !arguments.containsKey("-otype") && !arguments.containsKey("-of")) {
            ArgInputTypeException exception = new ArgInputTypeException();
            log.throwing("Aggregator", "Aggregator", exception);
            throw exception;
        }

        String[] a = arguments.get("-agg").split(":");
        if (a.length == 3) {
            this.groupByField = a[0];
            this.aggregationFunctionName = a[1];
            this.aggregationField = a [2];

            //System.out.println(groupByField +" "+ aggregationFunctionName +" "+ aggregationField);
        } else {
            ArgAggException exception = new ArgAggException();
            log.throwing("Aggregator", "Aggregator", exception);
            throw exception;
        }

        switch (arguments.get("-itype"))  {
            case "csv":
                parser = new CsvParser();
                parser.setFileContents(inputFileContents);
                break;
            case "xml":
                parser = new XmlParser();
                parser.setFileContents(inputFileContents);
                break;
            default:
                ArgInputTypeException exception = new ArgInputTypeException();
                log.throwing("Aggregator", "Aggregator", exception);
                throw exception;
        }
        log.info("Successfully initialized parser with input type stdin");

        switch (arguments.get("-otype")) {
            case "csv":
                serializer = new CsvSerializer();
                serializer.setFilePath(arguments.get("-of"));
                break;
            case "xml":
                serializer = new XmlSerializer();
                serializer.setFilePath(arguments.get("-of"));
                break;
            case "db":
                serializer = new DbSerializer();
                serializer.setFilePath(arguments.get("-conf"));
                break;
            default:
                ArgOutputTypeException exception = new ArgOutputTypeException();
                log.throwing("Aggregator", "Aggregator", exception);
                throw exception;
        }
        log.info(String.format("Successfully initialized serializer with output type %s", arguments.get("-otype")));
    }

    /**
     * Aggregates the data using the specified console settings
     * @throws ArgException
     */
    public void aggregate() throws ArgException{
        data = parser.oParse();

        log.info("Entered aggregate function");

        if (!data.containsKey(aggregationField) || !data.containsKey(groupByField)) {
            ArgException exception = new ArgException();
            log.throwing("Aggregator", "Aggregator", exception);
            throw exception;
        }

        int rowsPerThread = data.get(aggregationField).size() / THREADS_COUNT;
        ArrayList<AggregationThread> aggThreads = new ArrayList<>();
        for (int i = 0; i < THREADS_COUNT; i++) {
            int finishIndex = i == (THREADS_COUNT - 1) ? data.get(aggregationField).size() : i * rowsPerThread + rowsPerThread;
            AggregationFunction aggFunction = null;
            switch (aggregationFunctionName) {
                case "avg":
                    aggFunction = new Avg();
                    break;
                case "sum":
                    aggFunction = new Sum();
                    break;
                case "count":
                    aggFunction = new Count();
                    break;
                case "min":
                    aggFunction = new Min();
                    break;
                case "max":
                    aggFunction = new Max();
                    break;
            }

            aggFunction.setStartIndex(i * rowsPerThread);
            aggFunction.setFinishIndex(finishIndex);
            aggFunction.setData(data);
            aggFunction.setAggregationField(aggregationField);
            aggFunction.setGroupByField(groupByField);

            aggThreads.add(new AggregationThread(aggFunction));

            log.info("Added new aggregation thread");
        }

        for (AggregationThread thread : aggThreads) {
            try {
                thread.join();
            } catch (InterruptedException exc) {

            }
        }

        log.info("All threads finished");

        AggregationFunction aggFunction = null;
        switch (aggregationFunctionName) {
            case "avg":
                aggFunction = new Avg();
                break;
            case "sum":
                aggFunction = new Sum();
                break;
            case "count":
                aggFunction = new Count();
                break;
            case "min":
                aggFunction = new Min();
                break;
            case "max":
                aggFunction = new Max();
                break;
        }

        aggFunction.setAggregationField(aggregationField);
        aggFunction.setGroupByField(groupByField);

        log.info("Aggregation finished successfully");

        ArrayList<HashMap<String, ArrayList<String>>> tempData = new ArrayList<>();
        for (AggregationThread thread : aggThreads) {
            tempData.add(thread.getResult());
        }

        HashMap<String, ArrayList<String>> result = aggFunction.finalizeWork(tempData);

        serializer.serialize(result);

        log.info("The data is serialized");
    }
}
