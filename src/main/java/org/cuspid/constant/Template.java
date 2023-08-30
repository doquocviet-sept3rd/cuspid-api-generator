package org.cuspid.constant;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author Do Quoc Viet
 * The template for the generation
 */

public final class Template {

    /**
     * Repository interface template
     */
    public static final String REPOSITORY_INTERFACE_TEMPLATE;

    /**
     * Service interface template
     */
    public static final String SERVICE_INTERFACE_TEMPLATE;

    /**
     * Service implementation template
     */
    public static final String SERVICE_IMPL_TEMPLATE;

    /**
     * Api template
     */
    public static final String API_TEMPLATE;

    /**
     * Controller template
     */
    public static final String CONTROLLER_TEMPLATE;

    static {
        REPOSITORY_INTERFACE_TEMPLATE = getTemplate("repository_interface.template");
        SERVICE_INTERFACE_TEMPLATE = getTemplate("service_interface.template");
        SERVICE_IMPL_TEMPLATE = getTemplate("service_impl.template");
        API_TEMPLATE = getTemplate("api.template");
        CONTROLLER_TEMPLATE = getTemplate("controller.template");
    }

    /**
     * Get the template
     *
     * @param filename the filename
     * @return the template
     */
    static String getTemplate(String filename) {
        try (InputStream in = Objects.requireNonNull(Template.class.getResource("/template/" + filename)).openStream()) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Template() {
    }
}
