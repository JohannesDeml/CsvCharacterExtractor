/*
 * --------------------------------------------------------------------------------------------------------------------
 * <copyright file="ConfigData.java" company="Supyrb">
 *   Copyright (c) 2018 Supyrb. All rights reserved.
 * </copyright>
 * <author>
 *   Johannes Deml
 *   send@johannesdeml.com
 * </author>
 * --------------------------------------------------------------------------------------------------------------------
 */

import java.util.List;

public class ConfigData {
    private String inPath;
    private String outPath;
    private String includeCharacters;
    private String excludeCharacters;
    private List<CombinedOutputData> combinedOutputTargets;

    public ConfigData(String inPath, String outPath, String includeCharacters, String excludeCharacters, List<CombinedOutputData> combinedOutputTargets) {
        this.inPath = inPath;
        this.outPath = outPath;
        this.includeCharacters = includeCharacters;
        this.excludeCharacters = excludeCharacters;
        this.combinedOutputTargets = combinedOutputTargets;
    }

    public String getInPath() {
        return inPath;
    }

    public void setInPath(String inPath) {
        this.inPath = inPath;
    }

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public String getIncludeCharacters() {
        return includeCharacters;
    }

    public void setIncludeCharacters(String includeCharacters) {
        this.includeCharacters = includeCharacters;
    }

    public String getExcludeCharacters() {
        return excludeCharacters;
    }

    public void setExcludeCharacters(String excludeCharacters) {
        this.excludeCharacters = excludeCharacters;
    }

    public List<CombinedOutputData> getCombinedOutputTargets() {
        return combinedOutputTargets;
    }

    public void setCombinedOutputTargets(List<CombinedOutputData> combinedOutputTargets) {
        this.combinedOutputTargets = combinedOutputTargets;
    }

    public void addCombinedOutputTarget(CombinedOutputData target) {
        this.combinedOutputTargets.add(target);
    }
}

