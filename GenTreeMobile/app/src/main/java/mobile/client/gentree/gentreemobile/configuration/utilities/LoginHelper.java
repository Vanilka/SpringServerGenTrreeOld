package mobile.client.gentree.gentreemobile.configuration.utilities;

/**
 * Created by vanilka on 13/12/2017.
 */
public class LoginHelper {
    public static boolean isNotEmpty(String text) {
        return text != null && !text.trim().equals("");
    }
}
