package org.cuspid.processor;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.FileUtils;
import org.cuspid.constant.CuspidSystemProperty;
import org.cuspid.system.CuspidSystem;
import org.cuspid.util.Generators;
import org.cuspid.util.Strings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static org.cuspid.constant.Template.REPOSITORY_TEMPLATE;

/**
 * @author Do Quoc Viet
 * Repository processor
 */

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RepositoryProcessor {

    /**
     * Generating repository classes
     *
     * @param workingDirectory the root directory
     * @param classes          classes to generate
     */
    @SneakyThrows
    public static void execute(String workingDirectory, Set<Class<?>> classes) {
        try {
            // Creating repository directory
            final String repositoryDir = String.format("%s\\repository", workingDirectory);
            FileUtils.forceMkdir(new File(repositoryDir));

            for (Class<?> clazz : classes) {
                String prefix = (String) CuspidSystem.get(CuspidSystemProperty.PREFIX);
                Map<String, String> projections = Generators.getProjections(clazz);
                final String filename = String.format("%s\\%sRepository.java", repositoryDir, prefix + clazz.getSimpleName());
                final FileWriter writer = new FileWriter(filename);
                writer.write(Strings.substitute(REPOSITORY_TEMPLATE, projections));
                writer.close();
            }
        } catch (IOException ioException) {
            throw new MojoExecutionException("Failed to create service interface directory.", ioException);
        }
    }

}
