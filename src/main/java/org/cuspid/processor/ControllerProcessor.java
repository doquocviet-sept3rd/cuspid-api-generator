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
 * Controller processor
 */

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ControllerProcessor {

    /**
     * Generating controller classes
     *
     * @param workingDirectory the root directory
     * @param classes          classes to generate
     */
    @SneakyThrows
    public static void execute(String workingDirectory, Set<Class<?>> classes) {
        try {
            // Creating controller directory
            final String controllerDir = String.format("%s\\controller", workingDirectory);
            FileUtils.forceMkdir(new File(controllerDir));

            for (Class<?> clazz : classes) {
                String prefix = (String) CuspidSystem.get(CuspidSystemProperty.PREFIX);
                Map<String, Object> projections = Generators.getProjections(clazz);
                final String filename = String.format("%s\\%sController.java", controllerDir, prefix + clazz.getSimpleName());
                try (final FileWriter writer = new FileWriter(filename)) {
                    Mustache mustache = new DefaultMustacheFactory().compile("template/controller.mustache");
                    mustache.execute(writer, projections).flush();
                }
            }
        } catch (IOException ioException) {
            throw new MojoExecutionException("Failed to create controller directory.", ioException);
        }
    }

}
