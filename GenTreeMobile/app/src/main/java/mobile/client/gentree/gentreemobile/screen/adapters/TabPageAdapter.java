package mobile.client.gentree.gentreemobile.screen.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import lombok.Getter;
import lombok.Setter;
import mobile.client.gentree.gentreemobile.configuration.wrappers.FragmentWrapper;
import mobile.client.gentree.gentreemobile.screen.fragments.MemberListFragment;
import mobile.client.gentree.gentreemobile.screen.fragments.RelationListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vanilka on 22/12/2017.
 */
public class TabPageAdapter extends FragmentPagerAdapter {

    //List reference fragments in TabView
    private final List<FragmentWrapper> tabList = new ArrayList<>();

    @Getter
    private final MemberListFragment membersFragment = MemberListFragment.newInstance(this);

    @Getter
    private final RelationListFragment relationsFragment = RelationListFragment.newInstance(this);


    public TabPageAdapter(FragmentManager fm) {
        super(fm);
    }


    /**
     * Adding new Tab
     *
     * @param fragment
     * @param title
     */
    public void addFragment(Fragment fragment, String title) {
        if (!(fragment instanceof MemberListFragment) && ! (fragment instanceof RelationListFragment))
            tabList.add(new FragmentWrapper(fragment, title));

    }

    /**
     * Getting Fragment
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return membersFragment;
            case 1:
                return relationsFragment;
            default:
                return tabList.get(position).getFragment();
        }
    }

    @Override
    public int getCount() {
        return tabList.size() + 2;
    }


    /**
     * Get Tab Title on position given in parameter
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Members";
            case 1:
                return "Relations";
            default:
                return tabList.get(position).getTitle();
        }
    }


}
