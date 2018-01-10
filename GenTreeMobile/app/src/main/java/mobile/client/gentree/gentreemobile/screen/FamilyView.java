package mobile.client.gentree.gentreemobile.screen;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.fasterxml.jackson.databind.ObjectMapper;
import gentree.server.dto.FamilyDTO;
import gentree.server.dto.OwnerDTO;
import gentree.server.dto.OwnerExtendedDTO;
import mobile.client.gentree.gentreemobile.R;
import mobile.client.gentree.gentreemobile.configuration.OwnerService;
import mobile.client.gentree.gentreemobile.configuration.utilities.BundleParams;
import mobile.client.gentree.gentreemobile.screen.adapters.TabPageAdapter;
import mobile.client.gentree.gentreemobile.screen.fragments.MemberListFragment;

public class FamilyView extends AppCompatActivity {

    private final OwnerService ownerService = OwnerService.INSTANCE;

    private ObjectMapper objectMapper = new ObjectMapper();

    private ViewPager pageViewer;
    private TabPageAdapter tabPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_view);
        initialize();
        readDtos();
    }

    private void initialize() {
        initializePageViewer();
        initializeTabBar();
    }

    private void initializePageViewer() {
        // Set up the ViewPager with the sections adapter.
        pageViewer = (ViewPager) findViewById(R.id.container);
        tabPageAdapter = new TabPageAdapter(getSupportFragmentManager());
        pageViewer.setAdapter(tabPageAdapter);
    }

    private void initializeTabBar() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pageViewer);
    }


    private void readDtos() {
        try {
            tabPageAdapter.getMembersFragment().setMembers(ownerService.getFamily().getMembers());
            tabPageAdapter.getRelationsFragment().setRelations(ownerService.getFamily().getRelations());
            System.out.println("Members " + ownerService.getFamily().getMembers());
        } catch (Exception e) {
            Snackbar.make(pageViewer, "Error while mapping objects", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }


}
