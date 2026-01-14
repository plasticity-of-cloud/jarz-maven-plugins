# GitHub Packages vs Maven Central

## GitHub Packages (Easier Setup)

### ✅ Advantages:
- **No approval needed** - Works immediately
- **Automatic authentication** - Uses `GITHUB_TOKEN`
- **Private repositories** - Can publish private packages
- **Integrated with GitHub** - Same permissions as repository

### ❌ Limitations:
- **GitHub account required** - Users need GitHub authentication to download
- **Less discoverable** - Not in public Maven search
- **GitHub-specific** - Tied to GitHub ecosystem

### Usage:
```xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/plasticity-cloud/jarz-maven-plugins</url>
    </repository>
</repositories>
```

Users need GitHub token in `~/.m2/settings.xml`:
```xml
<servers>
    <server>
        <id>github</id>
        <username>GITHUB_USERNAME</username>
        <password>GITHUB_TOKEN</password>
    </server>
</servers>
```

## Maven Central (Public Distribution)

### ✅ Advantages:
- **Public access** - No authentication needed for downloads
- **Standard repository** - Default in all Maven builds
- **Discoverable** - Appears in Maven search engines
- **Industry standard** - Expected for open source libraries

### ❌ Requirements:
- **Group ID verification** - Must prove domain ownership
- **GPG signing** - All artifacts must be signed
- **Complete metadata** - Strict POM requirements
- **Manual approval** - Initial setup requires Sonatype ticket

## Recommendation

**For internal/private use**: GitHub Packages  
**For public open source**: Maven Central  
**For quick testing**: GitHub Packages first, then Maven Central

Both workflows are configured - choose based on your distribution needs!
