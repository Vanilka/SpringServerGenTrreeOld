package mobile.client.gentree.gentreemobile.rest.tasks;

import android.os.AsyncTask;
import mobile.client.gentree.gentreemobile.rest.responses.ServerResponse;

/**
 * Created by vanilka on 15/12/2017.
 */
public class RetrieveFullFamilyTask extends AsyncTask<Void, Integer, ServerResponse> {

    private ServerResponse serverResponse;

    public RetrieveFullFamilyTask() {

    }

    @Override
    protected ServerResponse doInBackground(Void... voids) {
        return serverResponse;
    }
}
