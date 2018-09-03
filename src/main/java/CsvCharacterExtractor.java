/*
 * --------------------------------------------------------------------------------------------------------------------
 * <copyright file="CsvCharacterExtractor.java" company="Supyrb">
 *   Copyright (c) 2018 Supyrb. All rights reserved.
 * </copyright>
 * <author>
 *   Johannes Deml
 *   send@johannesdeml.com
 * </author>
 * --------------------------------------------------------------------------------------------------------------------
 */

import java.util.ArrayList;

public class CsvCharacterExtractor {
    public static void main(String[] args) {
        System.out.println("Running CsvCharacterExtractor");

        ConfigData config = new ConfigData("./in/table.csv", "./out/", "", "", new ArrayList<CombinedOutputData>());
        ConfigReader configReader = new ConfigReader();
        configReader.parseXmlFile("./config.xml", config);
        CsvUniqueCharacterProcessor processor = new CsvUniqueCharacterProcessor();

        processor.runProcess(config);
    }
}
