package mobile.client.gentree.gentreemobile.rest.tasks;

import android.os.AsyncTask;
import android.util.Log;
import gentree.server.dto.FamilyDTO;
import gentree.server.dto.OwnerDTO;
import mobile.client.gentree.gentreemobile.configuration.utilities.ServerUrl;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Created by vanilka on 13/12/2017.
 */
public class LoginTask extends AsyncTask<String, Integer, Void> {

    @Override
    protected Void doInBackground(String... strings) {
        try {
            String login = strings[0];
            String pass = strings[1];

            HttpAuthentication authHeader = new HttpBasicAuthentication("admin", "admin");
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAuthorization(authHeader);
            requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<?> httpEntity = new HttpEntity<Object>(null, requestHeaders);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            OwnerDTO owner = restTemplate.postForObject(ServerUrl.URL_LOGIN, httpEntity, OwnerDTO.class);

            System.out.println("Owner is : " +owner);
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        return null;
    }
}
