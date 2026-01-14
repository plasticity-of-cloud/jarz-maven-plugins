package com.example.hellojarz;

import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.core.JsonFactory;

/**
 * Hello JARZ - Example application demonstrating JARZ Maven plugins
 */
public class HelloJarz {
    
    public static void main(String[] args) {
        System.out.println("🚀 Hello JARZ!");
        System.out.println("===============");
        
        // Demonstrate dependency usage
        String message = "  Welcome to JARZ compressed archives!  ";
        String trimmed = StringUtils.trim(message);
        String capitalized = StringUtils.capitalize(trimmed);
        
        System.out.println("Original: '" + message + "'");
        System.out.println("Processed: '" + capitalized + "'");
        
        // Show Jackson is available
        JsonFactory factory = new JsonFactory();
        System.out.println("Jackson JsonFactory available: " + (factory != null));
        
        System.out.println("\n✅ JARZ packaging successful!");
        System.out.println("📦 This application was packaged using JARZ format");
        System.out.println("🔗 Learn more: https://jarz-streaming.net");
    }
}
