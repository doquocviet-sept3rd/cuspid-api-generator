package org.cuspid;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.cuspid.constant.CuspidSystemProperty;
import org.cuspid.process.Process;
import org.cuspid.system.CuspidSystem;
import org.reflections.Reflections;

import javax.persistence.Entity;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Do Quoc Viet
 * The main code for the application
 */

@SuppressWarnings("unused")
@Mojo(name = "generate", defaultPhase = LifecyclePhase.COMPILE, requiresDependencyResolution = ResolutionScope.COMPILE)
public class CodeGenMojo extends AbstractMojo {

    @SuppressWarnings("unused")
    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject MAVEN_PROJECT;

    @Parameter(name = "prefix", defaultValue = "Cuspid", readonly = true)
    private String PREFIX;

    /**
     * Executes the application
     *
     * @throws MojoExecutionException when the application execution is interrupted
     */
    @Override
    @SuppressWarnings("unchecked")
    public void execute() throws MojoExecutionException {
        // Print the project information
        System.out.println("""
                ########################################################################
                # Thanks for using Cuspid Api Generator.                               #
                # Please consider donation to help us maintain this project.😊         #
                # https://org.cuspid/donate                                            #
                ########################################################################
                """);

        // Setting properties
        CuspidSystem.putProperty(CuspidSystemProperty.LOG, getLog());
        CuspidSystem.putProperty(CuspidSystemProperty.MAVEN_PROJECT, MAVEN_PROJECT);
        CuspidSystem.putProperty(CuspidSystemProperty.PREFIX, PREFIX);

        try {
            List<URL> projectClassPaths = new ArrayList<>();
            for (String element : (List<String>) MAVEN_PROJECT.getCompileClasspathElements()) {
                try {
                    projectClassPaths.add(new File(element).toURI().toURL());
                } catch (MalformedURLException malformedURLException) {
                    throw new MojoExecutionException(element + " is an invalid classpath element.", malformedURLException);
                }
            }
            Reflections reflections = new Reflections(new URLClassLoader(projectClassPaths.toArray(new URL[0])));
            CuspidSystem.putProperty(CuspidSystemProperty.ENTITIES, reflections.getTypesAnnotatedWith(Entity.class));
        } catch (DependencyResolutionRequiredException e) {
            throw new MojoExecutionException("Dependency resolution failed.", e);
        }

        // Execute process
        Process.execute();
    }
}
