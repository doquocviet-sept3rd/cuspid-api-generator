package org.cuspid.process;

import jakarta.persistence.Id;
import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.FileUtils;

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

    private static FileWriter writeRepository(String repositoryDirectory, Class<?> clazz, Class<?> entityIdClass) throws IOException {
        FileWriter writer = new FileWriter(repositoryDirectory + "\\" + clazz.getSimpleName() + "Repository.java");
        writer.write("package org.cuspid.generated.repository;\n\n");
        writer.write("import " + clazz.getPackage().getName() + "." + clazz.getSimpleName() + ";\n");
        writer.write("import org.springframework.data.jpa.repository.JpaRepository;\n");
        writer.write("import org.springframework.stereotype.Repository;\n");
        writer.write("import " + entityIdClass.getName() + ";\n\n");
        writer.write("@Repository\n");
        writer.write("public interface " + clazz.getSimpleName() + "Repository extends JpaRepository<" + clazz.getSimpleName() + ", " + entityIdClass.getSimpleName() + "> {\n");
        writer.write("}\n");
        return writer;
    }

}
