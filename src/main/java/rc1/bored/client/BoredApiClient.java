package rc1.bored.client;

import org.springframework.web.client.RestClientException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.*;
import rc1.bored.model.BoredActivity;

import static org.springframework.shell.command.invocation.InvocableShellMethod.log;


public class BoredApiClient {

    String API_URL = "http://www.boredapi.com/api/activity";
    RestTemplate restTemplate;
    public BoredActivity getDataFromApi(String requestUrl) {
        RestTemplate restTemplate = new RestTemplate();
        BoredActivity data = null;
        try {
            ResponseEntity<BoredActivity> response = restTemplate.getForEntity(requestUrl, BoredActivity.class);
             data = response.getBody();
            if (response.getStatusCode().is2xxSuccessful()) {
                if (data != null) {
                    log.info("Received data: " + data);
                    System.out.println("Received data: " + data.getActivity());
                } else {
                    log.error("Empty response []");
                }
            } else {
                log.error("Error: " + response.getStatusCode());
                throw new RestClientException("Error while fetching data from the API.");
            }
        } catch (RestClientException e) {
            log.error("Error connecting to the URL: " + e.getMessage());
            throw new RestClientException("Error while connecting to the API: " + e.getMessage());
        }
        return data;
    }
}
