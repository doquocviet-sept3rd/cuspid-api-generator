package org.cuspid;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.cuspid.annotation.CuspidApi;
import org.cuspid.constant.CuspidSystemProperty;
import org.cuspid.process.Process;
import org.cuspid.system.CuspidSystem;
import org.reflections.Reflections;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Do Quoc Viet
 * The main code for the application
 */

@SuppressWarnings("unused")
@Mojo(name = "generate", defaultPhase = LifecyclePhase.COMPILE, requiresDependencyResolution = ResolutionScope.COMPILE)
public class CodeGenMojo extends AbstractMojo {

    @SuppressWarnings("unused")
    @Parameter(defaultValue = "${project}")
    private MavenProject mavenProject;

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
                # Please consider donation to help us maintain this project.           #
                # https://org.cuspid/donate                                            #
                ########################################################################
                """);

        // Setting properties
        CuspidSystem.putProperty(CuspidSystemProperty.LOG, getLog());
        CuspidSystem.putProperty(CuspidSystemProperty.MAVEN_PROJECT, mavenProject);

        try {
            List<URL> projectClassPaths = new ArrayList<>();
            for (String element : (List<String>) mavenProject.getCompileClasspathElements()) {
                try {
                    projectClassPaths.add(new File(element).toURI().toURL());
                } catch (MalformedURLException malformedURLException) {
                    throw new MojoExecutionException(element + " is an invalid classpath element.", malformedURLException);
                }
            }
            Reflections reflections = new Reflections(new URLClassLoader(projectClassPaths.toArray(new URL[0])));
            Set<Class<?>> entities = reflections.getTypesAnnotatedWith(CuspidApi.class);
            CuspidSystem.putProperty(CuspidSystemProperty.ENTITIES, entities);
        } catch (DependencyResolutionRequiredException e) {
            throw new MojoExecutionException("Dependency resolution failed.", e);
        }

        // Execute process
        Process.execute();
    }
}
