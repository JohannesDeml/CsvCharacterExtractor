public class ConfigData {
    private String inPath;
    private String outPath;
    private String includeCharacters;
    private String excludeCharacters;

    public ConfigData(String inPath, String outPath, String includeCharacters, String excludeCharacters) {
        this.inPath = inPath;
        this.outPath = outPath;
        this.includeCharacters = includeCharacters;
        this.excludeCharacters = excludeCharacters;
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
}
