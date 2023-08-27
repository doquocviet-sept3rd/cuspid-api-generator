package org.cuspid.constant;

/**
 * @author Do Quoc Viet
 */

public enum CuspidSystemProperty {
    LOG("The Log property of the maven project"),
    ENTITIES("The Entities property of the maven project"),
    MAVEN_PROJECT("The Maven Project property");

    private final String description;

    CuspidSystemProperty(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
