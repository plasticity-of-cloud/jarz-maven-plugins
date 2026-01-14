package ${package};

import org.apache.commons.lang3.StringUtils;

/**
 * Hello JARZ Application
 * Generated from JARZ Maven Archetype
 */
public class App {
    
    public static void main(String[] args) {
        System.out.println("🚀 Hello JARZ!");
        System.out.println("===============");
        
        String message = "  Welcome to " + StringUtils.capitalize("${artifactId}") + "!  ";
        String processed = StringUtils.trim(message);
        
        System.out.println("Application: " + processed);
        System.out.println("📦 Packaged with JARZ format for 27% size reduction");
        System.out.println("🔗 Learn more: https://jarz-streaming.net");
    }
}
