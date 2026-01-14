# Publishing JARZ Maven Plugins

This guide covers how to publish the JARZ Maven plugins to Maven repositories.

## Publishing Options

### 1. Maven Central (Recommended for Public Release)

#### Prerequisites
- Sonatype OSSRH account
- GPG key for signing
- Domain verification for `net.jarz.streaming` groupId

#### Steps

1. **Configure Maven settings** (`~/.m2/settings.xml`):
```xml
<settings>
    <servers>
        <server>
            <id>ossrh</id>
            <username>your-sonatype-username</username>
            <password>your-sonatype-password</password>
        </server>
    </servers>
    <profiles>
        <profile>
            <id>ossrh</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <gpg.executable>gpg</gpg.executable>
                <gpg.passphrase>your-gpg-passphrase</gpg.passphrase>
            </properties>
        </profile>
    </profiles>
</settings>
```

2. **Add distribution management to parent pom.xml**:
```xml
<distributionManagement>
    <snapshotRepository>
        <id>ossrh</id>
        <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
    </snapshotRepository>
    <repository>
        <id>ossrh</id>
        <url>https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/</url>
    </repository>
</distributionManagement>
```

3. **Add required plugins for Central**:
```xml
<build>
    <plugins>
        <!-- Sources -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-source-plugin</artifactId>
            <version>3.3.0</version>
            <executions>
                <execution>
                    <id>attach-sources</id>
                    <goals>
                        <goal>jar-no-fork</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
        
        <!-- Javadoc -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-javadoc-plugin</artifactId>
            <version>3.6.3</version>
            <executions>
                <execution>
                    <id>attach-javadocs</id>
                    <goals>
                        <goal>jar</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
        
        <!-- GPG Signing -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-gpg-plugin</artifactId>
            <version>3.1.0</version>
            <executions>
                <execution>
                    <id>sign-artifacts</id>
                    <phase>verify</phase>
                    <goals>
                        <goal>sign</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
        
        <!-- Nexus Staging -->
        <plugin>
            <groupId>org.sonatype.plugins</groupId>
            <artifactId>nexus-staging-maven-plugin</artifactId>
            <version>1.6.13</version>
            <extensions>true</extensions>
            <configuration>
                <serverId>ossrh</serverId>
                <nexusUrl>https://s01.oss.sonatype.org/</nexusUrl>
                <autoReleaseAfterClose>true</autoReleaseAfterClose>
            </configuration>
        </plugin>
    </plugins>
</build>
```

4. **Deploy to Central**:
```bash
# Deploy snapshot
mvn clean deploy

# Release version
mvn versions:set -DnewVersion=1.0.0
mvn clean deploy -P release
```

### 2. GitHub Packages (Quick Start)

#### Configure GitHub Packages in parent pom.xml:
```xml
<distributionManagement>
    <repository>
        <id>github</id>
        <name>GitHub Packages</name>
        <url>https://maven.pkg.github.com/plasticity-cloud/jarz-maven-plugins</url>
    </repository>
</distributionManagement>
```

#### Deploy to GitHub Packages:
```bash
mvn clean deploy
```

### 3. Private Nexus Repository

#### Configure private repository:
```xml
<distributionManagement>
    <repository>
        <id>jarz-releases</id>
        <url>https://nexus.jarz-streaming.net/repository/maven-releases/</url>
    </repository>
    <snapshotRepository>
        <id>jarz-snapshots</id>
        <url>https://nexus.jarz-streaming.net/repository/maven-snapshots/</url>
    </snapshotRepository>
</distributionManagement>
```

## Release Process

### 1. Prepare Release

```bash
# Update versions
mvn versions:set -DnewVersion=1.0.0
mvn versions:commit

# Update README and documentation
# Commit changes
git add .
git commit -m "Release 1.0.0"
git tag v1.0.0
```

### 2. Deploy Release

```bash
# Deploy to repository
mvn clean deploy -P release

# Push tags
git push origin v1.0.0
git push origin main
```

### 3. Post-Release

```bash
# Bump to next snapshot
mvn versions:set -DnewVersion=1.1.0-SNAPSHOT
mvn versions:commit
git add .
git commit -m "Bump to 1.1.0-SNAPSHOT"
git push origin main
```

## Usage After Publishing

Once published, users can use the plugins by adding to their `pom.xml`:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>net.jarz.streaming</groupId>
            <artifactId>jarz-bom</artifactId>
            <version>1.0.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

<build>
    <plugins>
        <plugin>
            <groupId>net.jarz.streaming</groupId>
            <artifactId>jarz-package-plugin</artifactId>
            <executions>
                <execution>
                    <phase>package</phase>
                    <goals>
                        <goal>jarz</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

## Verification

After publishing, verify the plugins work:

```bash
cd examples/hello-jarz
mvn clean package
java -jar target/hello-jarz-1.0.0.jar
```

## Troubleshooting

### Common Issues

1. **GPG Signing Fails**: Ensure GPG key is properly configured
2. **Sonatype Upload Fails**: Check credentials and repository URLs
3. **Plugin Not Found**: Verify groupId and version are correct

### Support

For publishing issues, contact: ecosystem@plasticity.cloud
