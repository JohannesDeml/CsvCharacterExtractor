public class ConfigData {
    private String inPath;
    private String outPath;

    public ConfigData(String inPath, String outPath) {
        this.inPath = inPath;
        this.outPath = outPath;
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
}
