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
 * The generate service impl class
 */
public class GenerateServiceImpl {

    /**
     * Process the generated service impl
     *
     * @param workingDirectory the root directory
     * @param classes          classes to generate
     * @throws MojoExecutionException if there is a problem generating
     */
    public static void execute(String workingDirectory, Set<Class<?>> classes) throws MojoExecutionException {
        String serviceImplDirectory = workingDirectory + "\\service\\impl";

        // Create the service impl directory
        try {
            FileUtils.forceMkdir(new File(serviceImplDirectory));
        } catch (IOException ioException) {
            throw new MojoExecutionException("Failed to create service impl directory.", ioException);
        }

        // Generate the services impl
        for (Class<?> clazz : classes) {
            generateServiceImpl(serviceImplDirectory, clazz);
        }
    }

    /**
     * Generate service impl method
     *
     * @param serviceImplDirectory the service impl directory
     * @param clazz                the class to generate
     */
    private static void generateServiceImpl(String serviceImplDirectory, Class<?> clazz) {
        try {
            // Generate the service impl
            FileWriter writer = writeServiceImpl(serviceImplDirectory, clazz, GenerateUtil.findIdClass(clazz));
            writer.close();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    /**
     * Write service impl of the given entity
     *
     * @param serviceImplDirectory the service impl directory
     * @param clazz                the entity class
     * @param entityIdClass        the entity id class
     * @return {@link FileWriter} file writer instance
     * @throws IOException when writing service impl has failed
     */
    private static FileWriter writeServiceImpl(String serviceImplDirectory, Class<?> clazz, Class<?> entityIdClass) throws IOException {
        String fileName = serviceImplDirectory + "\\" + CuspidSystem.getProperty(CuspidSystemProperty.PREFIX) + clazz.getSimpleName() + "ServiceImpl.java";
        LOGGER.instance().debug("Writing " + fileName);
        FileWriter writer = new FileWriter(fileName);
        String body = buildServiceImplBody(
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
    private static String buildServiceImplBody(
            String entityName,
            String entityIdName,
            String entityClassSimpleName,
            String entityIdClassSimpleName
    ) {
        return Template.SERVICE_IMPL_TEMPLATE
                .replaceAll("\\$\\{prefix\\}", (String) CuspidSystem.getProperty(CuspidSystemProperty.PREFIX))
                .replaceAll("\\$\\{prefix\\.lowercase\\}", GenerateUtil.firstLowerCase((String) CuspidSystem.getProperty(CuspidSystemProperty.PREFIX)))
                .replaceAll("\\$\\{entityName\\}", entityName)
                .replaceAll("\\$\\{entityIdName\\}", entityIdName)
                .replaceAll("\\$\\{entityClassSimpleName\\}", entityClassSimpleName)
                .replaceAll("\\$\\{entityIdClassSimpleName\\}", entityIdClassSimpleName);
    }

}
