package org.bundestagsbot.Internals.Surveys;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.Charsets;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.bundestagsbot.Internals.Surveys.DawumJsonMapper.RootDawumJson;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.http.HttpClient;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class SurveyRequester {
    public static RootDawumJson getAPIResponse() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("https://api.dawum.de/");

        request.setHeader("Content-type", "application/json");

        HttpResponse response = httpClient.execute(request);

        HttpEntity entity = response.getEntity();
        Header encodingHeader = entity.getContentEncoding();

        Charset encoding = encodingHeader == null ? StandardCharsets.UTF_8 :
                Charsets.toCharset(encodingHeader.getValue());

        String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(json, RootDawumJson.class);
    }
}
