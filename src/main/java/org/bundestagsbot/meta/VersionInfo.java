package org.bundestagsbot.meta;

public class VersionInfo {
    private static final String version = "0.1.0";
    private static final String subVersion = "SNAPSHOT";

    public static String getVersion() {
        return version;
    }

    public static String getLongVersion() {
        if (subVersion.isEmpty()) {
            return "v." + version;
        }
        return "v." + version + "-" + subVersion;
    }
}
