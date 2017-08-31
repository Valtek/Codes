package com.alexander.aggregator.utility;

import java.io.Serializable;

public class Pair<T1, T2> implements Serializable {
    /**
     * First pair value
     */
    private T1 first;
    /**
     * Second pair value
     */
    private T2 second;

    /**
     * Simple constructor
     */
    public Pair() {

    }

    /**
     *
     * @param first first pair value
     */
    public void setFirst(T1 first) {
        this.first = first;
    }

    /**
     *
     * @param second second pair value
     */
    public void setSecond(T2 second) {
        this.second = second;
    }

    /**
     *
     * @return current first value
     */
    public T1 getFirst() {
        return first;
    }

    /**
     *
     * @return current second value
     */
    public T2 getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pair pair = (Pair) o;

        return first.equals(pair.first) && second.equals(pair.second);
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;

        result = 31 * result + (second != null ? second.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Parser{" +
                "first='" + first.toString() + '\'' +
                ", second='" + second.toString() + '\'' +
                '}';
    }
}
