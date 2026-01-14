# GitHub Actions Workflows

This directory contains GitHub Actions workflows for the JARZ Maven Plugins project.

## Workflows

### 1. CI (`ci.yml`)
**Triggers:** Push to main/develop, Pull requests to main

**Purpose:** Continuous integration testing and building
- Runs unit tests with JUnit reporting
- Builds all plugins and examples
- Tests Hello JARZ example execution
- Uploads build artifacts

### 2. Publish to GitHub Packages (`github-packages.yml`)
**Triggers:** Git tags (v*), Manual dispatch

**Purpose:** Publishes releases to GitHub Packages
- Builds and tests all plugins
- Publishes to GitHub Packages Maven registry
- Creates GitHub releases with auto-generated notes
- Supports manual version override

### 3. Publish Snapshots (`publish-snapshots.yml`)
**Triggers:** Push to main branch

**Purpose:** Automatic snapshot publishing
- Publishes SNAPSHOT versions on every main branch commit
- Adds commit comments with usage instructions
- Skips if commit message contains `[skip ci]`

### 4. Test Examples (`test-examples.yml`)
**Triggers:** Changes to examples/ or pom.xml files

**Purpose:** Validates example projects
- Tests Hello JARZ example compilation and execution
- Verifies JARZ file creation
- Uploads example artifacts

## Usage

### Publishing a Release

1. **Create and push a tag:**
   ```bash
   git tag v1.0.0
   git push origin v1.0.0
   ```

2. **Manual release (alternative):**
   - Go to Actions → "Publish to GitHub Packages"
   - Click "Run workflow"
   - Specify version (optional)

### Using Published Packages

Add to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/plasticity-cloud/jarz-maven-plugins-private</url>
    </repository>
</repositories>

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
```

### Authentication for GitHub Packages

Add to `~/.m2/settings.xml`:

```xml
<servers>
    <server>
        <id>github</id>
        <username>YOUR_GITHUB_USERNAME</username>
        <password>YOUR_GITHUB_TOKEN</password>
    </server>
</servers>
```

## Workflow Features

### Security
- Uses `GITHUB_TOKEN` for authentication
- Minimal required permissions
- Secure artifact handling

### Performance
- Maven dependency caching
- Conditional execution (skip CI)
- Parallel job execution where possible

### Reliability
- Comprehensive testing before publishing
- Example validation
- Automatic rollback on failures

### Visibility
- Test reporting with dorny/test-reporter
- Commit comments for snapshots
- Detailed release notes
- Artifact uploads for debugging

## Troubleshooting

### Common Issues

1. **Authentication Failed**
   - Verify GITHUB_TOKEN permissions
   - Check repository access settings

2. **Build Failures**
   - Check Java version compatibility
   - Verify Maven dependencies

3. **Example Test Failures**
   - Ensure plugins are properly installed
   - Check Hello JARZ application output

### Debugging

- Check workflow logs in Actions tab
- Download artifacts for local testing
- Use workflow_dispatch for manual testing

## Maintenance

### Regular Tasks
- Update action versions (dependabot handles this)
- Monitor workflow performance
- Review security alerts

### Version Updates
- Update Java version in all workflows
- Update Maven plugin versions
- Test with new GitHub Actions features
