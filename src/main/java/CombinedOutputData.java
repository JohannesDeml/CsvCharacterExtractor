/*
 * --------------------------------------------------------------------------------------------------------------------
 * <copyright file="CombinedOutputData.java" company="Supyrb">
 *   Copyright (c) 2018 Supyrb. All rights reserved.
 * </copyright>
 * <author>
 *   Johannes Deml
 *   send@johannesdeml.com
 * </author>
 * --------------------------------------------------------------------------------------------------------------------
 */

import java.util.List;

public class CombinedOutputData {
    private String name;
    private List<String> columns;

    public CombinedOutputData(String name, List<String> columns) {
        this.name = name;
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public void addColumn(String columnName) {
        columns.add(columnName);
    }
}
