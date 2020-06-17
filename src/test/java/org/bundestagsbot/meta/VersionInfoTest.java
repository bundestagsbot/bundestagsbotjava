package org.bundestagsbot.meta;

import org.junit.Test;

import static org.junit.Assert.*;

public class VersionInfoTest {
    @Test
    public void getVersion() {
        String result = VersionInfo.getVersion();
        assertNotNull("Version info cannot be null", result);
        assertFalse("Version info cannot be empty", result.isBlank());
    }

    @Test
    public void getLongVersion() {
        String result = VersionInfo.getLongVersion();
        assertNotNull("Long version info cannot be null", result);
        assertFalse("Long version info cannot be empty", result.isBlank());
    }
}