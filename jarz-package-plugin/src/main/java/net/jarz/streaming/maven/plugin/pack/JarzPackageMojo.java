package net.jarz.streaming.maven.plugin.pack;

import net.jarz.streaming.maven.shared.JarzConfiguration;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates JARZ archive from compiled classes.
 * Replaces maven-jar-plugin functionality.
 * 
 * @author Plasticity.Cloud
 * @since 1.0
 */
@Mojo(name = "jarz", defaultPhase = LifecyclePhase.PACKAGE, threadSafe = true)
public class JarzPackageMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    @Component
    private MavenProjectHelper projectHelper;

    @Parameter(defaultValue = "${project.build.outputDirectory}")
    private File classesDirectory;

    @Parameter(defaultValue = "${project.build.directory}/${project.build.finalName}.jarz")
    private File jarzFile;

    @Parameter(property = "jarz.compressionLevel", defaultValue = "3")
    private int compressionLevel;

    @Parameter(property = "jarz.dictionaryPath")
    private String dictionaryPath;

    @Parameter(property = "jarz.skip", defaultValue = "false")
    private boolean skip;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skip) {
            getLog().info("Skipping JARZ creation");
            return;
        }

        if (!classesDirectory.exists()) {
            throw new MojoExecutionException("Classes directory does not exist: " + classesDirectory);
        }

        try {
            createJarzArchive();
            attachJarzArtifact();
            getLog().info("Created JARZ archive: " + jarzFile.getAbsolutePath());
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to create JARZ archive", e);
        }
    }

    private void createJarzArchive() throws Exception {
        // Ensure parent directory exists
        jarzFile.getParentFile().mkdirs();
        
        Path classesPath = classesDirectory.toPath();
        
        getLog().info("Creating JARZ from: " + classesPath);
        getLog().info("Compression level: " + compressionLevel);
        if (dictionaryPath != null) {
            getLog().info("Using dictionary: " + dictionaryPath);
        }
        
        // Collect all files from classes directory
        Map<String, byte[]> entries = new HashMap<>();
        collectEntries(classesDirectory, "", entries);
        
        // Use JARZ v2 API to create JARZ archive
        int targetBlockSize = 64 * 1024;
        int maxBlockSize = 128 * 1024;
        jdk.incubator.jarz.v2.BlockAssigner assigner = new jdk.incubator.jarz.v2.BlockAssigner(targetBlockSize, maxBlockSize);
        
        // Create empty dependency graph since we don't have dependency analysis
        jdk.incubator.jarz.v2.DependencyGraph graph = new jdk.incubator.jarz.v2.DependencyGraph();
        List<jdk.incubator.jarz.v2.Block> blocks = assigner.assignBlocks(entries, graph);
        
        // Write JARZ v2 archive
        try (jdk.incubator.jarz.v2.BlockWriter writer = new jdk.incubator.jarz.v2.BlockWriter(jarzFile.toPath())) {
            for (jdk.incubator.jarz.v2.Block block : blocks) {
                writer.writeBlock(block);
            }
        }
        
        getLog().info("Created JARZ archive: " + jarzFile.getAbsolutePath());
    }
    
    private void collectEntries(File directory, String prefix, Map<String, byte[]> entries) throws Exception {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    collectEntries(file, prefix + file.getName() + "/", entries);
                } else {
                    String entryName = prefix + file.getName();
                    entries.put(entryName, java.nio.file.Files.readAllBytes(file.toPath()));
                }
            }
        }
    }

    private void attachJarzArtifact() {
        // Set JARZ as the primary artifact (replaces JAR)
        project.getArtifact().setFile(jarzFile);
        
        getLog().debug("Set JARZ as primary artifact");
    }
}
