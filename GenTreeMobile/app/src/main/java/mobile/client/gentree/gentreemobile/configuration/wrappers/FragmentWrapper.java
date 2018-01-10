package mobile.client.gentree.gentreemobile.configuration.wrappers;

import android.support.v4.app.Fragment;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by vanilka on 22/12/2017.
 */
@Getter
@Setter
public class FragmentWrapper {

    String title;
    Fragment fragment;

    public FragmentWrapper(Fragment fragment, String title) {
        this.fragment = fragment;
        this.title = title;
    }
}
