package mobile.client.gentree.gentreemobile.rest.tasks;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.widget.ArrayAdapter;
import com.fasterxml.jackson.core.type.TypeReference;
import gentree.exception.ExceptionBean;
import gentree.server.dto.FamilyDTO;
import gentree.server.dto.OwnerDTO;
import mobile.client.gentree.gentreemobile.configuration.utilities.BundleParams;
import mobile.client.gentree.gentreemobile.configuration.utilities.ServerUrl;
import mobile.client.gentree.gentreemobile.rest.responses.ExceptionResponse;
import mobile.client.gentree.gentreemobile.rest.responses.FamilyListResponse;
import mobile.client.gentree.gentreemobile.rest.responses.ServerResponse;
import mobile.client.gentree.gentreemobile.screen.adapters.FamilyListAdapter;
import mobile.client.gentree.gentreemobile.screen.controls.CustomFamilyListView;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vanilka on 15/12/2017.
 */
public class RetrieveFamiliesTask extends RestTask {

    private CustomFamilyListView listView;
    private OwnerDTO currentOwner;

    public RetrieveFamiliesTask(OwnerDTO owner, CustomFamilyListView view) {
        this.currentOwner = owner;
        this.listView = view;
        this.context = view.getContext();
        System.out.println("Context from retrieve Families " + view.getContext());

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
}
