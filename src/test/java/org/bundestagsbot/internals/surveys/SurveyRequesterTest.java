package org.bundestagsbot.internals.surveys;

import org.bundestagsbot.internals.surveys.dawumjsonmapper.RootDawumJson;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class SurveyRequesterTest {
    @Test
    public void getAPIResponse() {
        RootDawumJson response = null;
        try {
            response = SurveyRequester.getAPIResponse();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Receiving response from survey API failed.");
        }
        Assert.assertNotNull("Response cannot be null", response);
    }
}