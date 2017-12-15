package mobile.client.gentree.gentreemobile.screen.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import gentree.server.dto.FamilyDTO;
import gentree.server.dto.FamilyLazyDTO;
import mobile.client.gentree.gentreemobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vanilka on 15/12/2017.
 */
public class FamilyListAdapter extends ArrayAdapter<FamilyDTO> {

    public FamilyListAdapter(Context context,  ArrayList<FamilyDTO> familyList) {
        super(context, 0, familyList);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        FamilyDTO family = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_family, parent, false);
        }

        TextView familyName = (TextView) convertView.findViewById(R.id.name);
        familyName.setText(family.getName());
        return convertView;

    }



}


