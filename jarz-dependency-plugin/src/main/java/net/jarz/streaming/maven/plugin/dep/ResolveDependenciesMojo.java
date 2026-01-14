package net.jarz.streaming.maven.plugin.dep;

import net.jarz.streaming.maven.shared.JarzConfiguration;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Set;

/**
 * Resolves JARZ dependencies from CDN.
 * Replaces maven-dependency-plugin functionality.
 * 
 * @author Plasticity.Cloud
 * @since 1.0
 */
@Mojo(name = "resolve", defaultPhase = LifecyclePhase.PROCESS_RESOURCES, threadSafe = true)
public class ResolveDependenciesMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    @Parameter(property = "jarz.repository", defaultValue = "https://cdn.jarz.io/maven2")
    private String jarzRepository;

    @Parameter(property = "jarz.fallbackToJar", defaultValue = "true")
    private boolean fallbackToJar;

    @Parameter(property = "jarz.strictMode", defaultValue = "false")
    private boolean strictMode;

    @Parameter(property = "jarz.skip", defaultValue = "false")
    private boolean skip;

    @Parameter(defaultValue = "${project.build.directory}/jarz-dependencies")
    private File outputDirectory;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skip) {
            getLog().info("Skipping JARZ dependency resolution");
            return;
        }

        try {
            outputDirectory.mkdirs();
            resolveDependencies();
            getLog().info("Resolved JARZ dependencies to: " + outputDirectory);
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to resolve JARZ dependencies", e);
        }
    }

    private void resolveDependencies() throws Exception {
        Set<Artifact> dependencies = project.getArtifacts();
        
        for (Artifact artifact : dependencies) {
            if ("jar".equals(artifact.getType())) {
                resolveJarzDependency(artifact);
            }
        }
    }

    private void resolveJarzDependency(Artifact artifact) throws Exception {
        String jarzUrl = buildJarzUrl(artifact);
        String jarUrl = buildJarUrl(artifact);
        
        Path targetPath = outputDirectory.toPath().resolve(
            artifact.getArtifactId() + "-" + artifact.getVersion() + ".jarz"
        );

        try {
            // Try JARZ first
            downloadFile(jarzUrl, targetPath);
            getLog().info("Downloaded JARZ: " + artifact.getArtifactId());
        } catch (Exception e) {
            if (fallbackToJar && !strictMode) {
                // Fallback to JAR
                Path jarPath = outputDirectory.toPath().resolve(
                    artifact.getArtifactId() + "-" + artifact.getVersion() + ".jar"
                );
                downloadFile(jarUrl, jarPath);
                getLog().warn("Fallback to JAR: " + artifact.getArtifactId());
            } else {
                throw new Exception("JARZ not available and fallback disabled: " + artifact.getArtifactId(), e);
            }
        }
    }

    private String buildJarzUrl(Artifact artifact) {
        return String.format("%s/%s/%s/%s/%s-%s.jarz",
            jarzRepository,
            artifact.getGroupId().replace('.', '/'),
            artifact.getArtifactId(),
            artifact.getVersion(),
            artifact.getArtifactId(),
            artifact.getVersion()
        );
    }

    private String buildJarUrl(Artifact artifact) {
        return String.format("%s/%s/%s/%s/%s-%s.jar",
            jarzRepository,
            artifact.getGroupId().replace('.', '/'),
            artifact.getArtifactId(),
            artifact.getVersion(),
            artifact.getArtifactId(),
            artifact.getVersion()
        );
    }

    private void downloadFile(String url, Path targetPath) throws Exception {
        getLog().debug("Downloading: " + url);
        
        try (var inputStream = new URL(url).openStream()) {
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
