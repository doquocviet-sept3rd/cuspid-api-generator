package org.cuspid.constant;

/**
 * @author Do Quoc Viet
 * The Cuspid properties keys
 */

public enum CuspidSystemProperty {
    LOG("The Log property of the maven project"),
    ENTITIES("The Entities property of the maven project"),
    MAVEN_PROJECT("The Maven Project property"),
    PREFIX("The Prefix property of the project's classes");

    private final String description;

    CuspidSystemProperty(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
