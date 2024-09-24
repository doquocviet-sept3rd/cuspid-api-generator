package org.cuspid.util;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cuspid.constant.CuspidSystemProperty;
import org.cuspid.system.CuspidSystem;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static org.cuspid.util.Strings.firstLowerCase;

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
    public static Class<?> findIdClass(Class<?> clazz) {
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
    public static Map<String, String> getProjections(Class<?> clazz) {
        Class<?> entityIdClass = Generators.findIdClass(clazz);
        String prefix = (String) CuspidSystem.get(CuspidSystemProperty.PREFIX);
        Map<String, String> projections = new HashMap<>();
        projections.put("prefix", prefix);
        projections.put("entityName", clazz.getName());
        projections.put("entityIdName", entityIdClass.getName());
        projections.put("entityClassSimpleName", clazz.getSimpleName());
        projections.put("entityIdClassSimpleName", entityIdClass.getSimpleName());
        Map<String, String> transformedProjections = new HashMap<>();
        for (Map.Entry<String, String> entry : projections.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            transformedProjections.put(String.format("%s.toFirstLowerCase()", key), firstLowerCase(value));
        }
        projections.putAll(transformedProjections);
        return projections;
    }

}
