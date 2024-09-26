package org.cuspid.util;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cuspid.constant.CuspidSystemProperty;
import org.cuspid.domain.MustacheClass;
import org.cuspid.domain.MustacheString;
import org.cuspid.system.CuspidSystem;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static org.cuspid.constant.CuspidSystemProperty.PREFIX;

/**
 * @author Do Quoc Viet
 * The generate util class
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Generators {

    /**
     * Find id class of the provided class
     *
     * @param clazz the class to find the id class
     * @return the id class of the provided class
     */
    public static Class<?> findClazzId(Class<?> clazz) {
        Predicate<Annotation> predicate = annotation ->
                // @formatter:off
                annotation.annotationType().getTypeName().equals(Id.class.getTypeName())
                || annotation.annotationType().getTypeName().equals(EmbeddedId.class.getTypeName());
                // @formatter:on
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(declaredField -> Arrays
                        .stream(declaredField.getAnnotations())
                        .anyMatch(predicate))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No Id field found for class " + clazz))
                .getType();
    }

    /**
     * Get projections for injecting
     *
     * @param clazz class metadata to be injected
     * @return projections
     */
    public static Map<String, Object> getProjections(Class<?> clazz) {
        Map<String, Object> projections = new HashMap<>();
        projections.put("prefix", MustacheString.of((String) CuspidSystem.get(PREFIX)));
        projections.put("entity", MustacheClass.of(clazz));
        String outputDir = (String) CuspidSystem.get(CuspidSystemProperty.OUTPUT_DIR);
        projections.put("path", outputDir.replaceAll("[/\\\\]", ".") + ".cuspid");
        return projections;
    }

}
