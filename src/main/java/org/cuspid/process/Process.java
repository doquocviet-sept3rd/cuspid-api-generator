package org.cuspid.process;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.FileUtils;
import org.cuspid.constant.CuspidSystemProperty;
import org.cuspid.system.CuspidSystem;
import org.cuspid.util.LOGGER;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * @author Do Quoc Viet
 * The process class is responsible for creating repositories, services, and apis
 */
public class Process {

    /**
     * The execute method
     */
    @SuppressWarnings("unchecked")
    public static void execute() throws MojoExecutionException {
        LOGGER.instance().debug("Executing process");

        MavenProject mavenProject = (MavenProject) CuspidSystem.getProperty(CuspidSystemProperty.MAVEN_PROJECT);
        String workingDirectory = mavenProject.getBuild().getOutputDirectory() + "\\org\\cuspid\\generated";

        // Create the cuspid working directory
        try {
            FileUtils.forceMkdir(new File(workingDirectory));
        } catch (IOException ioException) {
            throw new MojoExecutionException("Failed to create cuspid working directory.", ioException);
        }

        Set<Class<?>> classes = (Set<Class<?>>) CuspidSystem.getProperty(CuspidSystemProperty.ENTITIES);
        GenerateRepositoryInterface.execute(workingDirectory, classes);
        GenerateServiceInterface.execute(workingDirectory, classes);
        GenerateServiceImpl.execute(workingDirectory, classes);
    }
}
