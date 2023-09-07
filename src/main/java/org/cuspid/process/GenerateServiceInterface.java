package org.cuspid.process;

import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.FileUtils;
import org.cuspid.constant.CuspidSystemProperty;
import org.cuspid.constant.Template;
import org.cuspid.system.CuspidSystem;
import org.cuspid.util.GenerateUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

/**
 * @author Do Quoc Viet
 * The generate service interface class
 */
public class GenerateServiceInterface {

    /**
     * Process the generated service interface
     *
     * @param workingDirectory the root directory
     * @param classes          classes to generate
     * @throws MojoExecutionException if there is a problem generating
     */
    public static void execute(String workingDirectory, Set<Class<?>> classes) throws MojoExecutionException {
        String serviceInterfaceDirectory = workingDirectory + "\\service";

        // Create the service interface directory
        try {
            FileUtils.forceMkdir(new File(serviceInterfaceDirectory));
        } catch (IOException ioException) {
            throw new MojoExecutionException("Failed to create service interface directory.", ioException);
        }

        // Generate the services interface
        for (Class<?> clazz : classes) {
            generateServiceInterface(serviceInterfaceDirectory, clazz);
        }
    }

    /**
     * Generate service interface method
     *
     * @param serviceInterfaceDirectory the service interface directory
     * @param clazz                     the class to generate
     */
    private static void generateServiceInterface(String serviceInterfaceDirectory, Class<?> clazz) {
        try {
            // Generate the service interface
            FileWriter writer = writeServiceInterface(serviceInterfaceDirectory, clazz, GenerateUtil.findIdClass(clazz));
            writer.close();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    /**
     * Write service interface of the given entity
     *
     * @param serviceInterfaceDirectory the service interface directory
     * @param clazz                     the entity class
     * @param entityIdClass             the entity id class
     * @return {@link FileWriter} file writer instance
     * @throws IOException when writing service interface has failed
     */
    private static FileWriter writeServiceInterface(String serviceInterfaceDirectory, Class<?> clazz, Class<?> entityIdClass) throws IOException {
        FileWriter writer = new FileWriter(serviceInterfaceDirectory + "\\" + CuspidSystem.getProperty(CuspidSystemProperty.PREFIX) + clazz.getSimpleName() + "Service.java");
        String body = buildServiceInterfaceBody(
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
    private static String buildServiceInterfaceBody(
            String entityName,
            String entityIdName,
            String entityClassSimpleName,
            String entityIdClassSimpleName
    ) {
        return Template.SERVICE_INTERFACE_TEMPLATE
                .replaceAll("\\$\\{prefix\\}", (String) CuspidSystem.getProperty(CuspidSystemProperty.PREFIX))
                .replaceAll("\\$\\{prefix\\.lowercase\\}", GenerateUtil.firstLowerCase((String) CuspidSystem.getProperty(CuspidSystemProperty.PREFIX)))
                .replaceAll("\\$\\{entityName\\}", entityName)
                .replaceAll("\\$\\{entityIdName\\}", entityIdName)
                .replaceAll("\\$\\{entityClassSimpleName\\}", entityClassSimpleName)
                .replaceAll("\\$\\{entityIdClassSimpleName\\}", entityIdClassSimpleName);
    }

}
