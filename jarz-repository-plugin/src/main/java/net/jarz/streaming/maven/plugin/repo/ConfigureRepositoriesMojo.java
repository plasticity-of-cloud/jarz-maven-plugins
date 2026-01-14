package net.jarz.streaming.maven.plugin.repo;

import net.jarz.streaming.maven.shared.JarzConfiguration;
import org.apache.maven.model.Repository;
import org.apache.maven.model.RepositoryPolicy;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.List;

/**
 * Configures JARZ repositories for dependency resolution.
 * 
 * @author Plasticity.Cloud
 * @since 1.0
 */
@Mojo(name = "configure", defaultPhase = LifecyclePhase.INITIALIZE, threadSafe = true)
public class ConfigureRepositoriesMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    @Parameter(property = "jarz.repository", defaultValue = "https://cdn.jarz.io/maven2")
    private String jarzRepository;

    @Parameter(property = "jarz.repositoryId", defaultValue = "jarz-central")
    private String repositoryId;

    @Parameter(property = "jarz.priority", defaultValue = "true")
    private boolean priority;

    @Parameter(property = "jarz.skip", defaultValue = "false")
    private boolean skip;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skip) {
            getLog().info("Skipping JARZ repository configuration");
            return;
        }

        try {
            configureJarzRepository();
            getLog().info("Configured JARZ repository: " + jarzRepository);
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to configure JARZ repository", e);
        }
    }

    private void configureJarzRepository() {
        Repository jarzRepo = new Repository();
        jarzRepo.setId(repositoryId);
        jarzRepo.setName("JARZ Central Repository");
        jarzRepo.setUrl(jarzRepository);
        jarzRepo.setLayout("default");

        // Configure release policy
        RepositoryPolicy releasePolicy = new RepositoryPolicy();
        releasePolicy.setEnabled(true);
        releasePolicy.setUpdatePolicy("daily");
        releasePolicy.setChecksumPolicy("warn");
        jarzRepo.setReleases(releasePolicy);

        // Configure snapshot policy
        RepositoryPolicy snapshotPolicy = new RepositoryPolicy();
        snapshotPolicy.setEnabled(false);
        jarzRepo.setSnapshots(snapshotPolicy);

        List<Repository> repositories = project.getRepositories();
        
        // Remove existing JARZ repository if present
        repositories.removeIf(repo -> repositoryId.equals(repo.getId()));
        
        if (priority) {
            // Add at beginning for priority
            repositories.add(0, jarzRepo);
        } else {
            repositories.add(jarzRepo);
        }

        getLog().debug("Added JARZ repository with priority: " + priority);
    }
}
