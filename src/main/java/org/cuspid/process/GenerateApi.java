package org.cuspid.process;

import jakarta.persistence.Id;
import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.FileUtils;
import org.cuspid.constant.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

/**
 * @author Do Quoc Viet
 * The generate api class
 */
public class GenerateApi {
    /**
     * Process the generated api interface
     *
     * @param workingDirectory the root directory
     * @param classes          classes to generate
     * @throws MojoExecutionException if there is a problem generating
     */
    public static void execute(String workingDirectory, Set<Class<?>> classes) throws MojoExecutionException {
        String apiInterfaceDirectory = workingDirectory + "\\api";

        // Create the api directory
        try {
            FileUtils.forceMkdir(new File(apiInterfaceDirectory));
        } catch (IOException ioException) {
            throw new MojoExecutionException("Failed to create api interface directory.", ioException);
        }

        // Generate the apis interface
        for (Class<?> clazz : classes) {
            generateApiInterface(apiInterfaceDirectory, clazz);
        }
    }

    /**
     * Generate api interface method
     *
     * @param apiInterfaceDirectory the api interface directory
     * @param clazz                 the class to generate
     */
    private static void generateApiInterface(String apiInterfaceDirectory, Class<?> clazz) {
        try {
            // Generate the api interface
            Class<?> entityIdClass = Arrays.stream(clazz.getDeclaredFields())
                    .filter(declaredField -> Arrays
                            .stream(declaredField.getAnnotations())
                            .anyMatch(annotation -> annotation.annotationType().getTypeName().equals(Id.class.getTypeName())))
                    .findFirst()
                    .orElseThrow()
                    .getType();
            FileWriter writer = writeApiInterface(apiInterfaceDirectory, clazz, entityIdClass);
            writer.close();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    /**
     * Write api interface of the given entity
     *
     * @param apiInterfaceDirectory the api interface directory
     * @param clazz                 the entity class
     * @param entityIdClass         the entity id class
     * @return {@link FileWriter} file writer instance
     * @throws IOException when writing service interface has failed
     */
    private static FileWriter writeApiInterface(String apiInterfaceDirectory, Class<?> clazz, Class<?> entityIdClass) throws IOException {
        FileWriter writer = new FileWriter(apiInterfaceDirectory + "\\Cuspid" + clazz.getSimpleName() + "Api.java");
        String body = buildApiInterfaceBody(
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
    private static String buildApiInterfaceBody(
            String entityName,
            String entityIdName,
            String entityClassSimpleName,
            String entityIdClassSimpleName
    ) {
        return Template.API_TEMPLATE
                .replaceAll("\\$\\{entityName\\}", entityName)
                .replaceAll("\\$\\{entityIdName\\}", entityIdName)
                .replaceAll("\\$\\{entityClassSimpleName\\}", entityClassSimpleName)
                .replaceAll("\\$\\{entityIdClassSimpleName\\}", entityIdClassSimpleName);
    }
}
