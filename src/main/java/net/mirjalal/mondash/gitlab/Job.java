package net.mirjalal.mondash.gitlab;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public class Job {
    private String url;
    private Map<String, String> params;

    public Job(String url, Map<String, String> params) {
        this.url = url;
        this.params = params;
    }

    public void triggerJob() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

        restTemplate.postForEntity(this.url, this.params, String.class);
    }
}
