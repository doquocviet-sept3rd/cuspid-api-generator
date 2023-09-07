package org.cuspid.util;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author Do Quoc Viet
 * The generate util class
 */
public class GenerateUtil {

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
        Optional<Field> fieldOptional = Arrays.stream(clazz.getDeclaredFields())
                .filter(declaredField -> Arrays
                        .stream(declaredField.getAnnotations())
                        .anyMatch(predicate))
                .findFirst();
        if (fieldOptional.isEmpty()) {
            throw new IllegalStateException("No Id field found for class " + clazz);
        }
        return fieldOptional
                .orElseThrow()
                .getType();
    }

    /**
     * Convert the given string to a string with lowercase the first character
     *
     * @param string the string to be converted
     * @return the string with lowercase the first character
     */
    public static String firstLowerCase(String string) {
        if (StringUtils.isBlank(string)) {
            return StringUtils.EMPTY;
        }
        return string.substring(0, 1).toLowerCase() + string.substring(1);
    }

}
