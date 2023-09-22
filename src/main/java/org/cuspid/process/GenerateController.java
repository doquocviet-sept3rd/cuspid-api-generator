package org.cuspid.process;

import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.FileUtils;
import org.cuspid.constant.CuspidSystemProperty;
import org.cuspid.constant.Template;
import org.cuspid.system.CuspidSystem;
import org.cuspid.util.GenerateUtil;
import org.cuspid.util.LOGGER;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

/**
 * @author Do Quoc Viet
 * The generate controller class
 */
public class GenerateController {

    /**
     * Process the generated controller
     *
     * @param workingDirectory the root directory
     * @param classes          classes to generate
     * @throws MojoExecutionException if there is a problem generating
     */
    public static void execute(String workingDirectory, Set<Class<?>> classes) throws MojoExecutionException {
        String controllerDirectory = workingDirectory + "\\api\\impl\\";

        // Create the controller directory
        try {
            FileUtils.forceMkdir(new File(controllerDirectory));
        } catch (IOException ioException) {
            throw new MojoExecutionException("Failed to create controller directory.", ioException);
        }

        // Generate the controllers
        for (Class<?> clazz : classes) {
            generateController(controllerDirectory, clazz);
        }
    }

    /**
     * Generate controller method
     *
     * @param controllerDirectory the controller directory
     * @param clazz               the class to generate
     */
    private static void generateController(String controllerDirectory, Class<?> clazz) {
        try {
            // Generate the controller
            FileWriter writer = writeController(controllerDirectory, clazz, GenerateUtil.findIdClass(clazz));
            writer.close();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    /**
     * Write controller of the given entity
     *
     * @param controllerDirectory the service interface directory
     * @param clazz               the entity class
     * @param entityIdClass       the entity id class
     * @return {@link FileWriter} file writer instance
     * @throws IOException when writing service interface has failed
     */
    private static FileWriter writeController(String controllerDirectory, Class<?> clazz, Class<?> entityIdClass) throws IOException {
        String fileName = controllerDirectory + "\\" + CuspidSystem.getProperty(CuspidSystemProperty.PREFIX) + clazz.getSimpleName() + "Controller.java";
        LOGGER.instance().debug("Writing " + fileName);
        FileWriter writer = new FileWriter(fileName);
        String body = buildControllerBody(
                clazz.getName(),
                entityIdClass.getName(),
                clazz.getSimpleName(),
                entityIdClass.getSimpleName()
        );
        writer.write(body);
        return writer;
    }

    /**
     * Build body for write method
     *
     * @param entityName              the name of the entity
     * @param entityIdName            the name of the entity identifier
     * @param entityClassSimpleName   the name of the entity class
     * @param entityIdClassSimpleName the name of the entity identifier class
     * @return {@link String} the body
     */
    private static String buildControllerBody(
            String entityName,
            String entityIdName,
            String entityClassSimpleName,
            String entityIdClassSimpleName
    ) {
        return Template.CONTROLLER_TEMPLATE
                .replaceAll("\\$\\{prefix\\}", (String) CuspidSystem.getProperty(CuspidSystemProperty.PREFIX))
                .replaceAll("\\$\\{prefix\\.lowercase\\}", GenerateUtil.firstLowerCase((String) CuspidSystem.getProperty(CuspidSystemProperty.PREFIX)))
                .replaceAll("\\$\\{entityName\\}", entityName)
                .replaceAll("\\$\\{entityIdName\\}", entityIdName)
                .replaceAll("\\$\\{entityClassSimpleName\\}", entityClassSimpleName)
                .replaceAll("\\$\\{entityIdClassSimpleName\\}", entityIdClassSimpleName);
    }

}
