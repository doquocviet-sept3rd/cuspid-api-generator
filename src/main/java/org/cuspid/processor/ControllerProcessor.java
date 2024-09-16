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

import static org.cuspid.constant.Template.CONTROLLER_TEMPLATE;

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
                Map<String, String> projections = Generators.getProjections(clazz);
                final String filename = String.format("%s\\%sController.java", controllerDir, prefix + clazz.getSimpleName());
                final FileWriter writer = new FileWriter(filename);
                writer.write(Strings.substitute(CONTROLLER_TEMPLATE, projections));
                writer.close();
            }
        } catch (IOException ioException) {
            throw new MojoExecutionException("Failed to create controller directory.", ioException);
        }
    }

}
