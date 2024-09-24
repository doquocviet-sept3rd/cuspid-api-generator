package org.cuspid.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Strings {

    public static String substitute(String template, Map<String, String> values) {
        Pattern pattern = Pattern.compile("\\$\\{([\\w.()]+)}");
        Matcher matcher = pattern.matcher(template);
        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            String key = matcher.group(1);
            String replacement = values.getOrDefault(key, matcher.group(0)); // Fallback to original if key is missing
            matcher.appendReplacement(result, replacement);
        }
        matcher.appendTail(result);

        return result.toString();
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
}
