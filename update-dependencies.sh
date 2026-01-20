#!/bin/bash

echo "🔄 JARZ Maven Plugins - Dependency Update Script"
echo "================================================="

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to print status
print_status() {
    echo -e "${GREEN}✅ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠️  $1${NC}"
}

print_error() {
    echo -e "${RED}❌ $1${NC}"
}

echo "📋 Summary of Dependabot Updates Applied:"
echo "----------------------------------------"

echo "Maven Dependencies:"
echo "  • maven-plugin-testing-harness: 3.3.0 → 3.5.0"
echo "  • archetype-packaging: 3.2.1 → 3.4.1"
echo "  • maven-archetype-plugin: 3.2.1 → 3.4.1"

echo ""
echo "GitHub Actions:"
echo "  • actions/setup-java: 4 → 5"
echo "  • actions/upload-artifact: 4 → 6"
echo "  • actions/checkout: 4 → 6"
echo "  • actions/cache: 4 → 5"
echo "  • softprops/action-gh-release: 1 → 2"
echo "  • dorny/test-reporter: 1 → 2"
echo "  • actions/github-script: 7 → 8"

echo ""
echo "🧪 Running validation tests..."

# Test 1: Validate Maven build
echo "1. Testing Maven build..."
if mvn clean compile -q; then
    print_status "Maven compilation successful"
else
    print_error "Maven compilation failed"
    exit 1
fi

# Test 2: Validate dependency resolution
echo "2. Testing dependency resolution..."
if mvn dependency:resolve -q; then
    print_status "All dependencies resolved successfully"
else
    print_error "Dependency resolution failed"
    exit 1
fi

# Test 3: Run tests
echo "3. Running unit tests..."
if mvn test -q; then
    print_status "All tests passed"
else
    print_error "Some tests failed"
    exit 1
fi

# Test 4: Validate archetype
echo "4. Testing archetype build..."
if mvn clean package -pl jarz-archetype -q; then
    print_status "Archetype builds successfully"
else
    print_error "Archetype build failed"
    exit 1
fi

echo ""
print_status "All dependency updates validated successfully!"

echo ""
echo "📝 Next Steps:"
echo "1. Commit these changes to your repository"
echo "2. The Dependabot PRs should automatically close"
echo "3. Consider running a full integration test"

echo ""
echo "🚀 Ready to merge all Dependabot PRs!"
