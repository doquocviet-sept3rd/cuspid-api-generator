package org.cuspid.constant;

import org.cuspid.util.LOGGER;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Do Quoc Viet
 */

public final class Template {
    public static final String REPOSITORY_TEMPLATE;

    public static final String SERVICE_INTERFACE_TEMPLATE = """
            """;

    public static final String SERVICE_IMPL_TEMPLATE = """
            """;

    public static final String API_TEMPLATE = """
            """;

    public static final String CONTROLLER_TEMPLATE = """
            """;

    static {
        try {
            REPOSITORY_TEMPLATE = Files.readString(Path.of("src\\main\\resources\\template\\repository.template"));
        } catch (IOException e) {
            LOGGER.instance().error("Can't read templates, please try again.");
            throw new RuntimeException(e);
        }
    }

    private Template() {
    }
}
