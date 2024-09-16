package org.cuspid.system;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cuspid.constant.CuspidSystemProperty;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Do Quoc Viet
 * Cuspid properties
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CuspidSystem {
    private static final Map<CuspidSystemProperty, Object> map = new ConcurrentHashMap<>();

    public static void put(CuspidSystemProperty key, Object value) {
        map.put(key, value);
    }

    public static Object get(CuspidSystemProperty cuspidSystemProperty) {
        return map.get(cuspidSystemProperty);
    }

}
