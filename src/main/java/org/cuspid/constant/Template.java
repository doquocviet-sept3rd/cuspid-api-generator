package org.cuspid.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author Do Quoc Viet
 * Template for the generation
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Template {
    public static final String REPOSITORY_TEMPLATE;
    public static final String SERVICE_TEMPLATE;
    public static final String SERVICE_IMPL_TEMPLATE;
    public static final String CONTROLLER_TEMPLATE;

    static {
        try {
            REPOSITORY_TEMPLATE = getTemplate("repository.template");
            SERVICE_TEMPLATE = getTemplate("service.template");
            SERVICE_IMPL_TEMPLATE = getTemplate("service_impl.template");
            CONTROLLER_TEMPLATE = getTemplate("controller.template");
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Get template
     *
     * @param filename filename
     * @return template
     */
    static String getTemplate(String filename) throws FileNotFoundException {
        try (InputStream in = Objects.requireNonNull(Template.class.getResource("/template/" + filename)).openStream()) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileNotFoundException(String.format("Could not read template file %s", filename));
        }
    }

}
