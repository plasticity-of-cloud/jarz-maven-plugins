# JARZ Maven Archetype

Maven archetype for creating JARZ-enabled applications with all plugins pre-configured.

## Usage

### Generate a New Project

```bash
mvn archetype:generate \
    -DarchetypeGroupId=net.jarz.streaming \
    -DarchetypeArtifactId=jarz-archetype \
    -DarchetypeVersion=1.0.0-SNAPSHOT \
    -DgroupId=com.example \
    -DartifactId=my-jarz-app \
    -Dversion=1.0.0 \
    -Dpackage=com.example.myjarzapp
```

### Interactive Mode

```bash
mvn archetype:generate -DarchetypeGroupId=net.jarz.streaming -DarchetypeArtifactId=jarz-archetype
```

Then follow the prompts to specify:
- `groupId`: Your project's group ID (e.g., `com.example`)
- `artifactId`: Your project's artifact ID (e.g., `my-jarz-app`)
- `version`: Initial version (default: `1.0.0`)
- `package`: Java package name (default: same as groupId)

## Generated Project Structure

```
my-jarz-app/
├── pom.xml                    # Pre-configured with JARZ plugins
├── README.md                  # Project documentation
└── src/main/java/
    └── com/example/myjarzapp/
        └── App.java           # Sample application
```

## Generated Features

The archetype creates a project with:

### Pre-configured JARZ Plugins
- **JARZ Repository Plugin** - CDN repository configuration
- **JARZ Dependency Plugin** - Dependency resolution with JAR fallback
- **JARZ Package Plugin** - JARZ archive creation

### Sample Application
- Simple "Hello JARZ" application
- Demonstrates dependency usage (Apache Commons Lang)
- Shows JARZ packaging benefits

### Build Configuration
- Java 11+ compatibility
- Maven 3.8+ support
- JARZ BOM integration for version management

## Building Generated Project

```bash
cd my-jarz-app
mvn clean package
java -jar target/my-jarz-app-1.0.0.jar
```

## Customization

After generation, you can:

1. **Add dependencies** - They'll be automatically processed by JARZ plugins
2. **Modify compression settings** - Adjust JARZ Package Plugin configuration
3. **Configure repositories** - Customize JARZ Repository Plugin settings

## Publishing the Archetype

The archetype is published alongside other JARZ Maven plugins:

```bash
# Install locally
mvn clean install

# Publish to GitHub Packages
mvn clean deploy -P github
```

## Example Usage Scenarios

### Web Application
```bash
mvn archetype:generate \
    -DarchetypeGroupId=net.jarz.streaming \
    -DarchetypeArtifactId=jarz-archetype \
    -DgroupId=com.mycompany \
    -DartifactId=web-service \
    -Dpackage=com.mycompany.webservice
```

### Microservice
```bash
mvn archetype:generate \
    -DarchetypeGroupId=net.jarz.streaming \
    -DarchetypeArtifactId=jarz-archetype \
    -DgroupId=com.mycompany.microservices \
    -DartifactId=user-service \
    -Dpackage=com.mycompany.microservices.user
```

### CLI Tool
```bash
mvn archetype:generate \
    -DarchetypeGroupId=net.jarz.streaming \
    -DarchetypeArtifactId=jarz-archetype \
    -DgroupId=com.mycompany.tools \
    -DartifactId=data-processor \
    -Dpackage=com.mycompany.tools.dataprocessor
```

## Benefits

- **Quick Start**: Get JARZ-enabled project in seconds
- **Best Practices**: Pre-configured with optimal settings
- **Consistency**: Standardized project structure
- **Documentation**: Generated README with usage instructions

## Support

For archetype issues or questions:
- **Email**: ecosystem@plasticity.cloud
- **Website**: https://jarz-streaming.net
- **Documentation**: See main JARZ Maven Plugins README
