# JARZ Maven Plugins - Version Management Scripts

## bump-version.sh

Automatically bumps project version after a public release.

### Usage

```bash
# Auto-increment minor version (1.0-SNAPSHOT → 1.1-SNAPSHOT)
./scripts/bump-version.sh

# Specify exact version
./scripts/bump-version.sh 2.0-SNAPSHOT

# After releasing 1.0.0 publicly
./scripts/bump-version.sh 1.1-SNAPSHOT
```

### Workflow

1. **Public release completed** (e.g., 1.0.0 released)
2. **Run script** in private repo: `./scripts/bump-version.sh 1.1-SNAPSHOT`
3. **Push changes**: `git push`
4. **Next sync** will use 1.1-SNAPSHOT (no conflicts)

### What it does

- Gets current version from pom.xml
- Updates all module versions with Maven versions plugin
- Commits changes with descriptive message
- Prompts for confirmation before making changes

### Safety Features

- Shows current and next version before proceeding
- Requires user confirmation
- Uses `mvn versions:commit` to clean up backup files
- Doesn't auto-push (you control when changes go remote)

## sync-to-public.sh

Synchronizes private repository tag to public repository using rsync strategy.

### Usage

```bash
# Sync specific tag to public repository (default path)
./scripts/sync-to-public.sh v1.0.0

# Sync to custom public repository path
./scripts/sync-to-public.sh v1.0.0 /path/to/custom/public/repo

# After creating new tag
git tag v1.0.0
git push origin v1.0.0
./scripts/sync-to-public.sh v1.0.0
```

### What it does

- Extracts specified tag to temporary directory
- Uses rsync with --delete to ensure exact synchronization
- Excludes .kiro* and .git* files automatically
- Commits and pushes changes to public repository
- Cleans up temporary files

### Advantages over git archive

- **True synchronization**: Removes files not in private repo
- **No leftover files**: --delete flag removes obsolete files
- **Reliable**: Most robust sync strategy
- **Safe**: Uses temporary directory, doesn't affect private repo
