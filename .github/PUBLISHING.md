# GitHub Actions Setup for Maven Central Publishing

## Required Secrets

Add these secrets to your GitHub repository (Settings → Secrets and variables → Actions):

### 1. Sonatype OSSRH Credentials
- `OSSRH_USERNAME` - Your Sonatype JIRA username
- `OSSRH_TOKEN` - Your Sonatype JIRA password/token

### 2. GPG Signing Key
- `GPG_PRIVATE_KEY` - Your GPG private key (export with: `gpg --armor --export-secret-keys YOUR_KEY_ID`)
- `GPG_PASSPHRASE` - Your GPG key passphrase

## Setup Steps

### 1. Create Sonatype OSSRH Account
1. Create account at https://issues.sonatype.org/
2. Create a ticket to claim your group ID `net.jarz.streaming`
3. Follow verification process

### 2. Generate GPG Key
```bash
gpg --gen-key
gpg --list-secret-keys --keyid-format LONG
gpg --armor --export-secret-keys YOUR_KEY_ID
gpg --keyserver keyserver.ubuntu.com --send-keys YOUR_KEY_ID
```

### 3. Release Process
1. Update version in `pom.xml` (remove `-SNAPSHOT`)
2. Create and push git tag: `git tag v1.0.0 && git push origin v1.0.0`
3. GitHub Actions will automatically build and deploy to Maven Central

## Workflows

- **CI** (`ci.yml`) - Runs on every push/PR to test the build
- **Release** (`release.yml`) - Runs on git tags to publish to Maven Central

## Maven Central Requirements Met

✅ **Group ID ownership** - `net.jarz.streaming` (requires verification)  
✅ **POM completeness** - Name, description, URL, licenses, developers, SCM  
✅ **Javadoc and sources** - Generated during release profile  
✅ **GPG signing** - All artifacts signed during release  
✅ **Staging repository** - Uses Nexus staging plugin
