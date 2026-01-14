package net.jarz.streaming.maven.shared;

/**
 * Configuration for JARZ plugins.
 * 
 * @author Plasticity.Cloud
 * @since 1.0
 */
public class JarzConfiguration {
    
    private String jarzRepository = "https://cdn.jarz.io/maven2";
    private boolean fallbackToJar = true;
    private boolean strictMode = false;
    private int compressionLevel = 3;
    private String dictionaryPath;
    
    public String getJarzRepository() {
        return jarzRepository;
    }
    
    public void setJarzRepository(String jarzRepository) {
        this.jarzRepository = jarzRepository;
    }
    
    public boolean isFallbackToJar() {
        return fallbackToJar;
    }
    
    public void setFallbackToJar(boolean fallbackToJar) {
        this.fallbackToJar = fallbackToJar;
    }
    
    public boolean isStrictMode() {
        return strictMode;
    }
    
    public void setStrictMode(boolean strictMode) {
        this.strictMode = strictMode;
    }
    
    public int getCompressionLevel() {
        return compressionLevel;
    }
    
    public void setCompressionLevel(int compressionLevel) {
        this.compressionLevel = compressionLevel;
    }
    
    public String getDictionaryPath() {
        return dictionaryPath;
    }
    
    public void setDictionaryPath(String dictionaryPath) {
        this.dictionaryPath = dictionaryPath;
    }
}
