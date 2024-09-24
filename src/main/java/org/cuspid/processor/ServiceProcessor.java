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

import static org.cuspid.constant.Template.SERVICE_IMPL_TEMPLATE;
import static org.cuspid.constant.Template.SERVICE_TEMPLATE;

/**
 * @author Do Quoc Viet
 * Service processor
 */

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceProcessor {

    /**
     * Generating service classes and their implementations
     *
     * @param workingDirectory the root directory
     * @param classes          classes to generate
     */
    @SneakyThrows
    public static void execute(String workingDirectory, Set<Class<?>> classes) {
        try {
            // Creating service directory
            final String serviceDir = String.format("%s\\service", workingDirectory);
            FileUtils.forceMkdir(new File(serviceDir));
            // Creating service implementations directory
            final String serviceImplDir = String.format("%s\\impl", serviceDir);
            FileUtils.forceMkdir(new File(serviceImplDir));

            for (Class<?> clazz : classes) {
                String prefix = (String) CuspidSystem.get(CuspidSystemProperty.PREFIX);
                Map<String, String> projections = Generators.getProjections(clazz);

                final String serviceFilename = String.format("%s\\%sService.java", serviceDir, prefix + clazz.getSimpleName());
                try (final FileWriter serviceWriter = new FileWriter(serviceFilename)) {
                    serviceWriter.write(Strings.substitute(SERVICE_TEMPLATE, projections));
                }

                final String serviceImplFilename = String.format("%s\\%sServiceImpl.java", serviceImplDir, prefix + clazz.getSimpleName());
                try (final FileWriter serviceImplWriter = new FileWriter(serviceImplFilename)) {
                    serviceImplWriter.write(Strings.substitute(SERVICE_IMPL_TEMPLATE, projections));
                }
            }
        } catch (IOException ioException) {
            throw new MojoExecutionException("Failed to create service interface directory.", ioException);
        }
    }

}
