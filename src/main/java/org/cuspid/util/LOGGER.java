package org.cuspid.util;

import org.apache.maven.plugin.logging.Log;
import org.cuspid.constant.CuspidSystemProperty;
import org.cuspid.system.CuspidSystem;

/**
 * @author Do Quoc Viet
 * The Logger util
 */

public class LOGGER {
    /**
     * Logger instance
     *
     * @return the logger instance
     */
    public static Log instance() {
        return (Log) CuspidSystem.getProperty(CuspidSystemProperty.LOG);
    }
}
