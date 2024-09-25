package org.cuspid.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Strings {
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
