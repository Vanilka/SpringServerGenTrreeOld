package mobile.client.gentree.gentreemobile.screen;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import mobile.client.gentree.gentreemobile.R;
import mobile.client.gentree.gentreemobile.screen.fragments.MemberListFragment;

public class FamilyView extends AppCompatActivity {

    private ViewPager pageViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_view);
        initialize();
    }

    private void initialize() {
        initializePageViewer();
        initializeTabBar();
    }

    private void initializePageViewer() {
        // Set up the ViewPager with the sections adapter.
        pageViewer = (ViewPager) findViewById(R.id.container);
    }

    private void initializeTabBar() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pageViewer);
    }

}
