package com.alexander.aggregator.serializers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public abstract  class Serializer implements Serializable {
    protected String filePath;

    /**
     * Simple constructor
     */
    public Serializer() {

    }

    /**
     *
     * @param filePath file path to serialize data to
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     *
     * @return current file path
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Serializes the data into the resulting file taken from {@link #filePath}
     * @param data the data to be serialized. Each key is the column name, the
     *             corresponding column is the array
     */
    public abstract void serialize(HashMap<String, ArrayList<String>> data);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Serializer serializer = (Serializer) o;

        return this.filePath.equals(serializer.filePath);
    }

    @Override
    public int hashCode() {
        int result = 31 * (filePath != null ? filePath.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Serializer{" +
                "filePath='" + filePath + '\'' +
                '}';
    }
}
