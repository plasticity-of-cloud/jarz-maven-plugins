package net.jarz.streaming.maven.shared;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for JarzConfiguration.
 * 
 * @author Plasticity.Cloud
 * @since 1.0
 */
class JarzConfigurationTest {

    @Test
    void testDefaultConfiguration() {
        JarzConfiguration config = new JarzConfiguration();
        
        assertEquals("https://cdn.jarz.io/maven2", config.getJarzRepository());
        assertTrue(config.isFallbackToJar());
        assertFalse(config.isStrictMode());
        assertEquals(3, config.getCompressionLevel());
        assertNull(config.getDictionaryPath());
    }

    @Test
    void testConfigurationSetters() {
        JarzConfiguration config = new JarzConfiguration();
        
        config.setJarzRepository("https://custom.jarz.io/maven2");
        config.setFallbackToJar(false);
        config.setStrictMode(true);
        config.setCompressionLevel(5);
        config.setDictionaryPath("/path/to/dict");
        
        assertEquals("https://custom.jarz.io/maven2", config.getJarzRepository());
        assertFalse(config.isFallbackToJar());
        assertTrue(config.isStrictMode());
        assertEquals(5, config.getCompressionLevel());
        assertEquals("/path/to/dict", config.getDictionaryPath());
    }
}
