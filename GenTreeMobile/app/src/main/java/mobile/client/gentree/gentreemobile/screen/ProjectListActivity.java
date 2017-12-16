package mobile.client.gentree.gentreemobile.screen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.fasterxml.jackson.databind.ObjectMapper;
import gentree.server.dto.OwnerExtendedDTO;
import mobile.client.gentree.gentreemobile.R;
import mobile.client.gentree.gentreemobile.configuration.utilities.BundleParams;
import mobile.client.gentree.gentreemobile.rest.tasks.RetrieveFamiliesTask;
import mobile.client.gentree.gentreemobile.screen.controls.CustomListView;

public class ProjectListActivity extends AppCompatActivity {

    private ObjectMapper objectMapper = new ObjectMapper();

    private CustomListView projectListView;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initialize() {
        projectListView = (CustomListView) findViewById(R.id.projectListView);

        projectListView.setHeader("Families");
    }

}
