package mobile.client.gentree.gentreemobile.screen;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import com.fasterxml.jackson.databind.ObjectMapper;
import gentree.server.dto.OwnerExtendedDTO;
import mobile.client.gentree.gentreemobile.R;
import mobile.client.gentree.gentreemobile.configuration.utilities.BundleParams;
import mobile.client.gentree.gentreemobile.rest.tasks.RetrieveFamiliesTask;

public class ProjectListActivity extends AppCompatActivity {

    private ObjectMapper objectMapper = new ObjectMapper();

    private ListView projectListView;

    private OwnerExtendedDTO currentOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);

        initialize();

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString(BundleParams.CURRENT_USER) != null) {
            readCurrentUser(bundle.getString(BundleParams.CURRENT_USER));
        }
    }


    private void readCurrentUser(String string) {
        try {
            currentOwner = objectMapper.readValue(string, OwnerExtendedDTO.class);
            new RetrieveFamiliesTask(currentOwner, projectListView).execute();
            /*            ArrayAdapter<OwnerExtendedDTO> adapter = new ArrayAdapter<OwnerExtendedDTO>(this,
                    android.R.layout.simple_list_item_1, (OwnerExtendedDTO[]) currentOwner.getFamilyList().toArray());
            projectListView.setAdapter(adapter);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initialize() {
        projectListView = (ListView) findViewById(R.id.projectListView);


    }

}
