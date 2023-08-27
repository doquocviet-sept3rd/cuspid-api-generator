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
 * The generate repository class
 */

public class GenerateRepository {

    /**
     * Process the generated repository interface
     *
     * @param workingDirectory the root directory
     * @param classes          classes to generate
     * @throws MojoExecutionException if there is a problem generating
     */
    public static void execute(String workingDirectory, Set<Class<?>> classes) throws MojoExecutionException {
        String repositoryDirectory = workingDirectory + "\\repository";

        // Create the repository directory
        try {
            FileUtils.forceMkdir(new File(repositoryDirectory));
        } catch (IOException ioException) {
            throw new MojoExecutionException("Failed to create repository directory.", ioException);
        }

        // Generate the repository interface
        for (Class<?> clazz : classes) {
            generateRepositoryInterface(repositoryDirectory, clazz);
        }

    }

    /**
     * Generate repository interface method
     *
     * @param repositoryDirectory the repository directory
     * @param clazz               the class to generate
     */
    private static void generateRepositoryInterface(String repositoryDirectory, Class<?> clazz) {
        try {
            // Generate the repository interface
            Class<?> entityIdClass = Arrays.stream(clazz.getDeclaredFields())
                    .filter(declaredField -> Arrays
                            .stream(declaredField.getAnnotations())
                            .anyMatch(annotation -> annotation.annotationType().getTypeName().equals(Id.class.getTypeName())))
                    .findFirst()
                    .orElseThrow()
                    .getType();

            FileWriter writer = writeRepository(repositoryDirectory, clazz, entityIdClass);
            writer.close();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    /**
     * Write repository of the given entity
     *
     * @param repositoryDirectory the repository directory
     * @param clazz               the entity class
     * @param entityIdClass       the entity id class
     * @return {@link FileWriter} file writer instance
     * @throws IOException when writing repository has failed
     */
    private static FileWriter writeRepository(String repositoryDirectory, Class<?> clazz, Class<?> entityIdClass) throws IOException {
        FileWriter writer = new FileWriter(repositoryDirectory + "\\Cuspid" + clazz.getSimpleName() + "Repository.java");
        String body = buildRepositoryBody(
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
    private static String buildRepositoryBody(
            String entityName,
            String entityIdName,
            String entityClassSimpleName,
            String entityIdClassSimpleName
    ) {
        return String.format(Template.REPOSITORY_TEMPLATE,
                entityName,
                entityIdName,
                entityClassSimpleName,
                entityClassSimpleName,
                entityIdClassSimpleName);
    }

}
