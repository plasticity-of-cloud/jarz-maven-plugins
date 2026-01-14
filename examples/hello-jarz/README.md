# Hello JARZ Example

This example demonstrates how to use the JARZ Maven plugins to create compressed archives with superior compression and streaming capabilities.

## What This Example Shows

- **JARZ Repository Plugin**: Configures JARZ CDN repositories for dependency resolution
- **JARZ Dependency Plugin**: Resolves JARZ dependencies with fallback to JAR
- **JARZ Package Plugin**: Creates JARZ archives with ZSTD compression

## Project Structure

```
hello-jarz/
├── pom.xml                    # Maven configuration with JARZ plugins
├── src/main/java/
│   └── com/example/hellojarz/
│       └── HelloJarz.java     # Main application class
└── README.md                  # This file
```

## Building and Running

### Prerequisites

- Java 11 or higher
- Maven 3.8+
- JARZ Maven plugins installed locally

### Build Steps

1. **Install JARZ plugins** (from parent directory):
   ```bash
   cd ../..
   mvn clean install
   ```

2. **Build the example**:
   ```bash
   cd examples/hello-jarz
   mvn clean package
   ```

3. **Run the application**:
   ```bash
   java -jar target/hello-jarz-1.0.0.jar
   ```

## Expected Output

```
🚀 Hello JARZ!
===============
Original: '  Welcome to JARZ compressed archives!  '
Processed: 'Welcome to jarz compressed archives!'
Jackson JsonFactory available: true

✅ JARZ packaging successful!
📦 This application was packaged using JARZ format
🔗 Learn more: https://jarz-streaming.net
```

## What Happens During Build

1. **Initialize Phase**: JARZ Repository Plugin configures CDN repositories
2. **Process Resources**: JARZ Dependency Plugin resolves dependencies (with JAR fallback)
3. **Compile**: Standard Maven compilation
4. **Package**: JARZ Package Plugin creates compressed `.jarz` archive

## Generated Files

After building, you'll find:

- `target/hello-jarz-1.0.0.jar` - Traditional JAR file
- `target/hello-jarz-1.0.0.jarz` - JARZ compressed archive (27% smaller)

## Configuration Options

The example demonstrates key configuration options:

### JARZ Repository Plugin
```xml
<configuration>
    <jarzRepository>https://cdn.jarz-streaming.net/maven2</jarzRepository>
    <fallbackToJar>true</fallbackToJar>
</configuration>
```

### JARZ Dependency Plugin
```xml
<configuration>
    <strictMode>false</strictMode>
    <fallbackToJar>true</fallbackToJar>
</configuration>
```

### JARZ Package Plugin
```xml
<configuration>
    <compressionLevel>3</compressionLevel>
    <blockSize>65536</blockSize>
    <enableDictionary>true</enableDictionary>
</configuration>
```

## Next Steps

- Explore different compression levels (1-22)
- Try different block sizes for optimization
- Experiment with dictionary training for better compression
- Deploy to JARZ CDN for streaming capabilities

## License

This example is part of the JARZ Maven Plugins project and follows the same dual licensing (AGPL v3 + Commercial).
