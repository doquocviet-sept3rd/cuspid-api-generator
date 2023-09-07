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
 * The generate repository class
 */

public class GenerateRepositoryInterface {

    /**
     * Process the generated repository interface
     *
     * @param workingDirectory the root directory
     * @param classes          classes to generate
     * @throws MojoExecutionException if there is a problem generating
     */
    public static void execute(String workingDirectory, Set<Class<?>> classes) throws MojoExecutionException {
        String repositoryInterfaceDirectory = workingDirectory + "\\repository";

        // Create the repository directory
        try {
            FileUtils.forceMkdir(new File(repositoryInterfaceDirectory));
        } catch (IOException ioException) {
            throw new MojoExecutionException("Failed to create repository directory.", ioException);
        }

        // Generate the repositories interface
        for (Class<?> clazz : classes) {
            generateRepositoryInterface(repositoryInterfaceDirectory, clazz);
        }

    }

    /**
     * Generate repository interface method
     *
     * @param repositoryInterfaceDirectory the repository directory
     * @param clazz                        the class to generate
     */
    private static void generateRepositoryInterface(String repositoryInterfaceDirectory, Class<?> clazz) {
        try {
            // Generate the repository interface
            FileWriter writer = writeRepository(repositoryInterfaceDirectory, clazz, GenerateUtil.findIdClass(clazz));
            writer.close();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    /**
     * Write repository of the given entity
     *
     * @param repositoryInterfaceDirectory the repository directory
     * @param clazz                        the entity class
     * @param entityIdClass                the entity id class
     * @return {@link FileWriter} file writer instance
     * @throws IOException when writing repository has failed
     */
    private static FileWriter writeRepository(String repositoryInterfaceDirectory, Class<?> clazz, Class<?> entityIdClass) throws IOException {
        FileWriter writer = new FileWriter(repositoryInterfaceDirectory + "\\" + CuspidSystem.getProperty(CuspidSystemProperty.PREFIX) + clazz.getSimpleName() + "Repository.java");
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
        return Template.REPOSITORY_INTERFACE_TEMPLATE
                .replaceAll("\\$\\{prefix\\}", (String) CuspidSystem.getProperty(CuspidSystemProperty.PREFIX))
                .replaceAll("\\$\\{prefix\\.lowercase\\}", GenerateUtil.firstLowerCase((String) CuspidSystem.getProperty(CuspidSystemProperty.PREFIX)))
                .replaceAll("\\$\\{entityName\\}", entityName)
                .replaceAll("\\$\\{entityIdName\\}", entityIdName)
                .replaceAll("\\$\\{entityClassSimpleName\\}", entityClassSimpleName)
                .replaceAll("\\$\\{entityIdClassSimpleName\\}", entityIdClassSimpleName);
    }

}
