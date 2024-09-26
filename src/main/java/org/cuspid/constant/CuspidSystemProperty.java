package org.cuspid.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Do Quoc Viet
 * Cuspid system property keys
 */

@Getter
@RequiredArgsConstructor
public enum CuspidSystemProperty {
    ENTITIES("The Entities property of the maven project"),
    MAVEN_PROJECT("The Maven Project property"),
    PREFIX("The Prefix property of the project's classes"),
    OUTPUT_DIR("The Output Directory property of the project");

    private final String description;
}
