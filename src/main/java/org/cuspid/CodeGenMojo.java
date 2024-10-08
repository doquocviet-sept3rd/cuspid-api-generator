package org.cuspid;

import jakarta.persistence.Entity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.cuspid.constant.CuspidSystemProperty;
import org.cuspid.processor.Processor;
import org.cuspid.system.CuspidSystem;
import org.reflections.Reflections;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static org.cuspid.constant.CuspidSystemProperty.MAVEN_PROJECT;
import static org.cuspid.constant.CuspidSystemProperty.OUTPUT_DIR;
import static org.cuspid.constant.CuspidSystemProperty.PREFIX;

/**
 * @author Do Quoc Viet
 * The main code for the application
 */

@SuppressWarnings("unused")
@Slf4j
@Mojo(name = "generate", defaultPhase = LifecyclePhase.COMPILE, requiresDependencyResolution = ResolutionScope.COMPILE)
public class CodeGenMojo extends AbstractMojo {

    @SuppressWarnings("unused")
    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject mavenProject;

    @Parameter(defaultValue = "Cuspid", readonly = true)
    private String prefix;

    @Parameter(required = true)
    private String outputDir;

    @Override
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public void execute() {
        log.info("""
                
                ########################################################################
                # Thanks for using Cuspid Api Generator.                               #
                # Please consider donation to help us maintain this project.😊         #
                # https://org.cuspid/donate                                            #
                ########################################################################
                """);

        // Setting properties
        CuspidSystem.put(MAVEN_PROJECT, mavenProject);
        CuspidSystem.put(PREFIX, prefix);
        CuspidSystem.put(OUTPUT_DIR, outputDir);

        try {
            final Function<URI, URL> uriToUrl = uri -> {
                try {
                    return uri.toURL();
                } catch (MalformedURLException ignored) {
                    // do nothing
                }
                return null;
            };
            URL[] classPaths = ((List<String>) mavenProject.getCompileClasspathElements())
                    .stream()
                    .map(File::new)
                    .map(File::toURI)
                    .map(uriToUrl)
                    .filter(Objects::nonNull)
                    .toArray(URL[]::new);
            Reflections reflections = new Reflections(new URLClassLoader(classPaths));
            CuspidSystem.put(CuspidSystemProperty.ENTITIES, reflections.getTypesAnnotatedWith(Entity.class));
        } catch (DependencyResolutionRequiredException e) {
            throw new MojoExecutionException("Dependency resolution failed.", e);
        }

        // Execute process
        Processor.execute();
    }
}
