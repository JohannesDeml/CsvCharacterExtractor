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
import com.vdurmont.emoji.EmojiParser;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class CsvUniqueCharacterProcessor {

    private CsvParserSettings parserSettings;
    private ColumnProcessor rowProcessor;
    private CsvParser parser;
    private Map<String, HashSet<Character>> uniqueLanguageCharacters;
    private ConfigData config;

    public CsvUniqueCharacterProcessor() {
        parserSettings = defineParserSettings();
        rowProcessor = defineProcessorSettings();
        parser = new CsvParser(parserSettings);
        uniqueLanguageCharacters = new HashMap<String, HashSet<Character>>();
    }

    public void setConfig(ConfigData config) {
        this.config = config;
    }

    public void runProcess() {
        String pathToInputCsv = config.getInPath();
        // Check for input file
        if(!fileExists(pathToInputCsv)) {
            System.out.println("Missing input file! The input path set in the config is " + pathToInputCsv);
            return;
        }

        parseInputFile(pathToInputCsv);
        Map<String, List<String>> columnValues = rowProcessor.getColumnValuesAsMapOfNames();
        uniqueLanguageCharacters.clear();

        // process all languages and extract their unique characters
        for (Map.Entry<String, List<String>> columnEntry : columnValues.entrySet()) {
            String columnName = columnEntry.getKey();
            String fileName = generateValidFileName(columnName);
            if(ignoreColumn(fileName)) {
                continue;
            }
            List<String> entries = columnEntry.getValue();
            System.out.println("Processing column " + fileName);
            HashSet<Character> uniqueCharacters = getUniqueCharacters(entries);
            addIncludeCharacters(uniqueCharacters);
            removeExcludeCharacters(uniqueCharacters);
            String uniqueCharacterString = getStringRepresentation(uniqueCharacters);
            System.out.println(fileName + " has " + uniqueCharacterString.length() + " unique characters");
            writeToFile(config.getOutPath()+fileName+".txt", uniqueCharacterString);
            uniqueLanguageCharacters.put(fileName, uniqueCharacters);
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

    private String generateValidFileName(String name) {
        return name.replaceAll("[\\\\/:*?\"<>|]", "_");
    }

    private boolean ignoreColumn(String columnName) {
        return columnName.toLowerCase().equals("id") || columnName.toLowerCase().equals("description");
    }

    private void parseInputFile(String pathToInputCsv) {
        parser.parse(getReader(pathToInputCsv));
    }

    private CsvParserSettings defineParserSettings() {
        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.getFormat().setLineSeparator("\n");
        parserSettings.setHeaderExtractionEnabled(true);
        return parserSettings;
    }

    private ColumnProcessor defineProcessorSettings() {
        ColumnProcessor rowProcessor = new ColumnProcessor();
        parserSettings.setProcessor(rowProcessor);
        return rowProcessor;
    }

    private boolean fileExists(String path) {
        File file = new File(path);
        if(!file.exists()) {
            return false;
        }
        return true;
    }

    private void writeToFile(String relativePath, String uniqueCharacterString) {
        PrintWriter out = null;
        try {

            File file = new File(relativePath);
            file.getParentFile().mkdirs();
            if(!file.exists()) {
                file.createNewFile();
            }

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
        boolean includeUpperAndLowercaseVersion = config.isIncludeUpperAndLowerCase();
        HashSet<Character> uniqueCharacters = new HashSet<Character>();
        for (String entry : entries) {
            if(entry == null) {
                continue;
            }
            // Remove emojis
            entry = EmojiParser.removeAllEmojis(entry);
            // Remove newlines
            entry = entry.replaceAll("\n\r", "");
            entry = entry.replaceAll("\n", "");
            char[] characters = entry.toCharArray();
            for (int i = 0; i<characters.length; i++) {
                char c = characters[i];
                if(includeUpperAndLowercaseVersion) {
                    char cUpper = Character.toUpperCase(c);
                    char cLower = Character.toLowerCase(c);
                    uniqueCharacters.add(cUpper);
                    uniqueCharacters.add(cLower);
                } else {
                    uniqueCharacters.add(c);
                }

            }
        }

        return uniqueCharacters;
    }

    private void removeExcludeCharacters(HashSet<Character> uniqueCharacters) {
        String excludeCharacters= config.getExcludeCharacters();

        for (int i = 0; i< excludeCharacters.length(); i++) {
            char c = excludeCharacters.charAt(i);
            uniqueCharacters.remove(c);
        }
    }

    private void addIncludeCharacters(HashSet<Character> uniqueCharacters) {
        String includeCharacters = config.getIncludeCharacters();

        for (int i = 0; i< includeCharacters.length(); i++) {
            char c = includeCharacters.charAt(i);
            uniqueCharacters.add(c);
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
