package mobile.client.gentree.gentreemobile.screen.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import gentree.server.dto.MemberDTO;
import mobile.client.gentree.gentreemobile.R;

import java.util.ArrayList;

/**
 * Created by vanilka on 21/12/2017.
 */
public class MemberListAdapter extends ArrayAdapter<MemberDTO> {

    public MemberListAdapter(Context context, ArrayList<MemberDTO> memberList) {
        super(context, 0, memberList);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MemberDTO memberDTO = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_member, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(memberDTO.getName());
        TextView surname = (TextView) convertView.findViewById(R.id.surname);
        surname.setText(memberDTO.getSurname());
        TextView borname = (TextView) convertView.findViewById(R.id.borname);
        borname.setText(memberDTO.getSurname());
        return convertView;

    }
}
