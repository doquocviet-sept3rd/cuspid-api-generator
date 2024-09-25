package org.cuspid.processor;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.FileUtils;
import org.cuspid.constant.CuspidSystemProperty;
import org.cuspid.system.CuspidSystem;
import org.cuspid.util.Generators;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

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
                Map<String, Object> projections = Generators.getProjections(clazz);

                final String serviceFilename = String.format("%s\\%sService.java", serviceDir, prefix + clazz.getSimpleName());
                try (final FileWriter serviceWriter = new FileWriter(serviceFilename)) {
                    Mustache mustache = new DefaultMustacheFactory().compile("template/service.mustache");
                    mustache.execute(serviceWriter, projections).flush();
                }

                final String serviceImplFilename = String.format("%s\\%sServiceImpl.java", serviceImplDir, prefix + clazz.getSimpleName());
                try (final FileWriter serviceImplWriter = new FileWriter(serviceImplFilename)) {
                    Mustache mustache = new DefaultMustacheFactory().compile("template/service_impl.mustache");
                    mustache.execute(serviceImplWriter, projections).flush();
                }
            }
        } catch (IOException ioException) {
            throw new MojoExecutionException("Failed to create service interface directory.", ioException);
        }
    }

}
