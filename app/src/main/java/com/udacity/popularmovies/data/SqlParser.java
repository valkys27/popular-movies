package com.udacity.popularmovies.data;

import android.content.Context;
import android.util.Log;

import java.io.*;
import java.util.Arrays;

/**
 * Created by tomas on 28.02.2018.
 */

public class SqlParser {

    private final Context context;

    private BufferedReader bufferedReader;
    private String lines;

    public SqlParser(Context context) {
        this.context = context;
    }

    private void initReader(Integer resourceId) {
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(this.context.getResources().openRawResource(resourceId), "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Charset UTF-8 not supported.", ex);
        }
    }

    public String[] parseSqlFileToStatementArray(Integer resourceId) {
        try {
            initReader(resourceId);
            readLines();
            return getSplitStatements();
        } catch (IOException ex) {
            throw new RuntimeException("Cannot parse given SQL script file.", ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Log.e(SqlParser.class.getName(), "Error when closing BufferedReader.");
            }
        }
    }

    private void readLines() throws IOException {
        StringBuilder sb = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null) {
            readLine(sb, line);
            line = bufferedReader.readLine();
        }
        lines = sb.toString();
    }

    private void readLine(StringBuilder sb, String line) {
        sb.append(line);
        sb.append('\n');
    }

    private String[] getSplitStatements() {
        String[] array = lines.split(";\n", -1);
        return Arrays.copyOf(array, array.length - 1);
    }
}
