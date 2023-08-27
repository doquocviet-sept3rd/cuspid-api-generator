package org.cuspid.system;

import org.cuspid.constant.CuspidSystemProperty;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Do Quoc Viet
 * The cuspid properties
 */

public final class CuspidSystem {
    private static final Map<CuspidSystemProperty, Object> map = new ConcurrentHashMap<>();

    public static void putProperty(CuspidSystemProperty key, Object value) {
        map.put(key, value);
    }

    public static Object getProperty(CuspidSystemProperty cuspidSystemProperty) {
        return map.get(cuspidSystemProperty);
    }

    private CuspidSystem() {
    }

}
