package org.cuspid.processor;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.FileUtils;
import org.cuspid.constant.CuspidSystemProperty;
import org.cuspid.system.CuspidSystem;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * @author Do Quoc Viet
 * Processing generating classes
 */

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Processor {

    /**
     * The execute method
     */
    @SuppressWarnings("unchecked")
    public static void execute() throws MojoExecutionException {
        log.debug("Executing process");

        MavenProject mavenProject = (MavenProject) CuspidSystem.get(CuspidSystemProperty.MAVEN_PROJECT);
        // TODO Convert to output build directory when available for production
        String workingDirectory = String.format("%s\\org\\cuspid\\generated", mavenProject.getBuild().getSourceDirectory());

        try {
            // Create working directory
            FileUtils.forceMkdir(new File(workingDirectory));
            Set<Class<?>> classes = (Set<Class<?>>) CuspidSystem.get(CuspidSystemProperty.ENTITIES);
            RepositoryProcessor.execute(workingDirectory, classes);
            ServiceProcessor.execute(workingDirectory, classes);
            ControllerProcessor.execute(workingDirectory, classes);
        } catch (IOException ioException) {
            throw new MojoExecutionException("Failed to create cuspid working directory.", ioException);
        }
    }
}
