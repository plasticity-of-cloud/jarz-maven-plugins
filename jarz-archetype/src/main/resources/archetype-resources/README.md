# ${artifactId}

JARZ-enabled application generated from the JARZ Maven Archetype.

## Features

- **JARZ Compression**: 27% smaller archives than traditional JAR files
- **Streaming Support**: S3 range-request and CDN HTTP/2 streaming capabilities
- **Zero Dependencies**: Ready-to-run application with JARZ plugins configured

## Building

```bash
mvn clean package
```

This will create both:
- `target/${artifactId}-${version}.jar` - Traditional JAR file
- `target/${artifactId}-${version}.jarz` - JARZ compressed archive

## Running

```bash
java -jar target/${artifactId}-${version}.jar
```

## JARZ Configuration

The project is pre-configured with all JARZ Maven plugins:

1. **JARZ Repository Plugin** - Configures JARZ CDN repositories
2. **JARZ Dependency Plugin** - Resolves JARZ dependencies with JAR fallback
3. **JARZ Package Plugin** - Creates compressed JARZ archives

## Customization

### Compression Settings

Modify the JARZ Package Plugin configuration in `pom.xml`:

```xml
<plugin>
    <groupId>net.jarz.streaming</groupId>
    <artifactId>jarz-package-plugin</artifactId>
    <configuration>
        <compressionLevel>3</compressionLevel>
        <blockSize>65536</blockSize>
        <enableDictionary>true</enableDictionary>
    </configuration>
</plugin>
```

### Adding Dependencies

Dependencies are automatically processed by JARZ plugins:

```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>my-library</artifactId>
    <version>1.0.0</version>
</dependency>
```

## License

This project follows the same dual licensing as JARZ Maven Plugins (AGPL v3 + Commercial).

**Project Website**: https://jarz-streaming.net  
**Contact**: ecosystem@plasticity.cloud
