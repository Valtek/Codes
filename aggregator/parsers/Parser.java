package com.alexander.aggregator.parsers;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Parser implements Serializable {
    protected String filePath;
    protected ArrayList<String> fileContents;

    /**
     * Simple constructor
     */
    public Parser() {

    }

    /**
     *
     * @param fileContents the file contents to parse
     */
    public void setFileContents(ArrayList<String> fileContents) {
        this.fileContents = fileContents;
    }

    /**
     *
     * @param filePath the path to the file to parse
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     *
     * @return current file contents
     */
    public ArrayList<String> getFileContents() {
        return fileContents;
    }

    /**
     *
     * @return current file path
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Reads the file and stores its contents into the {@link #fileContents}.
     * The file is being taken from {@link #filePath}
     * @return data from the file. Each key is the column name. The corresponding
     * column is the array
     */
    public HashMap<String, ArrayList<String>> oParse() {
        if (fileContents == null || fileContents.size() == 0) {
            if (fileContents == null) {
                fileContents = new ArrayList<>();
            }

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    fileContents.add(line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return parse();
    }

    /**
     * Parses the file contents taken from {@link #fileContents}
     * @return data from the file. Each key is the column name. The corresponding
     * column is the array
     */
    protected abstract HashMap<String, ArrayList<String>> parse();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Parser parser = (Parser) o;

        return this.fileContents.equals(parser.fileContents) && this.filePath.equals(parser.filePath);
    }

    @Override
    public int hashCode() {
        int result = fileContents != null ? fileContents.hashCode() : 0;

        result = 31 * result + (filePath != null ? filePath.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Parser{" +
                "filePath='" + filePath + '\'' +
                '}';
    }
}
