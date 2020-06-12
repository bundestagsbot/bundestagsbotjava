package org.bundestagsbot.meta;

import org.junit.Test;

import static org.junit.Assert.*;

public class AboutTest {
    @Test
    public void getInfo() {
        String result = About.getInfo();
        assertNotNull("About info cannot be null", result);
        assertFalse("About info cannot be empty", result.isBlank());
    }
}