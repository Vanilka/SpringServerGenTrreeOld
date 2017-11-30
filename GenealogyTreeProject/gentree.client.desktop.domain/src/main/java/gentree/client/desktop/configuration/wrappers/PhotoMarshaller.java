package gentree.client.desktop.configuration.wrappers;


import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 17/07/2017.
 */
public class PhotoMarshaller extends XmlAdapter<String, String> {
    private static List<String> ignoredPaths = new ArrayList<>();

    public static void addIgnoredPaths(String... paths) {
        ignoredPaths.addAll(Arrays.asList(paths));
    }

    @Override
    public String unmarshal(String v) throws Exception {
        if (v == null || v.equals("")) {
            return null;
        }
        return v;
    }

    @Override
    public String marshal(String v) throws Exception {
        if (ignoredPaths.contains(v)) {
            return null;
        } else {
            return v;
        }
    }
}
