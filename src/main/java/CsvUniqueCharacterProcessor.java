/*
 * --------------------------------------------------------------------------------------------------------------------
 * <copyright file="CsvUniqueCharacterProcessor.java" company="Supyrb">
 *   Copyright (c) 2018 Supyrb. All rights reserved.
 * </copyright>
 * <author>
 *   Johannes Deml
 *   send@johannesdeml.com
 * </author>
 * --------------------------------------------------------------------------------------------------------------------
 */

import com.univocity.parsers.common.processor.ColumnProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class CsvUniqueCharacterProcessor {

    private final String emojiRegex = "([\\u20a0-\\u32ff\\ud83c\\udc00-\\ud83d\\udeff\\udbb9\\udce5-\\udbb9\\udcee])";

    public void runProcess(ConfigData config) {
        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.getFormat().setLineSeparator("\n");
        parserSettings.setHeaderExtractionEnabled(true);

        // To get the values of all columns, use a column processor
        ColumnProcessor rowProcessor = new ColumnProcessor();
        parserSettings.setProcessor(rowProcessor);

        CsvParser parser = new CsvParser(parserSettings);

        String relativePathToInputCsv = config.getInPath();
        // Check for input file
        if(!checkForInputFile(relativePathToInputCsv)) {
            System.out.println("Missing input file! The input file needs to be at the relative path " + relativePathToInputCsv);
            return;
        }

        //This will kick in our column processor
        parser.parse(getReader(relativePathToInputCsv));

        //Finally, we can get the column values:
        Map<String, List<String>> columnValues = rowProcessor.getColumnValuesAsMapOfNames();

        Map<String, HashSet<Character>> uniqueLanguageCharacters = new HashMap<String, HashSet<Character>>();

        // process all languages and extract their unique characters
        for (Map.Entry<String, List<String>> columnEntry : columnValues.entrySet()) {
            String columnName = columnEntry.getKey();
            if(columnName.toLowerCase().equals("id") || columnName.toLowerCase().equals("description")) {
                continue;
            }
            List<String> entries = columnEntry.getValue();
            System.out.println("Processing column " + columnName);
            HashSet<Character> uniqueCharacters = getUniqueCharacters(entries);
            applyModifications(uniqueCharacters, config);
            String uniqueCharacterString = getStringRepresentation(uniqueCharacters);
            System.out.println(columnName + " has " + uniqueCharacterString.length() + " unique characters");
            writeToFile(config.getOutPath()+columnName+".txt", uniqueCharacterString);
            uniqueLanguageCharacters.put(columnName, uniqueCharacters);
        }

        // process all combination settings
        for (CombinedOutputData combinedOutputData : config.getCombinedOutputTargets()) {
            HashSet<Character> uniqueCharacters = new HashSet<Character>();
            String combinedDataName = combinedOutputData.getName();
            System.out.println("Processing combined data " + combinedDataName);

            for (String column : combinedOutputData.getColumns()) {
                if(!uniqueLanguageCharacters.containsKey(column)) {
                    System.out.println("Missing column " + column + " for combinedColumn " + combinedDataName + " - ignoring column.");
                    continue;
                }
                HashSet<Character> languageCharacters = uniqueLanguageCharacters.get(column);
                uniqueCharacters.addAll(languageCharacters);
            }

            String uniqueCharacterString = getStringRepresentation(uniqueCharacters);
            System.out.println(combinedDataName + " has " + uniqueCharacterString.length() + " unique characters");
            writeToFile(config.getOutPath()+combinedDataName+".txt", uniqueCharacterString);
        }
    }

    private boolean checkForInputFile(String relativePath) {
        File file = new File(relativePath);
        if(!file.exists()) {
            return false;
        }
        file.getParentFile().mkdirs();
        return true;
    }

    private void writeToFile(String relativePath, String uniqueCharacterString) {
        PrintWriter out = null;
        try {

            File file = new File(relativePath);
            if(!file.exists()) {
                file.createNewFile();
            }
            file.getParentFile().mkdirs();
            out = new PrintWriter(file, "UTF-8");
            out.write(uniqueCharacterString);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(out != null) {
                out.close();
            }
        }
    }

    private String getStringRepresentation(HashSet<Character> uniqueCharacters) {
        StringBuilder builder = new StringBuilder(uniqueCharacters.size());
        for (Character character: uniqueCharacters) {
            builder.append(character);
        }
        return builder.toString();
    }

    private HashSet<Character> getUniqueCharacters(List<String> entries) {

        HashSet<Character> uniqueCharacters = new HashSet<Character>();
        for (String entry : entries) {
            if(entry == null) {
                continue;
            }
            // Remove emojis
            entry = entry.replaceAll(emojiRegex, "");
            // Remove newlines
            entry = entry.replaceAll("\n", "");
            char[] characters = entry.toCharArray();
            for (int i = 0; i<characters.length; i++) {
                char c = characters[i];
                uniqueCharacters.add(c);
            }
        }

        return uniqueCharacters;
    }

    private void applyModifications(HashSet<Character> uniqueCharacters, ConfigData config) {
        String includeCharacters = config.getIncludeCharacters();
        String excludeCharacters= config.getExcludeCharacters();

        for (int i = 0; i< includeCharacters.length(); i++) {
            char c = includeCharacters.charAt(i);
            uniqueCharacters.add(c);
        }

        for (int i = 0; i< excludeCharacters.length(); i++) {
            char c = excludeCharacters.charAt(i);
            uniqueCharacters.remove(c);
        }
    }

    public Reader getReader(String relativePath) {
        try {
            InputStream inputStream = new FileInputStream(relativePath);
            return new InputStreamReader(inputStream, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
