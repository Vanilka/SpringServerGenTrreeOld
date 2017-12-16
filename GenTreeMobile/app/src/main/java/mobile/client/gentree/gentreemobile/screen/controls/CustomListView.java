package mobile.client.gentree.gentreemobile.screen.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import mobile.client.gentree.gentreemobile.R;

/**
 * Created by vanilka on 15/12/2017.
 */
public class CustomListView extends ListView {

   private ViewGroup header;

    public CustomListView(Context context) {
        super(context);
        init();
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setHeader(String text) {

        ((TextView) header.findViewById(R.id.headerText)).setText(text);
    }

    private void init() {
        LayoutInflater inflater =(LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
         header = (ViewGroup)inflater.inflate(R.layout.family_listview_header,this,false);
        addHeaderView(header);
    }


}
