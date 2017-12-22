package mobile.client.gentree.gentreemobile.screen.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import gentree.common.configuration.enums.Gender;
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
        ImageView simPhoto = (ImageView) convertView.findViewById(R.id.simPhoto);
        setSimPhoto(simPhoto, memberDTO);
        return convertView;

    }


    private void setSimPhoto(ImageView imageView, MemberDTO member) {

        try {
            byte[] decodedString = Base64.decode(member.getPhotoDTO().getEncodedPicture(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView.setImageBitmap(Bitmap.createBitmap(decodedByte));
        } catch (Exception e) {
            e.printStackTrace();
            imageView.setImageResource(member.getGender() == Gender.F ? R.drawable.generic_female : R.drawable.generic_male);
        }

    }

}
