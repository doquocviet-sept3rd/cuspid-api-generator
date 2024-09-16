package org.cuspid.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.cuspid.constant.CuspidSystemProperty;
import org.cuspid.system.CuspidSystem;

import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

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
     * Convert the given string to a string with lowercase the first character
     *
     * @param str the string to be converted
     * @return the string with lowercase the first character
     */
    public static String firstLowerCase(String str) {
        if (StringUtils.isBlank(str)) {
            return StringUtils.EMPTY;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
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
        HashMap<String, String> values = new HashMap<>();
        values.put("prefix", prefix);
        values.put("prefix.lowercase", Generators.firstLowerCase(prefix));
        values.put("entityName", clazz.getName());
        values.put("entityIdName", entityIdClass.getName());
        values.put("entityClassSimpleName", clazz.getSimpleName());
        values.put("entityIdClassSimpleName", entityIdClass.getSimpleName());
        return values;
    }

}
