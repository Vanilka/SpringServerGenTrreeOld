package mobile.client.gentree.gentreemobile.rest.tasks;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import com.fasterxml.jackson.core.type.TypeReference;
import gentree.exception.ExceptionBean;
import gentree.server.dto.FamilyDTO;
import gentree.server.dto.OwnerDTO;
import mobile.client.gentree.gentreemobile.configuration.utilities.BundleParams;
import mobile.client.gentree.gentreemobile.configuration.utilities.ServerUrl;
import mobile.client.gentree.gentreemobile.rest.responses.ExceptionResponse;
import mobile.client.gentree.gentreemobile.rest.responses.FamilyResponse;
import mobile.client.gentree.gentreemobile.rest.responses.ServerResponse;
import mobile.client.gentree.gentreemobile.screen.FamilyView;
import mobile.client.gentree.gentreemobile.screen.ProjectListActivity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.List;

/**
 * Created by vanilka on 15/12/2017.
 */
public class RetrieveFullFamilyTask extends RestTask {


    private View view;

    private FamilyDTO family;

    public RetrieveFullFamilyTask(View view, FamilyDTO familyDTO) {
        this.family = familyDTO;
        this.view = view;
        this.context = view.getContext();
    }

    @Override
    protected ServerResponse doInBackground(Void... voids) {
        try {
            doGet(generateHttpEntity(ownerService.getOwner().getLogin(), ownerService.getOwner().getPassword()));
        } catch (HttpClientErrorException e) {
            System.out.println("error message : " + e.getMessage());
            serverResponse = new ExceptionResponse();
        } catch (Exception e) {
            System.out.println(e.getClass());
            Log.e("MainActivity", e.getMessage(), e);
            serverResponse = new ExceptionResponse();
        }
        return serverResponse;
    }

    @Override
    protected void onPostExecute(ServerResponse serverResponse) {
        super.onPostExecute(serverResponse);

        if(serverResponse.getStatus() == ServerResponse.ResponseStatus.OK) {
            System.out.println("Retrieved Familly : " + ((FamilyResponse) serverResponse).getFamily());
            ownerService.setFamily(((FamilyResponse) serverResponse).getFamily());
            openProjectViewActivity();
        } else {
            Snackbar.make(view, "Error while retrieve family", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private void doGet(HttpEntity httpEntity) throws IOException {
        ResponseEntity<String> entity = restTemplate.exchange(String.format(ServerUrl.URL_GET_FAMILY, family.getId()), HttpMethod.GET, httpEntity, String.class);

        if (entity.getStatusCode() == HttpStatus.OK) {
            FamilyDTO family = objectMapper.readValue(entity.getBody(), FamilyDTO.class);
            this.serverResponse = new FamilyResponse(family);
        } else {
            ExceptionBean exceptionBean = objectMapper.readValue(entity.getBody(), ExceptionBean.class);
            this.serverResponse = new ExceptionResponse(exceptionBean);
        }
    }


    private void openProjectViewActivity() {
        try {
            Intent intent = new Intent(context, FamilyView.class);
            context.startActivity(intent);
        } catch (Exception e) {
            Snackbar.make(view, "conversion error", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

}
