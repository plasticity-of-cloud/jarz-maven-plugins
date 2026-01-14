# JARZ Maven Plugins

Maven plugins ecosystem for JARZ compressed archive format.

## Overview

JARZ format provides 30% size reduction over traditional JAR files using ZSTD compression with dependency-aware block clustering. This plugin ecosystem enables Maven projects to create and consume JARZ archives seamlessly.

## Plugins

### 1. JARZ Package Plugin
Creates JARZ archives from compiled classes (replaces maven-jar-plugin).

```xml
<plugin>
    <groupId>io.jarz</groupId>
    <artifactId>jarz-package-plugin</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>jarz</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### 2. JARZ Repository Plugin
Configures JARZ CDN repositories for dependency resolution.

```xml
<plugin>
    <groupId>io.jarz</groupId>
    <artifactId>jarz-repository-plugin</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <executions>
        <execution>
            <phase>initialize</phase>
            <goals>
                <goal>configure</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### 3. JARZ Dependency Plugin
Resolves JARZ dependencies from CDN (replaces maven-dependency-plugin).

```xml
<plugin>
    <groupId>io.jarz</groupId>
    <artifactId>jarz-dependency-plugin</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <executions>
        <execution>
            <phase>process-resources</phase>
            <goals>
                <goal>resolve</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## Bill of Materials (BOM)

Use the BOM for coordinated version management:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>io.jarz</groupId>
            <artifactId>jarz-bom</artifactId>
            <version>1.0.0-SNAPSHOT</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

## Quick Start

### Using the Maven Archetype (Recommended)

Generate a new JARZ-enabled project:

```bash
mvn archetype:generate \
    -DarchetypeGroupId=net.jarz.streaming \
    -DarchetypeArtifactId=jarz-archetype \
    -DarchetypeVersion=1.0.0-SNAPSHOT \
    -DgroupId=com.example \
    -DartifactId=my-jarz-app
```

### Manual Configuration

1. **Add JARZ BOM to your project**
2. **Configure JARZ repository plugin** (initialize phase)
3. **Add JARZ dependency plugin** (process-resources phase)  
4. **Add JARZ package plugin** (package phase)

See the [Hello JARZ example](examples/hello-jarz/) for a complete working project.

## Examples

- **[Hello JARZ](examples/hello-jarz/)** - Complete example showing all plugins in action

## Configuration

All plugins support common configuration options:

- `jarz.repository` - JARZ CDN URL (default: https://cdn.jarz-streaming.net/maven2)
- `jarz.fallbackToJar` - Fallback to JAR if JARZ unavailable (default: true)
- `jarz.strictMode` - Fail if JARZ not available (default: false)
- `jarz.compressionLevel` - ZSTD compression level (default: 3)

## Build

```bash
mvn clean install
```

## Publishing

See [PUBLISHING.md](PUBLISHING.md) for detailed instructions on publishing to Maven Central, GitHub Packages, or private repositories.

## License

Dual Licensed under AGPL v3.0 and Commercial License - see [LICENSE](LICENSE) file for details.

**Open Source:** Free under AGPL v3.0 (requires sharing your application source code)  
**Commercial:** Proprietary use available - contact ecosystem@plasticity.cloud

**Project Website**: https://jarz-streaming.net

Copyright 2024-2026 Plasticity.Cloud and CoDriverLabs
