package mobile.client.gentree.gentreemobile.rest.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.widget.ArrayAdapter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gentree.exception.ExceptionBean;
import gentree.server.dto.FamilyDTO;
import gentree.server.dto.OwnerDTO;
import mobile.client.gentree.gentreemobile.configuration.GenTreeRestTemplate;
import mobile.client.gentree.gentreemobile.configuration.utilities.ServerUrl;
import mobile.client.gentree.gentreemobile.rest.responses.ExceptionResponse;
import mobile.client.gentree.gentreemobile.rest.responses.FamilyListResponse;
import mobile.client.gentree.gentreemobile.rest.responses.ServerResponse;
import mobile.client.gentree.gentreemobile.screen.controls.CustomListView;
import mobile.client.gentree.gentreemobile.screen.adapters.FamilyListAdapter;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vanilka on 15/12/2017.
 */
public class RetrieveFamiliesTask extends AsyncTask<Void, Integer, ServerResponse> {

    private ServerResponse serverResponse;
    private CustomListView listView;
    private Context context;

    private ObjectMapper objectMapper = new ObjectMapper();
    private RestTemplate restTemplate = new GenTreeRestTemplate();

    private OwnerDTO currentOwner;

    public RetrieveFamiliesTask(OwnerDTO owner, CustomListView view) {
        this.currentOwner = owner;
        this.listView = view;
        this.context = view.getContext();
        System.out.println("Context from retrieve Families " +  view.getContext());

    }

    @Override
    protected ServerResponse doInBackground(Void... voids) {
        try {
            doGet(generateHttpEntity(currentOwner.getLogin(), currentOwner.getPassword()));
        } catch (IOException e) {
            e.printStackTrace();
            serverResponse = new ExceptionResponse();
        }
        return serverResponse;
    }


    @Override
    protected void onPostExecute(ServerResponse serverResponse) {
        super.onPostExecute(serverResponse);

        if (serverResponse.getStatus() == ServerResponse.ResponseStatus.OK) {
          ArrayList<FamilyDTO> list = new ArrayList<>(((FamilyListResponse) serverResponse).getFamilyList());
            ArrayAdapter<FamilyDTO> adapter = new FamilyListAdapter(context, list);
            listView.setAdapter(adapter);
        } else {
            Snackbar.make(listView, "Error while retrieve families", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private void doGet(HttpEntity httpEntity) throws IOException {

        ResponseEntity<String> entity = restTemplate.exchange(ServerUrl.URL_GET_FAMILIES, HttpMethod.GET, httpEntity, String.class);
        if (entity.getStatusCode() == HttpStatus.OK) {
            List<FamilyDTO> familyList = objectMapper.readValue(entity.getBody(), new TypeReference<List<FamilyDTO>>() {
            });
            this.serverResponse = new FamilyListResponse(familyList);
        } else {
            ExceptionBean exceptionBean = objectMapper.readValue(entity.getBody(), ExceptionBean.class);
            this.serverResponse = new ExceptionResponse(exceptionBean);
        }

    }

    private HttpEntity generateHttpEntity(String login, String password) {
        HttpAuthentication authHeader = new HttpBasicAuthentication(login, password);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAuthorization(authHeader);
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<?> httpEntity = new HttpEntity<Object>(null, requestHeaders);
        return httpEntity;
    }
}
